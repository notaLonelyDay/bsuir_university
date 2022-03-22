package com.notalonelyday.labs.lab3.shape.base

abstract class AbstractFunction<R>: Function<R> {
    abstract fun invoke(x: Double): R
}