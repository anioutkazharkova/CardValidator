package network.json

import jdk.nashorn.api.scripting.ScriptObjectMirror
import java.io.IOException
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class JsonMapper {
    private var engine: ScriptEngine? = null

    companion object {
        private var mapper: JsonMapper? = null

        fun instance(): JsonMapper {
           if (mapper == null) {
               mapper = JsonMapper()
           }
            return mapper!!
        }
    }

    private  constructor() {
        initEngine()
    }

    fun initEngine() {
        val sem = ScriptEngineManager()
        this.engine = sem.getEngineByName("javascript")
    }

    @Throws(IOException::class, ScriptException::class)
    fun <T : Any> parseJson(jsonString: String, clazz: Class<T>): T? {
        val script = "Java.asJSONCompatible($jsonString)"
        val jsonResult = this.engine!!.eval(script)

        val jsonMap = jsonResult as Map<*, *>
        try {
            val output = clazz.newInstance()
            jsonMap.forEach { key, value ->
                setFieldValue(output, key.toString(), value)

            }
            return output
        } catch (e: Exception) {
            print(e.message)
        }

        return null
    }

  private  fun setFieldValue(currentObject: Any, fieldName: String, fieldValue: Any?): Boolean {
        var clazz: Class<*>? = currentObject.javaClass
        while (clazz != null) {
            try {
                var field = clazz.getDeclaredField(fieldName)
                field.isAccessible = true
                if (fieldValue is ScriptObjectMirror) {
                    val complex = field.type.newInstance()
                    (fieldValue as Map<*,*>).forEach{t,u ->
                        setFieldValue(complex,t.toString(),u)

                    }
                    field.set(currentObject,complex)
                }else
                field.set(currentObject, fieldValue)
                return true
            } catch (e: NoSuchFieldException) {
                clazz = clazz.superclass
            } catch (e: Exception) {
                throw IllegalStateException(e)
            }

        }
        return false
    }

}