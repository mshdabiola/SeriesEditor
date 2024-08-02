package com.mshdabiola.designsystem.component

import android.Manifest
import android.content.pm.PackageManager
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import java.io.File


@Composable
actual fun GetFilePath(
    onFile: (File?) -> Unit,
) {
    val context = LocalContext.current
//
    LaunchedEffect(Unit) {

        val file = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOCUMENTS,
        ).parentFile
        val file2 = context.getExternalFilesDir(null)
        val file3 = ContextCompat.getExternalFilesDirs(context, null)[0]
        println("file path ${file?.absolutePath}")
        if (file == null)
            onFile(null)
        else
            onFile(File(file, "series"))

    }


}


@Composable
actual fun PermissionDialog(
    modifier: Modifier,
    onDismiss: () -> Unit,
    onFile: (File?) -> Unit,
) {
    // var showDialog by remember { mutableStateOf(false) }
    //  var permissionGranted by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
          val file=  Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS,
            ).parentFile
            onFile(File(file, "series"))
        } else {
            onDismiss()
        }
    }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("File Access Required") },
        text = { Text("This app needs permission to access files on your device.") },
        confirmButton = {
            Button(
                onClick = {
                    launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                },
            ) {
                Text("Grant Permission")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Deny")
            }
        },
    )


}
//
//
//@Composable
//fun File(modifier: Modifier = Modifier) {
//  val ssls = rememberLauncherForActivityResult(
//      contract = ActivityResultContracts.StartActivityForResult(),
//  ) {
//      it
//  }
//}
//
//@Composable
//fun RequestStoragePermission() {
//    var permissionGranted by remember { mutableStateOf(false) }
//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission(),
//    ) { isGranted: Boolean ->
//        permissionGranted = isGranted
//    }
//
//    if (permissionGranted) {
//        Text("Storage permission granted!")
//        // Proceed with file access
//    } else {
//        Button(onClick = { launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE) }) {
//            Text("Request storage permission")
//        }
//    }
//}
//
//@Composable
//fun hasWritePermission(): Boolean {
//    val context = LocalContext.current
//    return ContextCompat.checkSelfPermission(
//        context,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//    ) == PackageManager.PERMISSION_GRANTED
//}
//
//@Composable
//fun MyComposable() {
//    if (hasWritePermission()) {
//        // Write permission is granted, proceed with file writing operations
//    } else {
//        // Write permission is not granted, handle accordingly (e.g., request permission)
//    }
//}
//
//@Composable
//fun getExternalStorageUriForFile(fileName: String): File? {
//    val context = LocalContext.current
//    return context.getExternalFilesDir(null)?.let { directory ->
//        File(directory, fileName)
//    }
//}
@Composable
actual fun HasWrittenPermission(result: (Boolean) -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        result(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }

}