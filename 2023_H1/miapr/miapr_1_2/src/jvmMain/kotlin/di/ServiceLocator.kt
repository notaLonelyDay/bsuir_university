package di

import util.ColorProvider
import vm.KMinViewModel
import vm.MaxMinViewModel

class ServiceLocator {
    companion object{
        val kMinViewModel = KMinViewModel()
        val maxMinViewModel = MaxMinViewModel()
        val colorProvider = ColorProvider()
    }
}