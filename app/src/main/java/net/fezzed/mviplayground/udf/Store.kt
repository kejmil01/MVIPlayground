package net.fezzed.mviplayground.udf

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

open class Store<State, OneTimeEvent, Action : Any, ActionResult> : CurrentStateHolder<State> {
    private val actionsSubject: PublishSubject<Action> = PublishSubject.create()
    private val mutableState: BehaviorSubject<State> = BehaviorSubject.create()

    val state: Observable<State> = mutableState
    override val currentState: State?
        get() = mutableState.value
    val oneTimeEvent = SingleLiveEvent<OneTimeEvent>()

    fun onNextAction(action: Action) {
        actionsSubject.onNext(action)
    }

    open fun initializeStream(
        bindings: List<ActionBinding<out Action, ActionResult>>,
        reducer: BiFunction<State, ActionResult, State>,
        initialState: State
    ): Disposable {
        return actionsSubject
            .compose(buildActionProcessor(bindings))
            // Cache each state and pass it to the reducer to create a new state from
            // the previous cached one and the latest Result emitted from the action processor.
            // The Scan operator is used here for the caching.
            .scan(initialState, reducer)
            .distinctUntilChanged()
            .toFlowable(BackpressureStrategy.BUFFER)
            .subscribe {
                mutableState.onNext(it)
            }
    }

    protected fun <Action : Any, ActionResult> buildActionProcessor(
        bindings: List<ActionBinding<out Action, ActionResult>>
    ):
            ObservableTransformer<Action, ActionResult> {
        return ObservableTransformer { actions ->
            actions.publish { selector ->
                Observable.mergeArray(
                    *buildActionMapperArray(
                        selector,
                        bindings
                    )
                )
            }
        }
    }

    /**
     * Maps actionProcessors with dedicated actions
     */
    protected fun <Action : Any, ActionResult> buildActionMapperArray(
        action: Observable<Action>,
        bindingArray: List<ActionBinding<out Action, ActionResult>>
    ): Array<ObservableSource<ActionResult>> {
        return bindingArray.map { binding ->
            action.ofType(binding.cls.java).compose(binding.transformer)
        }.toTypedArray()
    }
}