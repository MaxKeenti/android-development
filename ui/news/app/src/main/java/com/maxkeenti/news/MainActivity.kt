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
        title = "Heute ist Freitag",
        author = "Ich",
        date = "Es ist Freitag, 20. März 2026",
        content = "Ich denke dass diesem app in meinem Handy ist Lustig!")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsTheme {
        News(
            title = "Heute ist Freitag",
            author = "Ich",
            date = "Es ist Freitag, 20. März 2026",
            content = "Ich denke dass diesem app in meinem Handy ist Lustig!")
    }
}