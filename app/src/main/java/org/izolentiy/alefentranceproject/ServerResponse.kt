package org.izolentiy.alefentranceproject

data class ServerResponse(
   val urls: List<Image>
) {
   data class Image(
      val url: String
   )
}
