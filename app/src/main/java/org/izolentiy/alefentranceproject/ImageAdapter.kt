package org.izolentiy.alefentranceproject

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.izolentiy.alefentranceproject.ServerResponse.Image
import org.izolentiy.alefentranceproject.databinding.ItemImageBinding as Binding

class ImageAdapter(
   private val spanCount: Int,
   private val onImageClick: (image: Image) -> Unit
) : ListAdapter<Image, ImageAdapter.ViewHolder>(ImageComparator) {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
      Binding.inflate(LayoutInflater.from(parent.context), parent, false)
   )

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   @Suppress("DEPRECATION")
   private fun adjustToSquare(imageView: ImageView, context: Context) {
      val activity = (context as AppCompatActivity)

      imageView.layoutParams.apply {
         val displayWidth =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
               activity.windowManager.currentWindowMetrics.bounds.width()
            else
               activity.windowManager.defaultDisplay.width

         height = displayWidth / spanCount
      }
   }

   inner class ViewHolder(private val binding: Binding) : RecyclerView.ViewHolder(binding.root) {
      init {
         // Adjust the height of imageView to make it square
         adjustToSquare(binding.imageViewPicture, itemView.context)
         binding.root.setOnClickListener { onImageClick(getItem(adapterPosition)) }
      }

      fun bind(image: Image) {
         Glide.with(itemView)
            .load(image.url)
            .fitCenter()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(binding.imageViewPicture)
      }
   }

   object ImageComparator : DiffUtil.ItemCallback<Image>() {
      override fun areItemsTheSame(oldItem: Image, newItem: Image) = oldItem == newItem

      override fun areContentsTheSame(oldItem: Image, newItem: Image) = oldItem == newItem
   }

}