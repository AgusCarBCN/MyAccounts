package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import kotlinx.coroutines.launch


@Composable
fun IconComponent(isPressed: Boolean, iconResource: Int, iconSize: Int) {
    // Animación de escala para el punto seleccionado
    val scale = animateFloatAsState(
        targetValue = if (isPressed) 1.5f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "icon"
    )
    val indicatorColor by animateColorAsState(
        targetValue = if (isPressed) {
            LocalCustomColorsPalette.current.iconPressed
        } else {
            LocalCustomColorsPalette.current.iconColor
        },
        label = "indicator color",
        animationSpec = tween(
            durationMillis = 2000, // Duración de la animación
            easing = LinearOutSlowInEasing // Controla la velocidad de la transición
        )
    )

    Icon(
        painter = painterResource(id=iconResource),
        contentDescription = "indicator",
        tint = indicatorColor,
        modifier = Modifier
            .scale(scale.value)
            .size(iconSize.dp)


    )

}

@Composable
fun AnimatedIcon(iconResource: Int, modifier: Modifier) {
    // Creamos un animatable para manejar el color del ícono
    val initColor = LocalCustomColorsPalette.current.iconPressed
    val targetColor = LocalCustomColorsPalette.current.iconColor
    val color = remember { Animatable(initColor) }
    val coroutineScope = rememberCoroutineScope()

    // Iniciamos una corrutina para animar el color de manera infinita
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            // Animación infinita que alterna entre dos colores
            color.animateTo(
                targetValue = targetColor,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000), // Duración de la transición (1 segundo)
                    repeatMode = RepeatMode.Reverse // Alterna entre los dos colores
                )
            )
        }
    }
    Image(
        painter = painterResource (id = iconResource), // Reemplaza con tu ícono
        contentDescription = "Ícono de configuración",
        // Aplica el color animado al ícono
        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(color.value)
    )
}
