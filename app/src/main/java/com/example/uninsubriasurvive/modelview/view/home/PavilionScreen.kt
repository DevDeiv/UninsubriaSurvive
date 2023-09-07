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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.uninsubriasurvive.database.entity.Pavilion
import com.example.uninsubriasurvive.modelview.model.pavilion.PavilionState

@Composable
fun PavilionScreen(
    navController: NavController,
    pavilionState: PavilionState
) {

    val context = LocalContext.current
    Column (
        modifier = Modifier.fillMaxSize().padding(vertical = 26.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){



        LazyColumn(

        ) {
            items(pavilionState.pavilions) { item : Pavilion -> 
                ShowPavilion(
                    pavilion = item,
                    context = context,
                    navController = navController
                )
            }
        }

    }

}

@Composable
fun ShowPavilion(
    navController: NavController,
    pavilion: Pavilion,
    context: Context
) {
    
    androidx.compose.material.Card (
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = CircleShape.copy(all = CornerSize(18.dp))

    ){
        Row(
            modifier = Modifier.width(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Padiglione",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                )
                Text(
                    text = pavilion.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    softWrap = true
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = pavilion.address,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

            }
        }
        Row (
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){

          Column {
              TextButton(onClick = { navController.navigate("pavilion_details/${pavilion.id}") }) {
                  Text(text = "MOSTRA ORARI")
              }

              Button(
                  onClick = {
                      val mapIntentUri = "https://www.google.com/maps/dir/?api=1&destination=${pavilion.address}"

                      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapIntentUri))
                      intent.setPackage("com.google.android.apps.maps")
                      startActivity(
                            context,
                            intent,
                          null
                      )
                  },
                  modifier = Modifier.scale(0.8F)
              ) {
                  Text(text = "NAVIGA")
              }
          }

        }
        
    }
    
}