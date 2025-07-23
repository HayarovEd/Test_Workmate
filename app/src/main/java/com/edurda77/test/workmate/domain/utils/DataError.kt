package com.edurda77.test.workmate.domain.utils

sealed interface DataError : RootError {
    enum class Network: DataError {
        SERVER_ERROR,
        BAD_REQUEST,
        REQUEST_TIMEOUT,
        UNKNOWN
    }

    enum class SerializationError: DataError {
       FORMAT_ERROR,
    }

    enum class LocalDateBase : DataError {
        ERROR_READ_DATA,
        ERROR_WRITE_DATA
    }
}