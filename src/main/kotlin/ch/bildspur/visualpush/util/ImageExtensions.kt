package ch.bildspur.visualpush.util

import processing.core.PGraphics
import processing.core.PImage

fun  PGraphics.centerImageAdjusted(img: PImage) {
    val scaleFactor: Float = if (img.height > img.width)
        this.height.toFloat() / img.height
    else
        this.width.toFloat() / img.width

    this.centerImage(img, scaleFactor * img.width, scaleFactor * img.height)
}

fun PGraphics.centerImage(img: PImage) {
    this.centerImage(img, img.width.toFloat(), img.height.toFloat())
}


fun  PGraphics.centerImage(img: PImage, width: Float, height: Float) {
    this.image(img, this.width / 2.0f - width / 2.0f, this.height / 2.0f - height / 2.0f, width, height)
}