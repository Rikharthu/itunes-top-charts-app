package com.rikharthu.itunestopcharts.data.source

sealed class Resource<out T : Any?> {

    class Success<out T : Any>(val data: T) : Resource<T>() {
        override fun toString(): String {
            return "Success(data=$data)"
        }
    }

    class Error(val exception: Throwable) : Resource<Nothing>() {
        override fun toString(): String {
            return "Error(exception=$exception)"
        }
    }
}