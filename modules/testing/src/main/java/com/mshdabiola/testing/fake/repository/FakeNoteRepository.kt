/*
 *abiola 2024
 */

package com.mshdabiola.testing.fake.repository

import com.mshdabiola.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository constructor() {

    private val data = mutableListOf<Note>()
    suspend fun upsert(note: Note): Long {
        data.add(note)
        val lastIndex = data.lastIndex

        return note.id ?: lastIndex.toLong()
    }

    fun getAll(): Flow<List<Note>> {
        return flow { data }
    }

    fun getOne(id: Long): Flow<Note?> {
        return flow { data.find { it.id == id } }
    }

    suspend fun delete(id: Long) {
        data.removeIf { it.id == id }
    }
}
