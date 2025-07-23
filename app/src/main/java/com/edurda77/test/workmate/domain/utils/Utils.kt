package com.edurda77.test.workmate.domain.utils

import com.edurda77.test.workmate.domain.model.Gender
import com.edurda77.test.workmate.domain.model.StatusCharacter

fun Gender.convertToString(): String {
    return when (this) {
        Gender.FEMALE -> FEMALE
        Gender.MALE -> MALE
        Gender.GENDERLESS -> GENDERLESS
        Gender.UNKNOWN -> UNKNOWN
    }
}

fun String.convertToGender(): Gender {
    return when (this) {
        FEMALE -> Gender.FEMALE
        MALE -> Gender.MALE
        GENDERLESS -> Gender.GENDERLESS
        else -> Gender.UNKNOWN
    }
}

fun StatusCharacter.convertToString(): String {
    return when (this) {
        StatusCharacter.ALIVE -> ALIVE
        StatusCharacter.DEAD -> DEAD
        StatusCharacter.UNKNOWN -> UNKNOWN
    }
}

fun String.convertToStatusCharacter(): StatusCharacter {
    return when (this) {
        ALIVE -> StatusCharacter.ALIVE
        DEAD -> StatusCharacter.DEAD
        else -> StatusCharacter.UNKNOWN
    }
}