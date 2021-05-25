package org.izolentiy.alefentranceproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      if (supportFragmentManager.backStackEntryCount == 0)
         supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ImageListFragment()).commit()
   }
}