package com.akash.beautifulbhaluka.views.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LinkCard(modifier: Modifier = Modifier, navController: NavHostController? = null, link: String, text: String, icon: Int) {
    Column(
        modifier = modifier
            .drawBehind {
                val shadowColor = Color.Black.copy(alpha = 0.2f)
                val offsetX = 1.dp.toPx()
                val offsetY = 2.dp.toPx()
                val blur = 4.dp.toPx()

                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = shadowColor
                        asFrameworkPaint().setMaskFilter(
                            android.graphics.BlurMaskFilter(blur, android.graphics.BlurMaskFilter.Blur.NORMAL)
                        )
                    }
                    canvas.drawRoundRect(
                        left = offsetX,
                        top = offsetY,
                        right = size.width + offsetX,
                        bottom = size.height + offsetY,
                        radiusX = 20.dp.toPx(),
                        radiusY = 20.dp.toPx(),
                        paint = paint
                    )
                }
            }
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .heightIn(min = 128.dp)
            .clickable(enabled = true) {
                if (navController != null) {
                    navController.navigate(link)
                }
            }
            .padding(start = 8.dp, end = 8.dp, bottom = 10.dp, top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = "Logo",
                modifier = Modifier.size(64.dp)
            )
        }
        Text(
            text = text,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}