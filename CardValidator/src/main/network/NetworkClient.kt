package network



import data.response.ContentResponse
import data.response.Error
import network.json.JsonMapper
import network.util.NetworkConfiguration
import network.util.RequestMethod
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

/**
Native network client implementation
*/
class NetworkClient() : INetworkClient {

    //Json native mapper
    private var mapper: JsonMapper? = JsonMapper.instance()

    /**
     * @param clazz - type of result
     * @param path - url path of request
     * */
    @Throws(IOException::class)
    override fun <T : Any> requestGet(
        clazz: Class<T>,
        path: String
    ): ContentResponse<T> {
        return requestGet(clazz, path, null)
    }

    /**
     * @param clazz - type of result
     * @param path - url path of request
     * @param params - parameters (body) of request
     * */
    @Throws(IOException::class)
    override fun <T : Any> requestGet(
        clazz: Class<T>,
        path: String,
        params: HashMap<String, String>?
    ): ContentResponse<T> {
        return request(clazz, path, RequestMethod.GET, params)
    }

    /**
     * Common method for network request
     * @param clazz - type of result
     * @param path - url path of request
     * @param method - HTTP method of request
     * @param params - parameters (body) of request
     * */
    @Throws(IOException::class)
    private fun <T : Any> request(
        clazz: Class<T>,
        path: String,
        method: RequestMethod,
        params: HashMap<String, String>? = null
    ): ContentResponse<T> {
        var contentResponse = ContentResponse<T>()

        val requestUrl = URL("${NetworkConfiguration.BaseURL}${path}")
        var readLine: String? = null
        val conection = requestUrl.openConnection() as HttpURLConnection
        conection.requestMethod = method.value

        if (params != null) {
            for (k in params.keys) {
                conection.setRequestProperty(k, params.getValue(k))
            }
        }

        val responseCode = conection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val input = BufferedReader(
                InputStreamReader(conection.inputStream)
            )

            val response = StringBuffer()
            do {
                readLine = input.readLine()
                if (readLine != null) {
                    response.append(readLine)
                }
            } while (readLine != null)

            input.close()

            contentResponse.content = mapContent(clazz, response.toString())
        } else {
            contentResponse.error = Error(responseCode)
        }
        return contentResponse
    }

    /**
     * Mapping content with native mapper
     * */
    private fun <T : Any> mapContent(clazz: Class<T>, responseString: String): T? {
        val content = mapper?.parseJson(responseString, clazz)
        return content
    }
}




