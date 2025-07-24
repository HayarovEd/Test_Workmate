package com.edurda77.test.workmate.ui.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.edurda77.test.workmate.domain.model.CharacterDetails
import com.edurda77.test.workmate.domain.model.Gender
import com.edurda77.test.workmate.domain.model.StatusCharacter
import com.edurda77.test.workmate.ui.theme.background

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
                AsyncImage(
                    modifier = modifier.fillMaxWidth(),
                    model = characterDetails.imageUrl,
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
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 10.sp
                    )
                }
            }
            Spacer(modifier = modifier.height(15.dp))
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                text = characterDetails.name,
                color = Color.White,
                maxLines = 1,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(15.dp))
            Text(
                text = "${stringResource(characterDetails.gender.resId)} | ${characterDetails.species}",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 12.sp
            )
            Spacer(modifier = modifier.height(15.dp))
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
            type = "",
            episode = listOf("1", "2", "3", "4", "5")
        ),
        onClick = {}
    )
}