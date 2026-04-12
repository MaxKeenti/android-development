package com.maxkeenti.news_improved

import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeenti.news_improved.ui.theme.NewsimprovedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsimprovedTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NewsImprovedScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NewsImprovedScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val deviceId = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )
    val newsList = List(10) { i ->
        Triple(
            "Breaking News ${i + 1}",
            "Author ${i + 1}",
            "2025-0${(i % 9) + 1}-01"
        )
    }
    Column(modifier = modifier.fillMaxSize().padding(8.dp)) {
        Text(text = "Device ID: $deviceId", modifier = Modifier.padding(bottom = 8.dp))
        LazyColumn {
            items(newsList) { (title, author, date) ->
                News(
                    title = title,
                    author = author,
                    date = date,
                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsImprovedPreview() {
    NewsimprovedTheme {
        NewsImprovedScreen()
    }
}
