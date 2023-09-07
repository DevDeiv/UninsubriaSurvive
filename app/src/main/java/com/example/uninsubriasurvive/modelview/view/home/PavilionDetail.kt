package com.example.uninsubriasurvive.modelview.view.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.uninsubriasurvive.database.entity.Pavilion

@Composable
fun PavilionDetailsScreen(
    pavilion: Pavilion,
    context: Context
) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth().size(35.dp)
    ) {
        Button(onClick = { val mapIntentUri = "https://www.google.com/maps/dir/?api=1&destination=${pavilion.address}"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapIntentUri))
            intent.setPackage("com.google.android.apps.maps")
            ContextCompat.startActivity(
                context,
                intent,
                null
            )
        }) {
            Text(text = "NAVIGA")
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(26.dp)
            .fillMaxSize()
    ){

        Text(
            text = "Padiglione ${pavilion.name}",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp
            )
        )
        Text(
            text = pavilion.address,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        Spacer(modifier = Modifier.height(22.dp))
        LazyColumn()
        {
            items(pavilion.timeline){ item: String ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Normal
                    )
                )

            }
        }


    }

}