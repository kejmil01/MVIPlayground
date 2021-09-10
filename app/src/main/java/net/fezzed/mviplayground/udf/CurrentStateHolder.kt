package net.fezzed.mviplayground.udf

interface CurrentStateHolder<State> {
	val currentState: State?
}