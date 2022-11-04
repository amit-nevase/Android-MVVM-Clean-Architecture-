package com.amit.assignment.data.mapper // ktlint-disable filename

import com.amit.assignment.data.source.local.entity.CharacterEntity
import com.amit.assignment.domain.model.Character
// Entity Mapper for db
fun Character.toEntity() = CharacterEntity(
    id = id,
    imageURL = thumbnail.getUrl(),
    name = name,
    description = description
)
