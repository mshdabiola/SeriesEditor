package com.mshdabiola.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.datastore.model.InstructionJsonSerializer
import com.mshdabiola.datastore.model.QuestionJsonSerializer
import com.mshdabiola.datastore.model.UserDataJsonSerializer
import com.mshdabiola.datastore.model.UserDataSer
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Question
import okio.FileSystem
import okio.Path.Companion.toPath

fun createDataStoreUserData(
    producePath: () -> String,
): DataStore<UserDataSer> = DataStoreFactory.create(
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
class Migration(val path: String) : DataMigration<UserDataSer> {
    val file=path.toPath()
    override suspend fun cleanUp() {
        println("clean up" )

    }

    override suspend fun shouldMigrate(currentData: UserDataSer): Boolean {
        println("should migrate")
        return !currentData.shouldHideOnboarding
    }

    override suspend fun migrate(currentData: UserDataSer): UserDataSer {
        println("migrate")
      return  currentData.copy(
            userId = 90,
          shouldHideOnboarding = true
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
