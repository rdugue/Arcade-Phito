package com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ralphdugue.arcadephito.R
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeMark
import com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.TicTacToeViewModel
import com.ralphdugue.arcadephito.theme.ArcadePhitoTheme
import com.ralphdugue.arcadephito.util.isLandscapePhone
import com.ralphdugue.arcadephito.util.isLandscapeTablet
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    showBackground = true,
    heightDp = 420,
    widthDp = 933
)
@Preview(
    showBackground = true,
    heightDp = 933,
    widthDp = 420
)
@Preview(
    showBackground = true,
    heightDp = 800,
    widthDp = 1280
)
@Preview(
    showBackground = true,
    heightDp = 1280,
    widthDp = 800
)
@Composable
fun TicTacToePreview() {
    val cf = LocalConfiguration.current
    val screenHeight  = cf.screenHeightDp.dp
    val screenWidth = cf.screenWidthDp.dp
    ArcadePhitoTheme {
        TicTacToeGame(WindowSizeClass.calculateFromSize(DpSize(screenWidth, screenHeight))) {}
    }
}

@Composable
fun TicTacToeGame(
    windowSizeClass: WindowSizeClass,
    squares: List<Array<MutableStateFlow<TicTacToeMark>>> = TicTacToeViewModel.GameState().squares,
    isGameOver: Boolean = false,
    player: TicTacToeViewModel.Player = TicTacToeViewModel.Player(),
    opponent: TicTacToeViewModel.Player = TicTacToeViewModel.Player(isAI = true, mark = TicTacToeMark.O),
    currentTurn: TicTacToeMark = TicTacToeMark.X,
    winner: TicTacToeMark = TicTacToeMark.BLANK,
    onClickSquare: (square: Pair<Int, Int>) -> Unit = {},
    onAITurn: () -> Unit = {},
    onDismiss: (playAgain: Boolean) -> Unit = {}
) {
    when {
        windowSizeClass.isLandscapePhone() -> {
            TicTacToeBoardLandscape(
                squares = squares,
                isGameOver = isGameOver,
                player = player,
                opponent = opponent,
                currentTurn = currentTurn,
                winner = winner,
                onClickSquare = onClickSquare,
                onAITurn = onAITurn,
                onDismiss = onDismiss
            )
        }
        else -> {
            TicTacToeBoard(
                squares = squares,
                isGameOver = isGameOver,
                player = player,
                opponent = opponent,
                currentTurn = currentTurn,
                winner = winner,
                onClickSquare = onClickSquare,
                onAITurn = onAITurn,
                onDismiss = onDismiss
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeBoard(
    squares: List<Array<MutableStateFlow<TicTacToeMark>>> = TicTacToeViewModel.GameState().squares,
    isGameOver: Boolean = false,
    player: TicTacToeViewModel.Player = TicTacToeViewModel.Player(),
    opponent: TicTacToeViewModel.Player = TicTacToeViewModel.Player(isAI = true, mark = TicTacToeMark.O),
    currentTurn: TicTacToeMark = TicTacToeMark.X,
    winner: TicTacToeMark = TicTacToeMark.BLANK,
    onClickSquare: (square: Pair<Int, Int>) -> Unit = {},
    onAITurn: () -> Unit = {},
    onDismiss: (playAgain: Boolean) -> Unit = {}
) {
    when {
        isGameOver -> {
            WinnerDialog(
                modifier = Modifier.fillMaxSize(),
                winner = winner
            ) { onDismiss(it) }
        }
        else -> {
            if (opponent.isAI && currentTurn == opponent.mark) onAITurn()
            Column {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.tictactoe)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { onDismiss(false) }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.close),
                                contentDescription = stringResource(id = R.string.game_exit),
                            )
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    PlayerRow(
                        modifier = Modifier.align(Alignment.Start),
                        username = opponent.userProfile?.username ?: "AI",
                        imageUrl = opponent.userProfile?.imageUrl,
                        mark = opponent.mark ?: TicTacToeMark.O
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(10.dp),
                        modifier = Modifier
                            .width(500.dp)
                            .height(500.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        squares.forEachIndexed { x, row ->
                            itemsIndexed(row) { y, square ->
                                val squareState by square.collectAsStateWithLifecycle()
                                Card(
                                    modifier = Modifier.padding(4.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                    shape = RoundedCornerShape(5.dp)
                                ) {
                                    TicTacToeSquare(
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        mark = squareState
                                    ) { onClickSquare(Pair(x, y)) }
                                }
                            }
                        }
                    }
                    PlayerRow(
                        modifier = Modifier.align(Alignment.End),
                        username = player.userProfile?.username,
                        imageUrl = player.userProfile?.imageUrl,
                        mark = player.mark ?: TicTacToeMark.X
                    )
                }
            }
        }
    }
}

@Composable
fun TicTacToeSquare(
    modifier: Modifier,
    mark: TicTacToeMark = TicTacToeMark.BLANK,
    onClick: () -> Unit
) {
    val imageId = when (mark) {
        TicTacToeMark.BLANK -> R.drawable.tic_tac_toe_blank
        TicTacToeMark.X -> R.drawable.x
        TicTacToeMark.O -> R.drawable.o
    }
    Box(
        modifier = modifier
            .height(100.dp)
            .width(100.dp)
            .clickable { if (mark == TicTacToeMark.BLANK) onClick() }
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .align(Alignment.Center),
            painter = painterResource(id = imageId),
            contentDescription = null
        )
    }
}

@Composable
fun PlayerRow(
    modifier: Modifier,
    username: String?,
    imageUrl: String?,
    mark: TicTacToeMark
) {
    Card(
        modifier = modifier
            .padding(15.dp)
            .wrapContentWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val name = username ?: "JohnDoe"
            AsyncImage(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clip(CircleShape),
                model = imageUrl,
                placeholder = painterResource(id = R.drawable.profile),
                fallback = painterResource(id = R.drawable.profile),
                error = painterResource(id = R.drawable.profile),
                contentDescription = name
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp),
                painter = painterResource(id = when (mark) {
                    TicTacToeMark.X -> R.drawable.x
                    TicTacToeMark.O -> R.drawable.o
                    else -> R.drawable.tic_tac_toe_blank
                }),
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WinnerDialog(
    modifier: Modifier,
    winner: TicTacToeMark,
    onDismiss: (playAgain: Boolean) -> Unit
) {
    AlertDialog(onDismissRequest = { onDismiss(false) }) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = when (winner) {
                        TicTacToeMark.X -> stringResource(id = R.string.tictactoe_x_wins)
                        TicTacToeMark.O -> stringResource(id = R.string.tictactoe_o_wins)
                        else -> stringResource(id = R.string.tictactoe_draw)
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.align(Alignment.End)) {
                    TextButton(
                        onClick = {
                            onDismiss(true)
                        },
                    ) {
                        Text(stringResource(id =R.string.tictactoe_reset))
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    TextButton(
                        onClick = {
                            onDismiss(false)
                        },
                    ) {
                        Text(stringResource(id = R.string.tictactoe_quit))
                    }
                }
            }
        }
    }
}