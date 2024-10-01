package com.blogspot.agusticar.miscuentasv2.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette



@Composable
fun IconComponent(isPressed: Boolean, iconResource:Int, iconSize:Int){
    // Animación de escala para el punto seleccionado
    val scale = animateFloatAsState(
        targetValue = if (isPressed) 1.5f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow),
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
        painter = painterResource (iconResource)        ,
        contentDescription = "indicator",
        tint = indicatorColor,
        modifier = Modifier
            .scale(scale.value)
            .size(iconSize.dp)


    )
}
