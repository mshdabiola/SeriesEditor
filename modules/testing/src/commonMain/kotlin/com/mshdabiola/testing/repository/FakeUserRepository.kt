package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.IUserRepository
import com.mshdabiola.generalmodel.User
import com.mshdabiola.testing.defaultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeUserRepository : IUserRepository {
    private val _user = MutableStateFlow<List<User>>(defaultData.users)

    override fun getUser(id: Long): Flow<User?> {
        return _user
            .map { it.firstOrNull { it.id == id } }
    }

    override suspend fun setUser(user: User): Long {
        _user.value = _user.value.toMutableList().apply {
            if (user.id == -1L) {
                add(user)
            }else{
                val index = this.indexOfFirst { it.id == user.id }
                add(index, user)
            }
        }
        return 1
    }

    override suspend fun deleteUser(id: Long) {
        _user.value = _user.value.toMutableList().apply {
            removeIf { it.id == id }
        }
    }

}
