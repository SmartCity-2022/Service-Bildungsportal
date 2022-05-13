package com.smartcity.education.backend.assigners

interface Assigner<TFrom, TTo> {
    fun assign(from: TFrom, to: TTo)
}