package com.kevin.covershadow.widget

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kevin.covershadow.R
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


/**
 * Create by KevinTu on 2018/8/14
 */
class CoverView : RelativeLayout {

    private val coverImg: ImageView by lazy {
        findViewById<ImageView>(R.id.cover_img)
    }
    private val coverBgImg: ImageView by lazy {
        findViewById<ImageView>(R.id.cover_bg_img)
    }

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_audio_cover, this, true)

    }

    fun refreshView(coverUrl: String) {
        Glide.with(context)
                .load(coverUrl)
                .asBitmap()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(RoundedCornersTransformation(context, 30, 0))
                .listener(object : RequestListener<String, Bitmap> {
                    override fun onException(e: Exception?, model: String?,
                                             target: Target<Bitmap>?,
                                             isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(bitmap: Bitmap, model: String,
                                                 target: Target<Bitmap>, isFromMemoryCache: Boolean,
                                                 isFirstResource: Boolean): Boolean {
                        var realHeight = resources.getDimensionPixelOffset(R.dimen.cover_img_height)
                        var realWidth = bitmap.width * realHeight / bitmap.height
                        var layoutParams = RelativeLayout.LayoutParams(realWidth, realHeight)
                        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
                        coverImg.scaleType = ImageView.ScaleType.FIT_XY
                        coverImg.layoutParams = layoutParams

                        val color = bitmap.getPixel(bitmap.width / 8 * 5, 0)
                        createCoverBgImg(color, realWidth, realHeight)
                        return false
                    }
                })
                .into(coverImg)
    }

    private fun createCoverBgImg(color: Int, width: Int, height: Int) {
        var shadeWidth = resources.getDimensionPixelOffset(R.dimen.cover_bg_shade_width) * 2
        var layoutParams = RelativeLayout.LayoutParams(width + shadeWidth, height + shadeWidth)
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        coverBgImg.layoutParams = layoutParams
        var resourceId = R.mipmap.icon_cover_bg_square
        var viewAspectRatio = width.toFloat() / height.toFloat()
        if (viewAspectRatio <= 0.85F || viewAspectRatio >= 1.15F) {
            resourceId = R.mipmap.icon_cover_bg_rectangle
        }
        coverBgImg.setImageResource(resourceId)
        coverBgImg.scaleType = ImageView.ScaleType.CENTER_CROP
        coverBgImg.setColorFilter(color)
    }
}