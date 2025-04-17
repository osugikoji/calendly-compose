package com.calendly.compose.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var cameraVideo: ImageVector? = null

internal val Icons.CameraVideo: ImageVector
    get() {
        if (cameraVideo != null) {
            return cameraVideo!!
        }
        cameraVideo = ImageVector.Builder(
            name = "CameraVideo",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF0F172A)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(15.75f, 10.5f)
                lineTo(20.4697f, 5.78033f)
                curveTo(20.9421f, 5.3079f, 21.75f, 5.6425f, 21.75f, 6.3107f)
                verticalLineTo(17.6893f)
                curveTo(21.75f, 18.3575f, 20.9421f, 18.6921f, 20.4697f, 18.2197f)
                lineTo(15.75f, 13.5f)
                moveTo(4.5f, 18.75f)
                horizontalLineTo(13.5f)
                curveTo(14.7426f, 18.75f, 15.75f, 17.7426f, 15.75f, 16.5f)
                verticalLineTo(7.5f)
                curveTo(15.75f, 6.2574f, 14.7426f, 5.25f, 13.5f, 5.25f)
                horizontalLineTo(4.5f)
                curveTo(3.2574f, 5.25f, 2.25f, 6.2574f, 2.25f, 7.5f)
                verticalLineTo(16.5f)
                curveTo(2.25f, 17.7426f, 3.2574f, 18.75f, 4.5f, 18.75f)
                close()
            }
        }.build()
        return cameraVideo!!
    }

