package com.blogspot.agusticar.miscuentasv2.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.blogspot.agusticar.miscuentasv2.ui.theme.Black
import com.blogspot.agusticar.miscuentasv2.ui.theme.CoralPink
import com.blogspot.agusticar.miscuentasv2.ui.theme.Crimson
import com.blogspot.agusticar.miscuentasv2.ui.theme.DarkBrown
import com.blogspot.agusticar.miscuentasv2.ui.theme.DeepPurple
import com.blogspot.agusticar.miscuentasv2.ui.theme.LightGrey
import com.blogspot.agusticar.miscuentasv2.ui.theme.LightYellow
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
    val secondaryTextColor: Color = Color.Unspecified,
    val secondaryIconColor: Color = Color.Unspecified,
    val textFieldColor:Color=Color.Unspecified,
    val indicatorDefault:Color = Color.Unspecified,
    val indicatorSelected:Color = Color.Unspecified,
    val backgroundImage:Color = Color.Unspecified,

)

val LightCustomColorsPalette = CustomColorsPalette(
    containerColorDefault =  Crimson,
    containerColorPressed =  CoralPink,
    contentColorDefault =  White,
    contentColorPressed =  LightYellow,
    backgroundPrimary =  SoftPeach,
    backgroundSecondary =  LightYellow,
    textColor =  DarkBrown,
    hightTextColor =  Black,
    iconColor = LightYellow,
    secondaryTextColor =  Black,
    secondaryIconColor = Black,
    textFieldColor = LightYellow,
    indicatorDefault =  DeepPurple,
    indicatorSelected =  Crimson,
    backgroundImage = Crimson
)
val DarkCustomColorsPalette = CustomColorsPalette(
    containerColorDefault =  SoftPeach,
    containerColorPressed =  CoralPink,
    contentColorDefault = LightYellow,
    contentColorPressed =  White,
    backgroundPrimary =  DarkBrown,
    backgroundSecondary =  SoftPeach,
    textColor =  White,
    hightTextColor =  LightYellow,
    iconColor = White,
    secondaryTextColor = DarkBrown,
    secondaryIconColor =  Black,
    textFieldColor = LightGrey,
    indicatorDefault =  SoftPeach,
    indicatorSelected =  CoralPink,
    backgroundImage = Black
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }
