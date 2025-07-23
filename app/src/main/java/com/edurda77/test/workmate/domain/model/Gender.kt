package com.edurda77.test.workmate.domain.model

import com.edurda77.test.workmate.R

enum class Gender(val resId: Int) {
    FEMALE(R.string.female),
    MALE(R.string.male),
    GENDERLESS(R.string.genderless),
    UNKNOWN(R.string.unknown),
}