package net.fezzed.mviplayground.udf

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * Base class for ActionProcessors operating in a continuous manner,
 * to i.e. debounce the feed data before the actual processing is performed.
 *
 * Feed stream has to be initialized in a derived class,
 * i.e.
 * 		init {
 * 			feedSubject
 * 					.distinctUntilChanged()
 * 					.debounce(400, TimeUnit.MILLISECONDS)
 * 					.map { ... } //Converts to result object
 * 					.subscribe { resultSubject.onNext(it) }
 * 		}
 */
abstract class ContinuousActionProcessor<Action, ActionResult> :
    ActionProcessor<Action, ActionResult> {
    protected var feedSubject: PublishSubject<Action> = PublishSubject.create()
    protected var resultSubject: PublishSubject<ActionResult> = PublishSubject.create()
    protected var lastProcessingDisposable: Disposable? = null

    override fun process(action: Action): Observable<ActionResult> {
        lastProcessingDisposable?.dispose()

        return buildSafeResultStream(action)
            .doOnSubscribe {
                lastProcessingDisposable = it
            }
    }

    /**
     * Allows to feed the stream after establishing a subscription
     * 	- to ensure that all emitted results are going to be delivered to the subscriber.
     */
    protected fun buildSafeResultStream(action: Action): Observable<ActionResult> {
        return Observable.merge(
            buildResultStream(action),
            Observable.defer {
                onAfterResultStreamSubscription(action)
                return@defer Observable.empty<ActionResult>()
            })
    }

    /**
     * Builds the actual result stream.
     * Override it to add the custom logic, i.e. resultSubject.startWith({result})
     */
    protected open fun buildResultStream(action: Action): Observable<ActionResult> {
        return resultSubject
    }

    protected open fun onAfterResultStreamSubscription(action: Action) {
        feedSubject.onNext(action)
    }
}