package dev.rajesh.mobile_banking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun ProfilePicture(
    imageUrl: String?,
    employeeName: String,
    nameInitials: String,
    size: Dp,
    shape: Shape,
    background: Color,
    borderWidth: Dp,
    borderColor: Color,
    ratio: Float,
    onClick: () -> Unit = {}
) {

    //val context = LocalPlatformContext.current
    /*val imageRequest = remember(context, imageUrl){
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build()
    }*/

    ProfilePictureInitialText(
        nameInitials = nameInitials,
        size = size,
        background = background,
        shape = shape,
        borderWidth = borderWidth,
        borderColor = borderColor
    )
}

@Composable
fun ProfilePictureInitialText(
    nameInitials: String,
    size: Dp,
    background: Color,
    shape: Shape,
    borderWidth: Dp,
    borderColor: Color
) {
    Box(
        modifier = Modifier.size(size = size)
            .clip(shape = shape)
            .background(color = background)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = shape
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = nameInitials,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.appColors.primaryTextColor
            )
        )
    }
}