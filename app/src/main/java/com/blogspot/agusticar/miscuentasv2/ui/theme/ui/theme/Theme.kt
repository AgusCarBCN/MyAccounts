package com.blogspot.agusticar.miscuentasv2.ui.theme.ui.theme


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

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40


)

@Composable
fun MisCuentasv2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
// logic for which custom palette to use
    val customColorsPalette =
        if (darkTheme) DarkCustomColorsPalette
        else LightCustomColorsPalette

    // here is the important point, where you will expose custom objects
    CompositionLocalProvider(
        LocalCustomColorsPalette provides customColorsPalette // our custom palette
    ) {
        MaterialTheme(
            colorScheme = colorScheme, // the MaterialTheme still uses the "normal" palette
            typography = Typography,
            content = content
        )
    }

}