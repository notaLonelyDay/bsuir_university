package vm

import entity.Clazz
import entity.Point
import kotlinx.coroutines.flow.MutableStateFlow

abstract class ClusterViewModelProtocol {
    var classesFlow: MutableStateFlow<List<Clazz>> = MutableStateFlow(emptyList())
    var pointsFlow: MutableStateFlow<List<Point>> = MutableStateFlow(emptyList())

    abstract fun generateInput(pointCount: Int, classCount: Int, xRange: IntRange, yRange: IntRange)

    /**
     * true if have something more to relax
     */
    abstract fun relax(): Boolean

}