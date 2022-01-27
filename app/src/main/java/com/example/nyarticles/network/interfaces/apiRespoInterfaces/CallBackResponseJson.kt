package  com.example.nyarticles.network.interfaces.apiRespoInterfaces

import org.json.JSONObject

interface CallBackResponseJson {

    fun onSuccessResponse(result: JSONObject)
}