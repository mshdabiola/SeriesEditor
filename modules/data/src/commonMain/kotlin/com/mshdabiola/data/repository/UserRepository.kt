package com.mshdabiola.data.repository

import com.mshdabiola.database.dao.UserDao
import com.mshdabiola.database.model.asEntity
import com.mshdabiola.database.model.asModel
import com.mshdabiola.generalmodel.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher,
) : IUserRepository {
    override fun getUser(): Flow<User?> {
        return userDao.getUserById(1)
            .map {
                it?.asModel()
            }
    }

    override suspend fun setUser(user: User) {
        withContext(ioDispatcher) {
            userDao.insertUser(user.asEntity())
        }
    }

    override suspend fun deleteUser(id: Long) {
        withContext(ioDispatcher) {
            userDao.deleteUser(id)
        }
    }
}
