package com.amit.assignment.data

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.amit.assignment.data.source.local.AppDatabase
import com.amit.assignment.data.source.local.entity.CharacterEntity
import com.amit.assignment.util.TestUtil
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CharacterDaoTest {

    private lateinit var mDatabase: AppDatabase

    @Before
    fun createDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getTargetContext(),
            AppDatabase::class.java
        )
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun isCharacterListEmpty() {
        assertEquals(0, mDatabase.characterDao.loadAll().size)
    }

    @Test
    @Throws(Exception::class)
    fun insertCharacter() {
        val character: CharacterEntity = TestUtil.createCharacter(7)
        val insertedCharacter = mDatabase.characterDao.insert(character)
        assertNotNull(insertedCharacter)
    }

    @Test
    @Throws(Exception::class)
    fun insertCharacterById() {
        val character: CharacterEntity = TestUtil.createCharacter(1).apply {
            description = "Test"
        }
        mDatabase.characterDao.insert(character)
        val characterLoadedByTitle = mDatabase.characterDao.loadOneById(1)
        assertThat(characterLoadedByTitle, equalTo(character))
    }

    @Test
    @Throws(Exception::class)
    fun retrievesCharacters() {
        val characterList = TestUtil.makeCharacterList(5)
        characterList.forEach {
            mDatabase.characterDao.insert(it)
        }

        val loadedCharacter = mDatabase.characterDao.loadAll()
        assertEquals(characterList, loadedCharacter)
    }

    @Test
    @Throws(Exception::class)
    fun deleteCharacter() {
        val character = TestUtil.createCharacter(8)
        mDatabase.characterDao.delete(character)

        val loadOneByCharacterId = mDatabase.characterDao.loadOneById(8)
        assertNull(loadOneByCharacterId)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllCharacter() {
        mDatabase.characterDao.deleteAll()
        val loadedAllCharacter = mDatabase.characterDao.loadAll()
        assert(loadedAllCharacter.isEmpty())
    }
}
