/*
 *abiola 2024
 */

package com.mshdabiola.testing.repository

import com.mshdabiola.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestNoteRepository {

    private val data = MutableList(4) { index ->
        Note(index.toLong(), "title", "Content")
    }
    suspend fun upsert(note: Note): Long {
        val id = data.size.toLong()
        data.add(note.copy(id = id))
        return id
    }

    fun getAll(): Flow<List<Note>> {
        return flowOf(data)
    }

    fun getOne(id: Long): Flow<Note?> {
        return flowOf(data.singleOrNull { it.id == id })
    }

    suspend fun delete(id: Long) {
        data.removeIf { it.id == id }
    }
}
