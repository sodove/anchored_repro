import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

import anchored_repro.composeapp.generated.resources.Res
import anchored_repro.composeapp.generated.resources.compose_multiplatform
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

enum class DrawerState {
    Opened,
    Closed,
}

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }

            val density = LocalDensity.current
            val drawerWidth = 300.dp
            val startAnchor = with(density) { drawerWidth.toPx() }
            val state = remember {
                AnchoredDraggableState(
                    initialValue = DrawerState.Closed,
                    anchors = DraggableAnchors {
                        DrawerState.Opened at -startAnchor
                        DrawerState.Closed at 0f
                    },
                    positionalThreshold = { distance: Float -> distance * 0.5f },
                    velocityThreshold = { with(density) { 100.dp.toPx() } },
                    animationSpec = tween(),
                )
            }
            println(state.offset)
        }
    }
}