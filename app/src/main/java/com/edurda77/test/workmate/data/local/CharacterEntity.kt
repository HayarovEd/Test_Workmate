package com.edurda77.test.workmate.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.edurda77.test.workmate.domain.model.Gender
import com.edurda77.test.workmate.domain.model.StatusCharacter

@Entity(tableName = "characters")
@TypeConverters(StatusConverter::class, GenderConverter::class, StringListConverter::class)
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val species: String,
    val status: StatusCharacter,
    val imageUrl: String,
    val gender: Gender,
    val location: String,
    val origin: String,
    val episode: List<String>
)


class StatusConverter {
    @TypeConverter
    fun toStatus(value: String): StatusCharacter = enumValueOf(value)

    @TypeConverter
    fun fromStatus(status: StatusCharacter): String = status.name
}

class GenderConverter {
    @TypeConverter
    fun toGender(value: String): Gender = enumValueOf(value)

    @TypeConverter
    fun fromGender(gender: Gender): String = gender.name
}

class StringListConverter {
    @TypeConverter
    fun toStringList(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(",")
    }
}
