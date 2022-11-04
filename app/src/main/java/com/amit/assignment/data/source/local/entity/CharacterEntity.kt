package com.amit.assignment.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Character")
data class CharacterEntity(
    @PrimaryKey
    var id: Int,
    var imageURL: String?,
    var name: String?,
    var description: String?
)
