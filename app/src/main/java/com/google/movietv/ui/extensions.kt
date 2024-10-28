package com.google.movietv.ui

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


fun ProgressBar.show() {
    isVisible = true
}

fun ProgressBar.hide() {
    isVisible = false
}

fun Activity.checkPermission(vararg permission: String): Boolean {
    return permission.all { checkSelfPermission(it) == PERMISSION_GRANTED }
}

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun Double.round(): Double {
    val rounded = (Math.round(this * 10) / 10.0)
    return rounded
}

fun Context.getBitmapFromVector(@DrawableRes vectorResId: Int): BitmapDescriptor {
    val scale = 1.5f

    val vectorDrawable = ContextCompat.getDrawable(
        this, vectorResId
    )!!

    val width = (vectorDrawable.intrinsicWidth * scale).toInt()
    val height = (vectorDrawable.intrinsicHeight * scale).toInt()

    vectorDrawable.setBounds(
        0, 0, width, height
    )

    val bitmap = Bitmap.createBitmap(
        width, height, Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
