package network

import data.response.ContentResponse
import java.io.IOException

interface INetworkClient {

    @Throws(IOException::class)
    fun <T : Any> requestGet(
        clazz: Class<T>,
        path: String): ContentResponse<T>

    @Throws(IOException::class)
    fun <T : Any> requestGet(
        clazz: Class<T>,
        path: String,
        params: HashMap<String, String>?): ContentResponse<T>

}