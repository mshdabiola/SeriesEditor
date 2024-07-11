package com.mshdabiola.data.repository

import com.mshdabiola.generalmodel.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getUser(): Flow<User?>

    suspend fun setUser(user: User)

    suspend fun deleteUser(id:Long)


}