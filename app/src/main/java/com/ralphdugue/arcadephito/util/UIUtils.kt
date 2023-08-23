package com.ralphdugue.arcadephito.util

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

fun WindowSizeClass.isPorttaitPhone(): Boolean = with(this) {
    heightSizeClass == WindowHeightSizeClass.Medium && widthSizeClass == WindowWidthSizeClass.Compact
}

fun WindowSizeClass.isLandscapePhone(): Boolean = with(this) {
    heightSizeClass == WindowHeightSizeClass.Compact
}

fun WindowSizeClass.isPorttaitTablet(): Boolean = with(this) {
    heightSizeClass == WindowHeightSizeClass.Expanded
}

fun WindowSizeClass.isLandscapeTablet(): Boolean = with(this) {
    widthSizeClass == WindowWidthSizeClass.Expanded
}