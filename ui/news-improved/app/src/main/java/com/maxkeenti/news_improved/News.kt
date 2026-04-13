package com.maxkeenti.news_improved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun News(title: String, author: String, date: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD0D0D0)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            // Black title header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
            }

            // Body: two white inner cards side by side
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Left card: author + date (40% width)
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(end = 4.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            color = Color.Black,
                            text = buildAnnotatedString {
                                append("Publicado por:\n")
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) {
                                    append(author)
                                }
                            }
                        )
                        Text(
                            color = Color.Black,
                            text = buildAnnotatedString {
                                append("\nEl:\n")
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) {
                                    append(date)
                                }
                            }
                        )
                    }
                }

                // Right card: content (60% width)
                Card(
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        .padding(start = 4.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Text(
                        text = content,
                        modifier = Modifier.padding(8.dp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}
