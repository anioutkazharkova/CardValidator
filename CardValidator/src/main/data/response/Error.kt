package data.response


class Error() {

    var type: ErrorType? = ErrorType.NOT_FOUND
    var message: String = ""

    constructor(code: Int) : this() {
        when (code) {
            404 -> type = ErrorType.NOT_FOUND
            429 -> type = ErrorType.LIMIT_TIMEOUT
            else -> type = ErrorType.SERVER
        }
    }
}