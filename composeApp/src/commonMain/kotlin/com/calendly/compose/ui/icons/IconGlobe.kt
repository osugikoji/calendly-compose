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

private var globe: ImageVector? = null

internal val Icons.Globe: ImageVector
    get() {
        if (globe != null) {
            return globe!!
        }
        globe = ImageVector.Builder(
            name = "Globe",
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
                moveTo(6.11507f, 5.19043f)
                lineTo(6.4339f, 7.10337f)
                curveTo(6.6395f, 8.3369f, 7.2253f, 9.4754f, 8.1096f, 10.3596f)
                lineTo(9.75f, 12f)
                lineTo(9.36262f, 12.7747f)
                curveTo(9.1461f, 13.2079f, 9.231f, 13.731f, 9.5734f, 14.0734f)
                lineTo(10.9205f, 15.4205f)
                curveTo(11.1315f, 15.6315f, 11.25f, 15.9176f, 11.25f, 16.216f)
                verticalLineTo(17.3047f)
                curveTo(11.25f, 17.7308f, 11.4908f, 18.1204f, 11.8719f, 18.3109f)
                lineTo(12.0247f, 18.3874f)
                curveTo(12.4579f, 18.6039f, 12.981f, 18.519f, 13.3234f, 18.1766f)
                lineTo(14.0461f, 17.4539f)
                curveTo(15.161f, 16.339f, 15.952f, 14.9419f, 16.3344f, 13.4122f)
                curveTo(16.4357f, 13.0073f, 16.2962f, 12.5802f, 15.9756f, 12.313f)
                lineTo(14.6463f, 11.2053f)
                curveTo(14.3947f, 10.9956f, 14.0642f, 10.906f, 13.7411f, 10.9598f)
                lineTo(12.5711f, 11.1548f)
                curveTo(12.2127f, 11.2146f, 11.8475f, 11.0975f, 11.5906f, 10.8406f)
                lineTo(11.2955f, 10.5455f)
                curveTo(10.8562f, 10.1062f, 10.8562f, 9.3938f, 11.2955f, 8.9545f)
                lineTo(11.4266f, 8.82336f)
                curveTo(11.769f, 8.4809f, 12.2921f, 8.3961f, 12.7253f, 8.6126f)
                lineTo(13.3292f, 8.91459f)
                curveTo(13.4415f, 8.9708f, 13.5654f, 9f, 13.691f, 9f)
                curveTo(14.2924f, 9f, 14.6835f, 8.3671f, 14.4146f, 7.8292f)
                lineTo(14.25f, 7.5f)
                lineTo(15.5057f, 6.66289f)
                curveTo(16.1573f, 6.2285f, 16.6842f, 5.6316f, 17.0344f, 4.9311f)
                lineTo(17.1803f, 4.63942f)
                moveTo(6.11507f, 5.19043f)
                curveTo(4.2072f, 6.8407f, 3f, 9.2794f, 3f, 12f)
                curveTo(3f, 16.9706f, 7.0294f, 21f, 12f, 21f)
                curveTo(16.9706f, 21f, 21f, 16.9706f, 21f, 12f)
                curveTo(21f, 8.958f, 19.4908f, 6.2685f, 17.1803f, 4.6394f)
                moveTo(6.11507f, 5.19043f)
                curveTo(7.6929f, 3.8256f, 9.75f, 3f, 12f, 3f)
                curveTo(13.9286f, 3f, 15.7155f, 3.6066f, 17.1803f, 4.6394f)
            }
        }.build()
        return globe!!
    }
