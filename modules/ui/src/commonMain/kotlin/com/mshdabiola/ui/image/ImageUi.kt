package com.mshdabiola.ui.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import java.io.File

@Composable
fun ImageUi(
    modifier: Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val filePath = File(path)
    when (filePath.extension) {
        "svg" -> {
            AsyncImage(
                modifier = modifier,
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(path)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = contentDescription,
                contentScale = contentScale,
            )
        }

        else -> {
            AsyncImage(
                modifier = modifier,
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(path)
                    .build(),
                contentDescription = contentDescription,
                contentScale = contentScale,
            )
        }
    }
}
//
// expect fun loadSvgPainter(file: File, density: Density): Painter
// expect fun loadSvgPainter1(file: File, density: Density): ImageBitmap?
//
// // fun loadSvgPainter2(file : File, density: Density) : ImageVector {
// //    return loadSvgPainter1(file, density)
// // }
//
// expect fun loadXmlImageVector(file: File, density: Density): ImageVector
// expect fun loadImageBitmap(file: File): ImageBitmap?
