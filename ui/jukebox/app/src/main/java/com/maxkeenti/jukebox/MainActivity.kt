package com.maxkeenti.jukebox

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.maxkeenti.jukebox.ui.theme.JukeboxTheme

// Data class to keep your items organized
data class JukeboxItem(val label: String, val imageRes: Int, val soundRes: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Sample Data - Replace with your actual resource IDs
        val items = listOf(
            JukeboxItem("Anakin", R.drawable.anakin, R.raw.anakin_1),
            JukeboxItem("Chewie", R.drawable.chewie, R.raw.chewie),
            JukeboxItem("Han", R.drawable.han, R.raw.han),
            JukeboxItem("Lando", R.drawable.lando, R.raw.lando),
            JukeboxItem("Luke", R.drawable.luke, R.raw.luke),
            JukeboxItem("Saber", R.drawable.saber, R.raw.saber),
            JukeboxItem("Vader", R.drawable.vader, R.raw.vader),
            JukeboxItem("Yoda", R.drawable.yoda, R.raw.yoda),
            JukeboxItem("Gsatito", R.drawable.gatito, R.raw.message_1)
        )

        setContent {
            JukeboxTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    JukeboxGrid(items, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun JukeboxGrid(items: List<JukeboxItem>, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Sets the 3-column layout
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // The Text Label
                Text(text = item.label, modifier = Modifier.padding(bottom = 8.dp))

                // The Image (Clickable)
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.label,
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            // Initialize and play sound
                            val mediaPlayer = MediaPlayer.create(context, item.soundRes)
                            mediaPlayer.setOnCompletionListener { it.release() } // Free memory when done
                            mediaPlayer.start()
                        }
                )
            }
        }
    }
}