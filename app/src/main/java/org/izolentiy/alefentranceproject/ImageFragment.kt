package org.izolentiy.alefentranceproject

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.izolentiy.alefentranceproject.databinding.FragmentImageBinding

class ImageFragment : Fragment() {
   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      val binding = FragmentImageBinding.inflate(inflater, container, false)
      binding.apply {
         Glide.with(requireContext())
            .load(arguments?.getString(IMAGE_URL))
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(imageViewPicture)
      }

      // Enter fullscreen mode
      (requireActivity() as AppCompatActivity).supportActionBar?.hide()
      @Suppress("DEPRECATION")
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
         requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
      else
         requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

      return binding.root
   }

   override fun onDetach() {
      super.onDetach()
      // Exit fullscreen mode
      (requireActivity() as AppCompatActivity).supportActionBar?.show()
      @Suppress("DEPRECATION")
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
         requireActivity().window.insetsController?.show(WindowInsets.Type.statusBars())
      else
         requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
   }

   companion object {
      const val IMAGE_URL = "image_url"

      fun newInstance(imageUrl: String) = ImageFragment().apply {
         arguments = Bundle().apply { putString(IMAGE_URL, imageUrl) }
      }
   }
}