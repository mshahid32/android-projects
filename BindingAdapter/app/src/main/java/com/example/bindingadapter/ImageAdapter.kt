package com.example.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
@BindingAdapter("loadImage")
fun ImageView.loadImage(url : String){
    Glide.with(this.context).load(url).into(this)
}