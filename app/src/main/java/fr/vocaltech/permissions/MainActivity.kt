package fr.vocaltech.permissions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.vocaltech.permissions.ui.theme.PermissionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PermissionsTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationController(navController)
                }
            }
        }
    }
}

object Route {
    const val MAIN = "main"
    const val DEFAULT = "default"
    const val ACCOMPANIST = "accompanist"
}

@Composable
fun NavigationController(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.MAIN,
        modifier = Modifier
    ) {
        composable(Route.MAIN) {
            MainScreen(navController)
        }

        composable(Route.DEFAULT) {
            BuiltInPermissionsScreen()
        }
        composable(Route.ACCOMPANIST) {
            AccompanistPermissionsScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationControllerPreview() {
    PermissionsTheme {
        val navController = rememberNavController()

        NavigationController(navController)
    }
}