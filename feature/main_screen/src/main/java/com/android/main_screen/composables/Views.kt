package com.android.main_screen.composables

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.domain.model.PokemonDomain
import com.android.domain.model.SingleEventDomain
import com.android.domain.model.TrainerDomain
import com.android.reydix.core.resources.R
import com.android.ui.components.NetworkImage

@Composable
fun FeatureEventCard(
    singleEventDomain: SingleEventDomain?,
    onCardClick: (List<TrainerDomain>?) -> Unit,
) {
    val colors = MaterialTheme.colorScheme

    Card(
        onClick = { onCardClick(singleEventDomain?.trainers) },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface)
    ) {
        Column(
            modifier = Modifier
        ) {
            NetworkImage(
                url = singleEventDomain?.imgUrl.orEmpty(),
                contentDescription = singleEventDomain?.name.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = singleEventDomain?.name.orEmpty(),
                color = colors.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = singleEventDomain?.date.orEmpty(),
                    color = colors.onSurface.copy(alpha = 0.7f),
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = singleEventDomain?.location.orEmpty(),
                    color = colors.onSurface.copy(alpha = 0.7f),
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun SingleEventItem(
    singleEventDomain: SingleEventDomain?, onCardClick: (List<TrainerDomain>?) -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    Card(
        onClick = { onCardClick(singleEventDomain?.trainers) },
        modifier = Modifier
            .width(180.dp)
            .wrapContentHeight()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        )
    ) {
        Column {
            NetworkImage(
                url = singleEventDomain?.imgUrl.orEmpty(),
                contentDescription = singleEventDomain?.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = singleEventDomain?.name.orEmpty(),
                color = colors.onSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = singleEventDomain?.date.orEmpty(),
                color = colors.onSurface.copy(alpha = 0.7f),
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Text(
                text = singleEventDomain?.location.orEmpty(),
                color = colors.onSurface.copy(alpha = 0.7f),
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}


@Composable
fun SinglePokemonItem(pokemonDomain: PokemonDomain, onConnectClick: (String) -> Unit) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .width(140.dp)
            .wrapContentHeight()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(colors.onSurface.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                NetworkImage(
                    url = pokemonDomain.imageUrl,
                    contentDescription = pokemonDomain.name,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = pokemonDomain.name,
                color = colors.onSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = { onConnectClick(pokemonDomain.name) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary, contentColor = colors.onPrimary
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
            ) {
                Text(
                    text = stringResource(R.string.btn_connect_action), fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeatureEventCardPreview() {
    FeatureEventCard(
        singleEventDomain = SingleEventDomain(
            name = "Sample Event",
            date = "2023-10-01",
            location = "New York",
            imgUrl = "",
            trainers = listOf(),
            isHighlighted = true,
        ), onCardClick = {})
}


@Preview(showBackground = true)
@Composable
fun SingleEventItemPreview() {
    SingleEventItem(
        singleEventDomain = SingleEventDomain(
            name = "Sample EventSample EventSample EventSample EventSample EventSample EventSample EventSample EventSample EventSample EventSample EventSample EventSample Event",
            date = "2023-10-01",
            location = "New York",
            imgUrl = "",
            trainers = listOf(),
            isHighlighted = true,
        ), onCardClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SinglePokemonItemPreview() {
    SinglePokemonItem(
        pokemonDomain = PokemonDomain(
            name = "Pikachu",
            imageUrl = "",
        ), onConnectClick = {})
}