package com.edurda77.test.workmate.ui.uikit

import com.edurda77.test.workmate.R
import com.edurda77.test.workmate.domain.utils.DataError
import com.edurda77.test.workmate.domain.utils.ResultWork
import com.edurda77.test.workmate.ui.uikit.UiText.StringResource

fun DataError.asUiText(): UiText {
    return when (this) {

        DataError.LocalDateBase.ERROR_READ_DATA -> {
            StringResource(
                R.string.error_read
            )
        }
        DataError.LocalDateBase.ERROR_WRITE_DATA -> {
            StringResource(
                R.string.error_write
            )
        }
        DataError.Network.SERVER_ERROR -> {
            StringResource(
                R.string.server_error
            )
        }
        DataError.Network.BAD_REQUEST -> {
            StringResource(
                R.string.bad_request
            )
        }
        DataError.Network.REQUEST_TIMEOUT -> {
            StringResource(
                R.string.request_timeout
            )
        }
        DataError.Network.UNKNOWN -> {
            StringResource(
                R.string.unknown_error
            )
        }
        DataError.SerializationError.FORMAT_ERROR -> {
            StringResource(
                R.string.format_error
            )
        }
    }
}

fun ResultWork.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}