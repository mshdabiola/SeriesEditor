package com.mshdabiola.data.repository

import com.mshdabiola.generalmodel.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getUser(id: Long): Flow<User?>

    suspend fun setUser(user: User): Long

    suspend fun deleteUser(id: Long)
}
