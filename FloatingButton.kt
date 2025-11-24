package com.example.snowtimerapp.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FloatingButton(bottomNavPadding: Dp) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (expanded) 90f else 0f, label = "rotationAnimation")
    val fab1Offset: Dp by animateDpAsState(
        targetValue = if (expanded) 80.dp else 0.dp,
        label = "fab1OffsetAnimation"
    )
    val fab2Offset: Dp by animateDpAsState(
        targetValue = if (expanded) 150.dp else 0.dp,
        label = "fab2OffsetAnimation"
    )
    val density = LocalDensity.current
    val duration = 200

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(animationSpec = tween(durationMillis = duration)),
            exit = fadeOut(animationSpec = tween(durationMillis = duration))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = 0.7f }
                    .background(Color.Black)
                    .clickable { expanded = false }
            )
        }

        Box(
            modifier = Modifier.padding(
                bottom = bottomNavPadding + 20.dp,
                end = 20.dp
            )
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = slideInVertically(
                    animationSpec = tween(
                        durationMillis = duration,
                        delayMillis = 50,
                        easing = LinearOutSlowInEasing
                    ),
                    initialOffsetY = { with(density) { fab2Offset.roundToPx() } }
                ) + fadeIn(animationSpec = tween(durationMillis = duration, delayMillis = 50)),
                exit = slideOutVertically(
                    animationSpec = tween(durationMillis = duration, easing = FastOutSlowInEasing),
                    targetOffsetY = { with(density) { fab2Offset.roundToPx() } }
                ) + fadeOut(animationSpec = tween(durationMillis = duration))
            ) {
                FloatingActionButton(
                    onClick = {},
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .offset(y = -fab2Offset)
                ) {
                    Icon(
                        imageVector = Icons.Default.QuestionAnswer,
                        contentDescription = "floating button2"
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = slideInVertically(
                    animationSpec = tween(
                        durationMillis = duration,
                        easing = LinearOutSlowInEasing
                    ),
                    initialOffsetY = { with(density) { fab1Offset.roundToPx() } }
                ) + fadeIn(animationSpec = tween(durationMillis = duration)),
                exit = slideOutVertically(
                    animationSpec = tween(
                        durationMillis = duration,
                        delayMillis = 50,
                        easing = FastOutSlowInEasing
                    ),
                    targetOffsetY = { with(density) { fab1Offset.roundToPx() } }
                ) + fadeOut(animationSpec = tween(durationMillis = duration, delayMillis = 50))
            ) {
                FloatingActionButton(
                    onClick = {},
                    containerColor = Color.Green,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .offset(y = -fab1Offset)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "floating button1"
                    )
                }
            }

            FloatingActionButton(
                onClick = { expanded = !expanded },
                containerColor = if (expanded) Color.White else Color.Black,
                contentColor = if (expanded) Color.Black else Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .graphicsLayer(rotationZ = rotation)
            ) {
                AnimatedContent(
                    targetState = expanded,
                    transitionSpec = {
                        scaleIn(animationSpec = tween(duration)) togetherWith scaleOut(
                            animationSpec = tween(
                                duration
                            )
                        )
                    },
                    label = "iconAnimation"
                ) { isExpanded ->
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.Close else Icons.Default.Add,
                        contentDescription = "toggle"
                    )
                }
            }
        }
    }
}