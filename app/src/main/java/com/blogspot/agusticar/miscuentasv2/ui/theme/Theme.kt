package com.blogspot.agusticar.miscuentasv2.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.blogspot.agusticar.miscuentasv2.model.DarkCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.model.LightCustomColorsPalette
import com.blogspot.agusticar.miscuentasv2.model.LocalCustomColorsPalette

// Definición de la paleta de colores para un tema claro
/*val LightColorApp = lightColorScheme(
    primary = CoralPink, // Color primario
    secondary = SoftPeach, // Color secundario
    background = LightYellow, // Color de fondo
    surface = White, // Color de superficie
    error = Red, // Color de error
    onPrimary = Black, // Texto sobre el color primario
    onSecondary = Black, // Texto sobre el color secundario
    onBackground = Black, // Texto sobre el fondo
    onSurface = Black, // Texto sobre la superficie
    onError = White // Texto sobre el color de error
)

// Definición de la paleta de colores para un tema oscuro
val DarkColorApp = darkColorScheme(
    primary = CoralPink, // Color primario
    onPrimary = Black, // Texto negro sobre el color primario
    secondary = SoftPeach, // Color secundario
    onSecondary = Black, // Texto negro sobre el color secundario
    background = DarkBrown, // Color de fondo oscuro
    onBackground = White, // Texto blanco sobre el fondo oscuro
    surface = DeepPurple, // Color de superficie para tarjetas y componentes
    onSurface = White, // Texto blanco sobre la superficie
    error = Red, // Color de error
    onError = White // Texto blanco sobre el color de error
)*/

// Implementación del tema con soporte para tema claro/oscuro y colores dinámicos
@Composable
fun MisCuentasv2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color es disponible en Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    /*val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorApp
        else -> LightColorApp
    }

    // logic for which custom palette to use*/
    val customColorsPalette =
        if (darkTheme) DarkCustomColorsPalette
        else LightCustomColorsPalette

    // here is the important point, where you will expose custom objects
    CompositionLocalProvider(
        LocalCustomColorsPalette provides customColorsPalette // our custom palette
    ) {
        MaterialTheme(
            //colorScheme = colorScheme, // the MaterialTheme still uses the "normal" palette
            content = content
        )
    }

}