package me.davidcosta.github.extensions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Int.toShortNotation(): String =
    this
        .takeIf { it >= 1_000 }
        ?.let {
            DecimalFormat(
                "#.#K",
                DecimalFormatSymbols(Locale.US)
            )
                .format(it / 1_000.0)
        } ?: this.toString()
