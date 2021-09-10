package net.fezzed.mviplayground.udf

import io.reactivex.rxjava3.core.Observable

interface ActionProcessor<Action, ActionResult> {
    fun process(action: Action): Observable<ActionResult>
}