package jp.zuikou.system.redditprojectsample1.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import jp.zuikou.system.redditprojectsample1.R

fun ImageView.load(url: String, mWidth: Int, mHeight: Int, isGif: Boolean = false, loadOnlyFromCache: Boolean = false, onLoadingFinished: () -> Unit = {}) {
    if (url.isEmpty()){
        Glide.with(this).clear(this)
        return

    }

    val listener = object : RequestListener<Drawable> {

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }
    }

    val gifListener = object : RequestListener<GifDrawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<GifDrawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }

        override fun onResourceReady(
            resource: GifDrawable?,
            model: Any?,
            target: Target<GifDrawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }

    }
    if(isGif){
        Glide.with(this)
            .asGif()
            .load(url)
            .override(mWidth, mHeight)
            .placeholder(R.drawable.image_holder)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).onlyRetrieveFromCache(loadOnlyFromCache))
            .listener(gifListener)
            .into(this)
    } else {
        Glide.with(this)
            .load(url)
            .override(mWidth, mHeight)
            .placeholder(R.drawable.image_holder)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).onlyRetrieveFromCache(loadOnlyFromCache))
            .listener(listener)
            .into(this)
    }
}