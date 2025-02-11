package com.reza.common.util.navigation

const val CONTINENT_CODE = "continent_code"

enum class NavigationRoute(val route: String) {
    HOME("home"),
    DETAIL("detail/{$CONTINENT_CODE}");
}