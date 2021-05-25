package org.izolentiy.alefentranceproject

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.izolentiy.alefentranceproject.ServerResponse.Image
import java.lang.reflect.Type

class ServerResponseDeserializer : JsonDeserializer<ServerResponse> {
   override fun deserialize(
      json: JsonElement?,
      typeOfT: Type?,
      context: JsonDeserializationContext?
   ) = ServerResponse(
      urls = json?.asJsonArray?.map { Image(it.asString) } ?: emptyList()
   )
}