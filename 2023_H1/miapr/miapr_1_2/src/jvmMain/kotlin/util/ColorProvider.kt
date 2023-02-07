package util

import androidx.compose.ui.graphics.Color
import java.util.concurrent.ThreadLocalRandom

class ColorProvider {
    private val used: MutableSet<Color> = mutableSetOf()
    private val colorMap: MutableMap<Int, Color> = mutableMapOf()

    fun getClassColor(colorClass: Int): Color {
        return colorMap.getOrPut(colorClass, ::nextColor)
    }

    fun clear(){
        used.clear()
        colorMap.clear()
    }

    private fun nextColor(): Color {
        val nextColor = defaultColors.find { !used.contains(it) } ?: generateColor()
        used.add(nextColor)
        return nextColor
    }

    private fun generateColor(): Color{
        return Color(
            ThreadLocalRandom.current().nextInt(255),
            ThreadLocalRandom.current().nextInt(255),
            ThreadLocalRandom.current().nextInt(255),
            255
        )
    }

    companion object {
        val defaultColors = listOf(
            Color.Black,
            Color.Red,
            Color.Green,
            Color.Blue,
            Color.Yellow,
            Color.Cyan,
            Color.Magenta,
            Color.LightGray,
        )
    }
}