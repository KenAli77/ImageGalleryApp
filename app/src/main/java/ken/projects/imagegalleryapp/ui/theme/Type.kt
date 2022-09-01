package ken.projects.imagegalleryapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ken.projects.imagegalleryapp.R

val outfit = FontFamily(
    Font(R.font.outfit_regular),
    Font(R.font.outfit_bold, FontWeight.Bold),
    Font(R.font.outfit_light, FontWeight.Thin)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = outfit,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

)