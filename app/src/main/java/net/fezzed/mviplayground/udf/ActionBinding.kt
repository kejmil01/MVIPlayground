package net.fezzed.mviplayground.udf

import android.util.Log
import io.reactivex.rxjava3.core.ObservableTransformer
import kotlin.reflect.KClass

class ActionBinding<Action : Any, ActionResult>(
    val cls: KClass<Action>,
    private val actionProcessor: ActionProcessor<Action, ActionResult>
) {

    internal val transformer: ObservableTransformer<in Any, ActionResult> =
        ObservableTransformer { actions ->
            actions.flatMap { action ->
                @Suppress("UNCHECKED_CAST")
                val actionValue = action as Action

                Log.d("UDF_FLOW", "actionProcessor - action: $action")
                actionProcessor.process(actionValue)
            }
        }

}