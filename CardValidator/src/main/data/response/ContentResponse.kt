package data.response


class ContentResponse<T:Any> {

    var content:T? = null
    var error: Error? = null
}