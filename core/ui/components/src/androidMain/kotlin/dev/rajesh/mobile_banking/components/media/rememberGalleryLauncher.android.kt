package dev.rajesh.mobile_banking.components.media

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.io.File
import java.util.UUID
import com.yalantis.ucrop.UCrop

@Composable
actual fun rememberGalleryLauncher(
    onImageSelected: (String) -> Unit,
    onError: (Throwable) -> Unit
): () -> Unit {
    val uCropLauncher = rememberLauncherForActivityResult(
        contract = object : ActivityResultContract<List<Uri>, Uri?>() {
            override fun createIntent(context: Context, input: List<Uri>): Intent {
                val inputUri = input[0]
                val outputUri = Uri.fromFile(
                    File(
                        context.cacheDir,
                        "cropped_image_${UUID.randomUUID()}.jpg"
                    )
                )

                val options = UCrop.Options().apply {
                    setFreeStyleCropEnabled(true)
                    setHideBottomControls(true)
                    setShowCropGrid(true)
                    setShowCropFrame(true)
                    withAspectRatio(1f, 1f)
                }

                return UCrop.of(inputUri, outputUri)
                    .withOptions(options)
                    .getIntent(context)
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
                return if (resultCode == android.app.Activity.RESULT_OK) {
                    intent?.let { UCrop.getOutput(it) }
                } else {
                    // You can also get the error from UCrop.getError(intent)
                    null
                }
            }
        }
    ) { uri: Uri? ->
        if (uri != null) {
            onImageSelected(uri.toString())
        } else {
            onError(Exception("Image cropping failed or was cancelled."))
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            //onImageSelected(uri.toString())
            uCropLauncher.launch(listOf(uri))
        } else {
            onError(Exception("No image selected"))
        }
    }

    return remember {
        {
            try {
                galleryLauncher.launch("image/*")
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}