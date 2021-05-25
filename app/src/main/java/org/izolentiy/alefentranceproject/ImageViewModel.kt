package org.izolentiy.alefentranceproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.izolentiy.alefentranceproject.ServerResponse.Image
import ru.gildor.coroutines.okhttp.await

class ImageViewModel : ViewModel() {

   private val _images = MutableLiveData<List<Image>>()
   val images: LiveData<List<Image>> get() = _images

   fun fetchImages() = viewModelScope.launch(Dispatchers.IO) {
      val request = Request.Builder()
         .url("http://dev-tasks.alef.im/task-m-001/list.php").build()
      val response = OkHttpClient().newCall(request).await()

      val json = response.body!!.string()
      val gson = GsonBuilder()
         .registerTypeAdapter(ServerResponse::class.java, ServerResponseDeserializer()).create()

      _images.postValue(gson.fromJson(json, ServerResponse::class.java).urls)
   }

}