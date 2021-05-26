package org.izolentiy.alefentranceproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import org.izolentiy.alefentranceproject.databinding.FragmentImageListBinding

class ImageListFragment : Fragment() {
   private val onImageClick: (ServerResponse.Image) -> Unit = { image ->
      Log.d("_TAG", "pressed: ${image.url}")

      requireActivity().supportFragmentManager.beginTransaction()
         .replace(R.id.fragment_container, ImageFragment.newInstance(image.url))
         .addToBackStack(null).commit()
   }

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Measure screen there and setup span count
      val spanCount = with(requireContext().resources.displayMetrics) {
         val dpWidth = widthPixels / density
         return@with if (dpWidth > 600) 3 else 2
      }

      val viewModel = ViewModelProvider(this).get(ImageViewModel::class.java)
      val imageAdapter = ImageAdapter(spanCount, onImageClick)

      val binding = FragmentImageListBinding.inflate(inflater, container, false)
      binding.apply {
         swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchImages()
         }
         recyclerViewImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), spanCount)
         }
      }

      if (viewModel.images.value.isNullOrEmpty()) viewModel.fetchImages()
      viewModel.images.observe(viewLifecycleOwner) { images ->
         imageAdapter.apply { submitList(images) }
         binding.swipeRefreshLayout.isRefreshing = false
      }
      return binding.root
   }
}