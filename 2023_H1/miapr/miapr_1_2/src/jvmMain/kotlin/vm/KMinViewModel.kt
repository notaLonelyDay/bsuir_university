package vm

import entity.Clazz
import entity.Point
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.random.nextInt

class KMinViewModel : ClusterViewModelProtocol() {


    override fun generateInput(pointCount: Int, classCount: Int, xRange: IntRange, yRange: IntRange) {
        generatePoints(pointCount, xRange, yRange)
        generateClasses(classCount, xRange, yRange)
    }

    private fun generatePoints(count: Int, xRange: IntRange, yRange: IntRange) {
        pointsFlow.value = (0 until count)
            .map {
                Point(
                    Random.nextInt(xRange),
                    Random.nextInt(yRange),
                    BASE_CLASS,
                )
            }
            .toList()
    }

    private fun generateClasses(count: Int, xRange: IntRange, yRange: IntRange) {
        classesFlow.value = (0 until count)
            .mapIndexed { index, it ->
                Clazz(
                    Random.nextInt(xRange),
                    Random.nextInt(yRange),
                    index + BASE_CLASS,
                )
            }
            .toList()
    }

    /**
     * true if have something more to relax
     */
    override fun relax(): Boolean {
        var relaxed = false
        pointsFlow.value = pointsFlow.value.mapNotNull { point ->
            val bestClass = classesFlow.value.minByOrNull { clazz ->
                getSKO(point, clazz)
            }
            bestClass?.let { clazz ->
                if (clazz.clazz != point.clazz)
                    relaxed = true
                point.copy(
                    clazz = clazz.clazz
                )
            } ?: run {
                println("No best class found")
                null
            }
        }

        classesFlow.value = classesFlow.value
            .map { clazz ->
                var xSum = 0
                var xCount = 0
                var ySum = 0
                var yCount = 0

                pointsFlow.value
                    .filter { it.clazz == clazz.clazz }
                    .forEach {
                        xSum += it.x
                        xCount++
                        ySum += it.y
                        yCount++
                    }

                clazz.copy(
                    x = if (xCount != 0) xSum / xCount else clazz.x,
                    y = if (yCount != 0) ySum / yCount else clazz.y,
                )
            }

        return relaxed
    }

    private fun getSKO(point: Point, clazz: Clazz): Double {
        return sqrt(((point.x - clazz.x).toDouble()).pow(2.0) + (point.y - clazz.y).toDouble().pow(2))
    }

    companion object {
        const val BASE_CLASS = 0
    }

}