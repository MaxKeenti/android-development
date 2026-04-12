package com.maxkeenti.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.maxkeenti.news.ui.theme.NewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UIMainActivity()
        }
    }
}

@Composable
fun UIMainActivity() {
    News(
        title = "Article Title",
        author = "Author Name",
        date = "March 20, 2026",
        content = "Article content goes here. This is a placeholder demonstrating the News card layout.")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsTheme {
        News(
            title = "Article Title",
            author = "Author Name",
            date = "March 20, 2026",
            content = "Article content goes here. This is a placeholder demonstrating the News card layout.")
    }
}