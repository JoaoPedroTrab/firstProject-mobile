package com.example.jetpack_firstproject

import android.os.Bundle
import androidx.compose.ui.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack_firstproject.ui.theme.JetpackfirstProjectTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackfirstProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}
fun DrawScope.circle(offset: (Float) -> Offset) {
    val radius = 20f
    drawCircle(
        Color.Black,
        radius = radius,
        center = offset(radius)
    )
}
fun DrawScope.center() {
    circle{
        Offset((size.width / 2f) + (it / 2) ,(size.height / 2f) + (it / 2))
    }
}

fun DrawScope.topRight() {
    circle{
        Offset(size.width - it, it * 2)

    }
}

fun DrawScope.bottomLeft() {
    circle {
        Offset(it * 2, size.height - it)
    }
}

fun DrawScope.topLeft() {
    circle{
        Offset(it * 2, it * 2)
    }
}

fun DrawScope.bottomRight() {
    circle {
        Offset(size.width - it , size.height - it)
    }
}

fun DrawScope.centerRight() {
    circle {
        Offset((size.width - it), (size.height / 2f) + (it / 2))
    }
}

fun DrawScope.centerLeft() {
    circle {
        Offset((it*2), (size.height / 2f) + (it / 2))
    }
}

fun DrawScope.bullet(number: Int) {
    when(number) {
        1 -> {
            center()
        }
        2 -> {
            topRight()
            bottomLeft()
        }
        3 -> {
            topRight()
            center()
            bottomLeft()
        }
        4 -> {
            topRight()
            bottomRight()
            topLeft()
            bottomLeft()
        }
        5 -> {
            topRight()
            bottomRight()
            topLeft()
            bottomLeft()
            center()
        }
        6 -> {
            topRight()
            bottomRight()
            topLeft()
            bottomLeft()
            centerLeft()
            centerRight()
        }
    }

}

@Composable
fun Dice (number: Int, modifier: Modifier) {
    Canvas (
        modifier = modifier.size(96.dp, 96.dp ))
    {
        drawRoundRect(Color.Green,
            cornerRadius = CornerRadius(20f, 20f),
            topLeft = Offset(10f, 10f),
            size = size
        )
        bullet(number = number)
    }
}

@Composable
fun App() {
    var number by remember { mutableStateOf(1) }
    var points by remember { mutableStateOf(0) }
    var modifierP by remember { mutableStateOf(0) }
    var modifierS by remember { mutableStateOf(0) }
    var pointsUpgrade by remember { mutableStateOf(10) }
    var pointsPerSeccond by remember { mutableStateOf(20)}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Dice (number, Modifier.align(Alignment.Center))

        LaunchedEffect(modifierS) {
            while (true) {
                if (modifierS > 0) {
                    number = (1..6).random()
                    points += (number) * modifierS
                }
                delay(10000)
            }
        }

        Button(onClick = {
            number = (1..6).random()
            points += number + modifierP
        },
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (100).dp)
        ) {
            Text ("Jogar")
        }

        Text (
            text = "Pontos: $points \nMelhoria: +$modifierP\nJogadas grátis a cada 10s: $modifierS",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
            color = Color.White
        )

        Button(onClick = {
            if ( points >= (pointsUpgrade * 2)) {
                points -= (pointsUpgrade * 2)
                pointsUpgrade = (pointsUpgrade * 2)
                modifierP++
            }
        },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 100.dp)
                .offset(y = (100).dp)
        ) {
            Text ("Bonus por dado: $${pointsUpgrade * 2}")
        }

        Button(onClick = {
            if (points >= (pointsPerSeccond * 3)) {
                points -= (pointsPerSeccond * 3)
                pointsPerSeccond = (pointsPerSeccond * 3)
                modifierS++
            }
        },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 200.dp)
                .offset(y = (100).dp)
        ) {
            Text ("Nova Jogada grátis: $${pointsPerSeccond * 3}")
        }
    }
}

@Preview
@Composable
fun GreetingPreview() {
    JetpackfirstProjectTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            App()
        }
    }
}
