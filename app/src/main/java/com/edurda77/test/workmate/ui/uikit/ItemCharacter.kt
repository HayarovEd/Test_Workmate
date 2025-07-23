package com.edurda77.test.workmate.ui.uikit

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.edurda77.test.workmate.domain.model.CharacterDetails
import com.edurda77.test.workmate.domain.model.Gender
import com.edurda77.test.workmate.domain.model.StatusCharacter
import com.edurda77.test.workmate.ui.theme.background
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Composable
fun ItemCharacter(
    modifier: Modifier = Modifier,
    characterDetails: CharacterDetails,
    onClick: () -> Unit,
) {
    val pointColor = when (characterDetails.status) {
        StatusCharacter.ALIVE -> Color.Green
        StatusCharacter.DEAD -> Color.Red
        StatusCharacter.UNKNOWN -> Color.White
    }
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = background
        ),
        onClick = onClick,
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = modifier.fillMaxWidth()
            ) {
                Log.d("TEST WORKMATE1", "imageUrl ${characterDetails.imageUrl}")
                AsyncImage(
                    modifier = modifier.fillMaxWidth(),
                    model = "https://rickandmortyapi.com/api/character/avatar/34.jpeg",
                    onError = {
                        Log.d("TEST WORKMATE1", "imageUrl ${it.result.throwable.message}")
                    },
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth
                )
                Row(
                    modifier = modifier
                        .align(alignment = Alignment.BottomEnd)
                        .clip(shape = RoundedCornerShape(topStart = 50.dp))
                        .background(Color.Black)
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(pointColor)
                    )
                    Spacer(modifier = modifier.width(5.dp))
                    Text(
                        text = stringResource(characterDetails.status.resId),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
            Spacer(modifier = modifier.height(15.dp))
            Text(
                text = characterDetails.name,
                color = Color.White,
                fontSize = 20.sp
            )
            Spacer(modifier = modifier.height(15.dp))
            Text(
                text = "${stringResource(characterDetails.gender.resId)} | ${characterDetails.species}",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 18.sp
            )
        }
    }
}

@Preview
@Composable
private fun ItemCharacterView() {
    ItemCharacter(
        characterDetails = CharacterDetails(
            id = 1,
            name = "Summer Smith",
            species = "Human",
            status = StatusCharacter.ALIVE,
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
            gender = Gender.MALE,
            location = "Earth (Replacement Dimension)",
            origin = "Earth (Replacement Dimension)",
            episode = listOf("1", "2", "3", "4", "5")
        ),
        onClick = {}
    )
}