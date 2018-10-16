package com.rikharthu.itunestopcharts.util

fun Long.toFormattedTime(): String {
    val minutes = this / 60
    val seconds = this - minutes * 60
    return String.format("%02d:%02d", minutes, seconds)
}

fun Long.millisToFormattedTime(): String = (this / 1000).toFormattedTime()