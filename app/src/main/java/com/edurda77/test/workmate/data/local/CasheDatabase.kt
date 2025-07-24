package com.edurda77.test.workmate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CharacterEntity::class],
    version = 1
)

abstract class CasheDatabase : RoomDatabase() {
    abstract val casheDao: CasheDao
}