package data.response

enum class ErrorType(val code: Int) {
    NOT_FOUND(404),
    LIMIT_TIMEOUT(429),
    SERVER(500)
}