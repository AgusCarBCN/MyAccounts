package com.blogspot.agusticar.miscuentasv2.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.blogspot.agusticar.miscuentasv2.ui.theme.Black
import com.blogspot.agusticar.miscuentasv2.ui.theme.CoralPink
import com.blogspot.agusticar.miscuentasv2.ui.theme.Crimson
import com.blogspot.agusticar.miscuentasv2.ui.theme.DarkBrown
import com.blogspot.agusticar.miscuentasv2.ui.theme.DeepPurple
import com.blogspot.agusticar.miscuentasv2.ui.theme.GreyBlue
import com.blogspot.agusticar.miscuentasv2.ui.theme.LightGrey
import com.blogspot.agusticar.miscuentasv2.ui.theme.LightYellow
import com.blogspot.agusticar.miscuentasv2.ui.theme.PeachyCream
import com.blogspot.agusticar.miscuentasv2.ui.theme.SoftPeach
import com.blogspot.agusticar.miscuentasv2.ui.theme.White

@Immutable
data class CustomColorsPalette(
    val buttonColorDefault: Color = Color.Unspecified,
    val buttonColorPressed: Color = Color.Unspecified,
    val disableButton:Color = Color.Unspecified,
    val textButtonColorDefault: Color = Color.Unspecified,
    val textButtonColorPressed: Color = Color.Unspecified,
    val backgroundPrimary: Color = Color.Unspecified,
    val backgroundSecondary: Color = Color.Unspecified,
    val barBackground: Color = Color.Unspecified,
    val textColor: Color = Color.Unspecified,
    val invertedTextColor: Color = Color.Unspecified,
    val boldTextColor: Color = Color.Unspecified,
    val barColor: Color = Color.Unspecified,
    val iconColor: Color = Color.Unspecified,
    val contentBarColor:Color = Color.Unspecified,
    val indicatorDefault:Color = Color.Unspecified,
    val indicatorSelected:Color = Color.Unspecified,
    val imageBackground:Color = Color.Unspecified,
    val drawerColor:Color = Color.Unspecified,
    val headDrawerColor:Color = Color.Unspecified,
    val contentDrawerColor:Color = Color.Unspecified,
    val focusedContainerTextField:Color=Color.Unspecified,
    val unfocusedContainerTextField:Color=Color.Unspecified,
    val focusedTextFieldColor:Color=Color.Unspecified,
    val unfocusedTextFieldColor:Color=Color.Unspecified
    )

val LightCustomColorsPalette = CustomColorsPalette(
    buttonColorDefault =  SoftPeach,
    buttonColorPressed =  CoralPink,
    disableButton = GreyBlue,
    textButtonColorDefault =  Black,
    textButtonColorPressed =  White,
    backgroundPrimary =  PeachyCream,
    backgroundSecondary =  LightYellow,
    barBackground= CoralPink,
    textColor =  DarkBrown,
    invertedTextColor= LightYellow,
    boldTextColor =  Black,
    iconColor = Black,
    contentBarColor = Black,
    indicatorDefault =  SoftPeach,
    indicatorSelected =  CoralPink,
    imageBackground = CoralPink,
    drawerColor = LightYellow,
    headDrawerColor = SoftPeach,
    contentDrawerColor = Black,
    focusedContainerTextField = LightYellow,
    unfocusedContainerTextField = LightYellow,
    focusedTextFieldColor = DarkBrown,
    unfocusedTextFieldColor = DarkBrown
)
val DarkCustomColorsPalette = CustomColorsPalette(
    buttonColorDefault =  SoftPeach,
    buttonColorPressed =  PeachyCream,
    disableButton = LightYellow,
    textButtonColorDefault =  LightYellow,
    textButtonColorPressed =  DarkBrown,
    backgroundPrimary =  DarkBrown,
    backgroundSecondary =  DarkBrown,
    barBackground= Black,
    textColor =  LightYellow,
    invertedTextColor= DarkBrown,
    boldTextColor =  LightYellow,
    iconColor = LightYellow,
    contentBarColor = LightYellow,
    indicatorDefault =  SoftPeach,
    indicatorSelected =  PeachyCream,
    imageBackground = Black,
    drawerColor = LightGrey,
    headDrawerColor = SoftPeach,
    contentDrawerColor = LightYellow,
    focusedContainerTextField = LightGrey,
    unfocusedContainerTextField = LightGrey,
    focusedTextFieldColor = LightYellow,
    unfocusedTextFieldColor = LightYellow
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }
