package fr.vocaltech.permissions

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AccompanistPermissionsScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.Start
    ) {
        val context = LocalContext.current

        // Camera permission
        val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
        CheckAndRequestCameraPermission(context, cameraPermissionState)

        // Location permission
        val locationPermissionsState = rememberMultiplePermissionsState(
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
        CheckAndRequestLocationPermissions(context, locationPermissionsState)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckAndRequestCameraPermission(context: Context, cameraPermissionState: PermissionState) {
    if (cameraPermissionState.status.isGranted) {
        Toast.makeText(context, "Camera is granted !", Toast.LENGTH_SHORT).show()
    } else {
        Column {
            val textToShow: String = if (cameraPermissionState.status.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "The camera is important for this app. Please grant the permission."
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Camera permission required for this feature to be available. " +
                        "Please grant the permission"
            }

            Text(textToShow)
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Request permission")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckAndRequestLocationPermissions(context: Context, locationPermissionsState: MultiplePermissionsState) {
    if (locationPermissionsState.allPermissionsGranted) {
        Toast.makeText(context, "Location is granted !", Toast.LENGTH_SHORT).show()
    } else {
        Column {
            val allPermissionsRevoked =
                locationPermissionsState.permissions.size == locationPermissionsState.revokedPermissions.size

            val textToShow = if (!allPermissionsRevoked) {
                // If not all the permissions are revoked, it's because the user
                // accepted the COARSE permission but not the FINE one
                "Need to grant the FINE location !"
            } else if (locationPermissionsState.shouldShowRationale) {
                // Both location permissions have been denied
                "Please, grant us FINE location"
            } else {
                // First time, the user sees this feature or the user doesn't want to be asked again
                "This feature requires location permission"
            }

            val buttonText = if (!allPermissionsRevoked) {
                "Allow Precise location"
            } else {
                "Request permissions"
            }

            Text(text = textToShow)
            Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                Text(buttonText)
            }
        }
    }
}