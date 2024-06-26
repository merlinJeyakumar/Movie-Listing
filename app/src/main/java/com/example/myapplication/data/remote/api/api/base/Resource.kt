package com.example.myapplication.data.remote.api.api.base

data class Resource<out T> (val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, "")
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(Status.ERROR, null, msg)
        }

        fun <T> empty(msg: String): Resource<T> {
            return Resource(Status.EMPTY, null, msg)
        }
    }
}
