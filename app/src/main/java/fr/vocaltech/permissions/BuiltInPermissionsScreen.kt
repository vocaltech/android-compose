package fr.vocaltech.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import fr.vocaltech.permissions.ui.theme.PermissionsTheme

@Composable
fun BuiltInPermissionsScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        // Camera permission
        val cameraPermission = Manifest.permission.CAMERA
        val cameraPermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                // open camera
                Toast.makeText(context, "Cam permission granted !", Toast.LENGTH_SHORT).show()
            } else {
                // show dialog
                Toast.makeText(context, "Cam permission denied !", Toast.LENGTH_SHORT).show()
            }
        }

        Button(onClick = { checkAndRequestCameraPermission(context, cameraPermission, cameraPermissionLauncher) }) {
            Text(text = "Open camera")
        }

        // Location permissions
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val locationPermissionsLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            permissionsMap ->
            val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }

            if (areGranted) {
                // Open location
                Toast.makeText(context, "Location permission granted !", Toast.LENGTH_SHORT).show()
            } else {
                // Show permission dialog
                Toast.makeText(context, "Location permission denied !", Toast.LENGTH_SHORT).show()
            }
        }
        Button(onClick = { checkAndRequestLocationPermissions(context, locationPermissions, locationPermissionsLauncher) }) {
            Text(text = "Use location")
        }
    }
}

fun checkAndRequestCameraPermission(
    context: Context,
    permission: String,
    launcher: ManagedActivityResultLauncher<String, Boolean>
) {
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)

    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        // open camera because permission is already granted
        Toast.makeText(context, "Launching cam screen...", Toast.LENGTH_SHORT).show()
    } else {
        // Request a permission
        launcher.launch(permission)
    }
}

fun checkAndRequestLocationPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    if (permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }) {
        // open location
        Toast.makeText(context, "Launching location screen...", Toast.LENGTH_SHORT).show()
    } else {
        // Request permissions
        launcher.launch(permissions)
    }
}

@Preview(showBackground = true)
@Composable
fun BuiltInPermissionsScreenPreview() {
    PermissionsTheme {
        BuiltInPermissionsScreen()
    }
}