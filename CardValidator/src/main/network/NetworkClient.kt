package network



import data.response.ContentResponse
import data.response.Error
import network.json.JsonMapper
import network.util.NetworkConfiguration
import network.util.RequestMethod
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class NetworkClient() : INetworkClient {
    private var mapper: JsonMapper? = JsonMapper.instance()

    @Throws(IOException::class)
    override fun <T : Any> requestGet(
        clazz: Class<T>,
        path: String
    ): ContentResponse<T> {
        return requestGet(clazz, path, null)
    }

    @Throws(IOException::class)
    override fun <T : Any> requestGet(
        clazz: Class<T>,
        path: String,
        params: HashMap<String, String>?
    ): ContentResponse<T> {
        return request(clazz, path, RequestMethod.GET, params)
    }


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

    private fun <T : Any> mapContent(clazz: Class<T>, responseString: String): T? {
        val content = mapper?.parseJson(responseString, clazz)
        return content
    }
}




