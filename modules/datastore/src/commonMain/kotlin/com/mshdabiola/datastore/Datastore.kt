package com.mshdabiola.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Question
import com.mshdabiola.model.UserData
import okio.FileSystem
import okio.Path.Companion.toPath

fun createDataStoreUserData(
    producePath: () -> String,
): DataStore<UserData> = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = UserDataJsonSerializer,
        producePath = {
            producePath().toPath()
        },
    ),
    migrations = listOf(
//    Migration(path = producePath())
    ),
)
class Migration(val path: String) : DataMigration<UserData> {
    val file = path.toPath()
    override suspend fun cleanUp() {
        println("clean up")
    }

    override suspend fun shouldMigrate(currentData: UserData): Boolean {
        println("should migrate")
        return !currentData.shouldHideOnboarding
    }

    override suspend fun migrate(currentData: UserData): UserData {
        println("migrate")
        return currentData.copy(
            userId = 90,
            shouldHideOnboarding = true,
        )
    }
}

fun createDataStoreInstruction(
    producePath: () -> String,
): DataStore<Map<Long, Instruction>> = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = InstructionJsonSerializer,
        producePath = {
            producePath().toPath()
        },
    ),
)

fun createDataStoreQuestion(
    producePath: () -> String,
): DataStore<Map<Long, Question>> = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = QuestionJsonSerializer,
        producePath = {
            producePath().toPath()
        },
    ),
)
