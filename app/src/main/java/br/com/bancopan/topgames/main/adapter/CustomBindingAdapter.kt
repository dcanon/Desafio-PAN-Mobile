package br.com.bancopan.topgames.main.adapter

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

object CustomBindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImage(imageView: ImageView, imageUrl: String) {
        Picasso.get().load(imageUrl).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("viewers")
    fun setViewers(view: TextView, count: Int?) {
        view.text = "Visualizações: " + count!!
    }

    @JvmStatic
    @BindingAdapter("channels")
    fun setChannels(view: TextView, count: Int?) {
        view.text = "Canais: " + count!!
    }
}
