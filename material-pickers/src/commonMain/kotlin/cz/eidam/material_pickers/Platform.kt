package cz.eidam.material_pickers

enum class Platform {
    Android, iOS, Desktop, Web
}

expect fun getPlatform(): Platform