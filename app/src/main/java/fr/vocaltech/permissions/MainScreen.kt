package fr.vocaltech.permissions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.vocaltech.permissions.ui.theme.PermissionsTheme

@Composable
fun MainScreen(navController: NavHostController?) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController?.navigate(Route.DEFAULT) }) {
            Text(text = "Built-in permissions requester")
        }

        Button(
            onClick = { navController?.navigate(Route.ACCOMPANIST) },
            modifier = Modifier
        ) {
            Text(text = "Accompanist permissions")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PermissionsTheme {
        MainScreen(null)
    }
}