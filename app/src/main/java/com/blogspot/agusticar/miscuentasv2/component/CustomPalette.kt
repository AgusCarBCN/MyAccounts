package com.blogspot.agusticar.miscuentasv2.component

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.blogspot.agusticar.miscuentasv2.ui.theme.Black
import com.blogspot.agusticar.miscuentasv2.ui.theme.CoralPink
import com.blogspot.agusticar.miscuentasv2.ui.theme.DarkBrown
import com.blogspot.agusticar.miscuentasv2.ui.theme.DeepPurple
import com.blogspot.agusticar.miscuentasv2.ui.theme.LightYellow
import com.blogspot.agusticar.miscuentasv2.ui.theme.Red
import com.blogspot.agusticar.miscuentasv2.ui.theme.SoftPeach
import com.blogspot.agusticar.miscuentasv2.ui.theme.White

@Immutable
data class CustomColorsPalette(
    val containerColorDefault: Color = Color.Unspecified,
    val containerColorPressed: Color = Color.Unspecified,
    val contentColorDefault: Color = Color.Unspecified,
    val contentColorPressed: Color = Color.Unspecified,
    val backgroundPrimary: Color = Color.Unspecified,
    val backgroundSecondary: Color = Color.Unspecified,
    val textColor: Color = Color.Unspecified,
    val hightTextColor: Color = Color.Unspecified,
    val barColor: Color = Color.Unspecified,
    val iconColor: Color = Color.Unspecified,
    val secondaryIconColor: Color = Color.Unspecified

)
val DeepPurple = Color(0xFF331436) // Color 1: Un púrpura profundo
val Crimson = Color(0xFF7A1745) // Color 2: Un carmesí oscuro
val CoralPink = Color(0xFFCB4757) // Color 3: Un rosa coral
val SoftPeach = Color(0xFFEB9961) // Color 4: Un melocotón suave
val LightYellow = Color(0xFFFCF4B6) // Color 5: Un amarillo claro
val Black = Color(0xFF000000)
val DarkBrown = Color(0xFF1A1B17)
val Red = Color(0xFFFF0000) // Color Rojo
val White = Color(0xFFFFFFFF) // Color Blanco

val LightCustomColorsPalette = CustomColorsPalette(
    containerColorDefault =  CoralPink,
    containerColorPressed =  Crimson,
    contentColorDefault =  LightYellow,
    contentColorPressed =  White,
    backgroundPrimary =  LightYellow,
    backgroundSecondary =  SoftPeach,
    textColor =  DarkBrown,
    hightTextColor =  Black,
    iconColor = LightYellow,
    secondaryIconColor = Black

)
val DarkCustomColorsPalette = CustomColorsPalette(
    containerColorDefault =  SoftPeach,
    containerColorPressed =  LightYellow,
    contentColorDefault =  Crimson,
    contentColorPressed =  DeepPurple,
    backgroundPrimary =  Black,
    backgroundSecondary =  DarkBrown,
    textColor =  LightYellow,
    hightTextColor =  White,
    iconColor = LightYellow,
    secondaryIconColor =  Black

)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }
