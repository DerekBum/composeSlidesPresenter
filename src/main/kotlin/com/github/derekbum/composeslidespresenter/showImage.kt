package com.github.derekbum.composeslidespresenter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap

@Composable
fun App() {
    MaterialTheme {

        Column(Modifier.fillMaxSize()) {

            val imageModifier = Modifier
                .fillMaxSize()

            val imageBitmap: ImageBitmap = remember(presentationSlidePath.value) {
                loadImageBitmap(presentationSlidePath.value.inputStream())
            }

            Image(
                painter = BitmapPainter(image = imageBitmap),
                contentDescription = "Slide",
                modifier = imageModifier
            )
        }

    }
}