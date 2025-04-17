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

private var clock: ImageVector? = null

internal val Icons.Clock: ImageVector
    get() {
        if (clock != null) {
            return clock!!
        }
        clock = ImageVector.Builder(
            name = "Clock",
            defaultWidth = 15.dp,
            defaultHeight = 15.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(7.50009f, 0.877014f)
                curveTo(3.8424f, 0.877f, 0.8773f, 3.8422f, 0.8773f, 7.4998f)
                curveTo(0.8773f, 11.1575f, 3.8424f, 14.1227f, 7.5001f, 14.1227f)
                curveTo(11.1578f, 14.1227f, 14.1229f, 11.1575f, 14.1229f, 7.4998f)
                curveTo(14.1229f, 3.8422f, 11.1577f, 0.877f, 7.5001f, 0.877f)
                close()
                moveTo(1.82726f, 7.49984f)
                curveTo(1.8273f, 4.3668f, 4.3671f, 1.827f, 7.5001f, 1.827f)
                curveTo(10.6331f, 1.827f, 13.1729f, 4.3668f, 13.1729f, 7.4998f)
                curveTo(13.1729f, 10.6328f, 10.6331f, 13.1727f, 7.5001f, 13.1727f)
                curveTo(4.3671f, 13.1727f, 1.8273f, 10.6328f, 1.8273f, 7.4998f)
                close()
                moveTo(8f, 4.50001f)
                curveTo(8f, 4.2239f, 7.7761f, 4f, 7.5f, 4f)
                curveTo(7.2239f, 4f, 7f, 4.2239f, 7f, 4.5f)
                verticalLineTo(7.50001f)
                curveTo(7f, 7.6326f, 7.0527f, 7.7598f, 7.1464f, 7.8536f)
                lineTo(9.14645f, 9.85357f)
                curveTo(9.3417f, 10.0488f, 9.6583f, 10.0488f, 9.8536f, 9.8536f)
                curveTo(10.0488f, 9.6583f, 10.0488f, 9.3417f, 9.8536f, 9.1465f)
                lineTo(8f, 7.29291f)
                verticalLineTo(4.50001f)
                close()
            }
        }.build()
        return clock!!
    }
