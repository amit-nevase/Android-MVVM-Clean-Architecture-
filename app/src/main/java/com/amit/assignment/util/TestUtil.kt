package com.amit.assignment.util

import com.amit.assignment.data.source.local.entity.CharacterEntity

object TestUtil {

    fun createCharacter(id: Int) = CharacterEntity(
        id = id,
        imageURL = "",
        name = "",
        description = ""
    )

    fun makeCharacterList(size: Int): MutableList<CharacterEntity> {
        val list = ArrayList<CharacterEntity>(size)
        list.forEach {
            it.description = "Character ${list.indexOf(it)}"
            it.id = (list.indexOf(it) + 1)
            list.add(it)
        }
        return list
    }
}
