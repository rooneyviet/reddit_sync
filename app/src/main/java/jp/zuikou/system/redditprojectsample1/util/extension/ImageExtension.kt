package jp.zuikou.system.redditprojectsample1.util.extension

import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun ImageView.loadImage(imageUrl: String){
    Glide.with(this.context)
        .load(imageUrl)
        .into(this)
}

fun ImageView.loadImageCircle(imageUrl: String){
    Glide.with(this.context)
        .load(imageUrl)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun ImageView.loadImage(imageUri: Uri){
    Glide.with(this.context)
        .load(imageUri)
        .into(this)
}

fun ImageView.loadImage(resId: Int){
    Glide.with(this.context)
        .load(resId)
        .into(this)
}
