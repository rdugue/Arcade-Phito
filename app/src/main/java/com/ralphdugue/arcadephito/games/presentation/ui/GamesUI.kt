package com.ralphdugue.arcadephito.games.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ralphdugue.arcadephito.games.data.GAMES_LIST
import com.ralphdugue.arcadephito.games.data.toGame
import com.ralphdugue.arcadephito.games.domain.Game
import com.ralphdugue.arcadephito.games.domain.GameType
import com.ralphdugue.arcadephito.theme.ArcadePhitoTheme

@Preview(showBackground = true)
@Composable
fun GamesListPreview() {
    ArcadePhitoTheme {
        GamesList(GAMES_LIST.map { it.toGame() }) {}
    }
}

@Composable
fun GamesList(list: List<Game>, onClick: (id: GameType) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(list) { game ->
            GamesListItem(game = game) { onClick(it) }
        }
    }
}

@Composable
fun GamesListItem(game: Game, onClick: (id: GameType) -> Unit) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .clickable { onClick(game.id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Image(
                modifier = Modifier.padding(5.dp),
                painter = painterResource(id = game.icon),
                contentDescription = null
            )
        }
        Text(text = game.name, style = MaterialTheme.typography.labelSmall)
    }
}