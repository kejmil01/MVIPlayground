package net.fezzed.mviplayground.udf

import androidx.annotation.IntRange

enum class ProcessState(private val intValue: Int) {
	IN_PROGRESS(0), IDLE(1);

	fun toInt(): Int {
		return intValue
	}

	companion object {
		/**
		 * @throws [NoSuchElementException] if no such element is found.
		 */
		fun fromInt(@IntRange(from = 0, to = 1) intValue: Int): ProcessState {
			return values().first { it.intValue == intValue }
		}
	}
}