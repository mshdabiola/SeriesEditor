package com.mshdabiola.data.repository

import com.mshdabiola.seriesmodel.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getUser(id: Long): Flow<User?>

    suspend fun setUser(user: User): Long

    fun getUserByPassword(name: String, password: String): Flow<User?>

    suspend fun deleteUser(id: Long)
}
