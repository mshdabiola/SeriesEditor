package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.IUserRepository
import com.mshdabiola.seriesmodel.User
import com.mshdabiola.testing.exportableData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeUserRepository : IUserRepository {
    private val _user = MutableStateFlow(exportableData.users)

    override fun getUser(id: Long): Flow<User?> {
        return _user
            .map { it.firstOrNull { it.id == id } }
    }

    override suspend fun setUser(user: User): Long {
        var id = 0L
        val list = _user.value.toMutableList().apply {
            id = if (user.id == -1L) {
                val id = _user.value.count() + 1L
                add(user.copy(id = id))
                id
            } else {
                val index = this.indexOfFirst { it.id == user.id }
                add(index, user)
                user.id
            }
        }
        _user.update {
            list
        }

        return id
    }

    override fun getUserByPassword(name: String, password: String): Flow<User?> {
        return _user
            .map { users -> users.firstOrNull { it.name == name && it.password == password } }
    }

    override suspend fun deleteUser(id: Long) {
        _user.value = _user.value.toMutableList().apply {
            removeIf { it.id == id }
        }
    }
}
