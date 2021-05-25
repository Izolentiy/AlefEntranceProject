package org.izolentiy.alefentranceproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.izolentiy.alefentranceproject.ServerResponse.Image
import org.izolentiy.alefentranceproject.databinding.ItemImageBinding as Binding

class ImageAdapter(
   private val onImageClick: (image: Image) -> Unit
) : ListAdapter<Image, ImageAdapter.ViewHolder>(ImageComparator) {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
      Binding.inflate(LayoutInflater.from(parent.context), parent, false)
   )

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   inner class ViewHolder(private val binding: Binding) : RecyclerView.ViewHolder(binding.root) {
      init {
         binding.root.setOnClickListener { onImageClick(getItem(adapterPosition)) }
      }

      fun bind(image: Image) {
         Glide.with(itemView)
            .load(image.url)
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