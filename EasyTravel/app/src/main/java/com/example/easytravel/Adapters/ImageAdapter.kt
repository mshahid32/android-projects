package com.example.easytravel.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.easytravel.R
import com.google.android.material.snackbar.Snackbar


class ImageAdapter internal constructor(var list: List<Int>) : PagerAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =
            LayoutInflater.from(container.context).inflate(R.layout.image_layout, container, false)
        val image = view.findViewById<ImageView>(R.id.imageView)
        image.setImageResource(list[position])
        image.setOnClickListener {
            Snackbar.make(view, "Image$position", Snackbar.LENGTH_LONG).show()
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
