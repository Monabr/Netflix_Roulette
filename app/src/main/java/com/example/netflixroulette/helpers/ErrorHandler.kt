package com.example.netflixroulette.helpers

import android.content.Context
import android.widget.Toast
import com.example.netflixroulette.R
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    private val context: Context
) {

    /**
     * Use this method to display error message
     *
     * @param code error code to display message
     */
    fun showError(code: Int) {
        when (code) {
            BAD_REQUEST -> Toast.makeText(
                context,
                context.getString(R.string.bad_request),
                Toast.LENGTH_LONG
            ).show()
            UNAUTHORIZED -> Toast.makeText(
                context,
                context.getString(R.string.unauthorized),
                Toast.LENGTH_LONG
            ).show()
            PAYMENT_REQUIRED -> Toast.makeText(
                context,
                context.getString(R.string.payment_required),
                Toast.LENGTH_LONG
            ).show()
            FORBIDDEN -> Toast.makeText(
                context,
                context.getString(R.string.forbidden),
                Toast.LENGTH_LONG
            ).show()
            NOT_FOUND -> Toast.makeText(
                context,
                context.getString(R.string.not_found),
                Toast.LENGTH_LONG
            ).show()
            METHOD_NOT_ALLOWED -> Toast.makeText(
                context,
                context.getString(R.string.method_not_allowed),
                Toast.LENGTH_LONG
            ).show()
            NOT_ACCEPTABLE -> Toast.makeText(
                context,
                context.getString(R.string.not_acceptable),
                Toast.LENGTH_LONG
            ).show()
            PROXY_AUTHENTICATION_REQUIRED -> Toast.makeText(
                context,
                context.getString(R.string.proxy_authentication_required),
                Toast.LENGTH_LONG
            ).show()
            REQUEST_TIMEOUT -> Toast.makeText(
                context,
                context.getString(R.string.request_timeout),
                Toast.LENGTH_LONG
            ).show()
            CONFLICT -> Toast.makeText(
                context,
                context.getString(R.string.conflict),
                Toast.LENGTH_LONG
            ).show()
            GONE -> Toast.makeText(
                context,
                context.getString(R.string.gone),
                Toast.LENGTH_LONG
            ).show()
            LENGTH_REQUIRED -> Toast.makeText(
                context,
                context.getString(R.string.length_required),
                Toast.LENGTH_LONG
            ).show()
            PRECONDITION_FAILED -> Toast.makeText(
                context,
                context.getString(R.string.precondition_failed),
                Toast.LENGTH_LONG
            ).show()
            PAYLOAD_TOO_LARGE -> Toast.makeText(
                context,
                context.getString(R.string.payload_too_large),
                Toast.LENGTH_LONG
            ).show()
            URI_TOO_LONG -> Toast.makeText(
                context,
                context.getString(R.string.uri_too_long),
                Toast.LENGTH_LONG
            ).show()
            UNSUPPORTED_MEDIA_TYPE -> Toast.makeText(
                context,
                context.getString(R.string.unsupported_media_type),
                Toast.LENGTH_LONG
            ).show()
            RANGE_NOT_SATISFIABLE -> Toast.makeText(
                context,
                context.getString(R.string.range_not_satisfiable),
                Toast.LENGTH_LONG
            ).show()
            EXPECTATION_FAILED -> Toast.makeText(
                context,
                context.getString(R.string.expectation_failed),
                Toast.LENGTH_LONG
            ).show()
            INTERNAL_SERVER_ERROR -> Toast.makeText(
                context,
                context.getString(R.string.internal_server_error),
                Toast.LENGTH_LONG
            ).show()
            NOT_IMPLEMENTED -> Toast.makeText(
                context,
                context.getString(R.string.not_implemented),
                Toast.LENGTH_LONG
            ).show()
            BAD_GATEWAY -> Toast.makeText(
                context,
                context.getString(R.string.bad_gateway),
                Toast.LENGTH_LONG
            ).show()
            SERVICE_UNAVAILABLE -> Toast.makeText(
                context,
                context.getString(R.string.service_unavailable),
                Toast.LENGTH_LONG
            ).show()
            else -> Toast.makeText(
                context,
                context.getString(R.string.unknown_error),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val PAYMENT_REQUIRED = 402
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val METHOD_NOT_ALLOWED = 405
        const val NOT_ACCEPTABLE = 406
        const val PROXY_AUTHENTICATION_REQUIRED = 407
        const val REQUEST_TIMEOUT = 408
        const val CONFLICT = 409
        const val GONE = 410
        const val LENGTH_REQUIRED = 411
        const val PRECONDITION_FAILED = 412
        const val PAYLOAD_TOO_LARGE = 413
        const val URI_TOO_LONG = 414
        const val UNSUPPORTED_MEDIA_TYPE = 415
        const val RANGE_NOT_SATISFIABLE = 416
        const val EXPECTATION_FAILED = 417

        const val INTERNAL_SERVER_ERROR = 500
        const val NOT_IMPLEMENTED = 501
        const val BAD_GATEWAY = 502
        const val SERVICE_UNAVAILABLE = 503
    }

}