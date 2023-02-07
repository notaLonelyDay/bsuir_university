package vm

import entity.Clazz
import entity.Point
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.random.nextInt

class MaxMinViewModel : ClusterViewModelProtocol() {

    override fun generateInput(pointCount: Int, classCount: Int, xRange: IntRange, yRange: IntRange) {
        val firstClassInt = getNextClazz()
        pointsFlow.value = (0 until pointCount)
            .map {
                Point(
                    Random.nextInt(xRange),
                    Random.nextInt(yRange),
                    firstClassInt,
                )
            }
            .toList()

        val firstClazz = Clazz(
            pointsFlow.value.first().x,
            pointsFlow.value.first().y,
            firstClassInt,
        )

        val furthestPoint = pointsFlow.value.maxByOrNull {
            getDistance(it, firstClazz)
        } ?: return

        classesFlow.value = listOf(
            firstClazz,
            Clazz(
                furthestPoint.x,
                furthestPoint.y,
                getNextClazz()
            )
        )

        updatePointsClasses()
    }

    override fun relax(): Boolean {
        val ret: Boolean

        var avgSum = 0.0
        for (clazz1 in classesFlow.value) {
            for (clazz2 in classesFlow.value) {
                avgSum += getDistance(clazz1, clazz2)
            }
        }
        val classesSize = classesFlow.value.size
        val avgDist = avgSum / (classesSize * classesSize - 1)

        var bestDist = -1.0
        var bestPoint: Point? = null

        classesFlow.value
            .map { clazz ->
                val pointsCount = pointsFlow.value.count { it.clazz == clazz.clazz }
                if (pointsCount > 1)
                    pointsFlow.value
                        .filter { it.clazz == clazz.clazz }
                        .forEach {
                            val curDistance = getDistance(it, clazz)
                            if (curDistance > bestDist) {
                                bestDist = curDistance
                                bestPoint = it
                            }
                        }
            }

        if (bestDist > (avgDist / 2)) {
            bestPoint?.let { classesFlow.value = classesFlow.value + Clazz(it.x, it.y, getNextClazz()) }
                ?: print("No best point found")
            ret = true
        } else {
            ret = false
        }


        updatePointsClasses()
        return ret
    }

    private fun updatePointsClasses() {
        pointsFlow.value = pointsFlow.value.mapNotNull { point ->
            val bestClass = classesFlow.value.minByOrNull { clazz ->
                getDistance(point, clazz)
            }
            bestClass?.let { clazz ->
                point.copy(
                    clazz = clazz.clazz
                )
            } ?: run {
                println("Invalid state. No best class found")
                null
            }
        }
    }

    private fun getDistance(point: Point, clazz: Clazz): Double {
        return sqrt(((point.x - clazz.x).toDouble()).pow(2.0) + (point.y - clazz.y).toDouble().pow(2))
    }

    private fun getDistance(point: Clazz, clazz: Clazz): Double {
        return sqrt(((point.x - clazz.x).toDouble()).pow(2.0) + (point.y - clazz.y).toDouble().pow(2))
    }

    private var nextClass = BASE_CLASS
    private fun getNextClazz(): Int{
        return  nextClass++
    }

    companion object {
        const val BASE_CLASS = 0
    }
}