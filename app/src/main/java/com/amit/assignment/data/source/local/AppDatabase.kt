package com.amit.assignment.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amit.assignment.data.source.local.dao.CharacterDao
import com.amit.assignment.data.source.local.entity.CharacterEntity

/**
 * To manage data items that can be accessed, updated
 * & maintain relationships between them
 *
 */
@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val characterDao: CharacterDao

    companion object {
        const val DB_NAME = "MarvelCharacterDatabase.db"
    }
}
