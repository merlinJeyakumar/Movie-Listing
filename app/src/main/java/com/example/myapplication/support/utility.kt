package com.example.myapplication.support

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import okio.buffer
import okio.source
import java.io.IOException
import java.nio.charset.Charset


fun Context.readJsonFromAssets(filePath: String): String? {
    try {
        val source = assets.open(filePath).source().buffer()
        return source.readByteString().string(Charset.forName("utf-8"))

    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

fun Context.loadImageFromAssets(fileName: String, imageView: ImageView): Boolean {
    return try {
        val inputStream = assets.open(fileName)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        Glide.with(imageView)
            .load(bitmap)
            .placeholder(R.drawable.placeholder_for_missing_posters)
            .into(imageView)
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}