package com.amit.assignment.data.source.local.dao

import androidx.room.* // ktlint-disable no-wildcard-imports
import com.amit.assignment.data.source.local.entity.CharacterEntity

/**
 * it provides access to [CharacterEntity] underlying database
 * */
@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: CharacterEntity): Long

    @Query("SELECT * FROM Character")
    fun loadAll(): MutableList<CharacterEntity>

    @Delete
    fun delete(character: CharacterEntity)

    @Query("DELETE FROM Character")
    fun deleteAll()

    @Query("SELECT * FROM Character where id = :id")
    fun loadOneById(id: Long): CharacterEntity?

    @Update
    fun update(photo: CharacterEntity)
}
