/*
 *abiola 2024
 */

package com.mshdabiola.testing.datastore

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.datastore.Store
import com.mshdabiola.datastore.StoreImpl
import com.mshdabiola.datastore.model.CurrentExamJsonSerializer
import com.mshdabiola.datastore.model.InstructionJsonSerializer
import com.mshdabiola.datastore.model.QuestionJsonSerializer
import com.mshdabiola.datastore.model.UserDataJsonSerializer
import kotlinx.coroutines.CoroutineScope
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.junit.rules.TemporaryFolder
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

val dataStoreModule = module {
    single {
    }

    single(qualifier = qualifier("userdata")) {
        val tmpFolder: TemporaryFolder = get()
        tmpFolder.testUserPreferencesDataStore(
            coroutineScope = get(),
        )
    }

    single(qualifier = qualifier("question")) {
        val tmpFolder: TemporaryFolder = get()
        tmpFolder.testQuestionPreferencesDataStore(
            coroutineScope = get(),
        )
    }

    single(qualifier = qualifier("instruction")) {
        val tmpFolder: TemporaryFolder = get()
        tmpFolder.testInstructionPreferencesDataStore(
            coroutineScope = get(),
        )
    }

    single(qualifier = qualifier("current")) {
        val tmpFolder: TemporaryFolder = get()
        tmpFolder.testCurrentPreferencesDataStore(
            coroutineScope = get(),
        )
    }

    single {
        StoreImpl(
            userdata = get(qualifier = qualifier("userdata")),
            question = get(qualifier = qualifier("question")),
            instruction = get(qualifier = qualifier("instruction")),
            current = get(qualifier = qualifier("current")),
            coroutineDispatcher = get(),
        )
    } bind Store::class
}

fun TemporaryFolder.testUserPreferencesDataStore(
    coroutineScope: CoroutineScope,
) = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = UserDataJsonSerializer,
        producePath = {
            val path = File(newFolder(), "data")
            if (!path.parentFile.exists()) {
                path.mkdirs()
            }
            path.toOkioPath()
        },
    ),
)

fun TemporaryFolder.testCurrentPreferencesDataStore(
    coroutineScope: CoroutineScope,
) = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = CurrentExamJsonSerializer,
        producePath = {
            val path = File(newFolder(), "current")
            if (!path.parentFile.exists()) {
                path.mkdirs()
            }
            path.toOkioPath()
        },
    ),
)

fun TemporaryFolder.testQuestionPreferencesDataStore(
    coroutineScope: CoroutineScope,
) = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = QuestionJsonSerializer,
        producePath = {
            val path = File(newFolder(), "question")
            if (!path.parentFile.exists()) {
                path.mkdirs()
            }
            path.toOkioPath()
        },
    ),
)

fun TemporaryFolder.testInstructionPreferencesDataStore(
    coroutineScope: CoroutineScope,
) = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = InstructionJsonSerializer,
        producePath = {
            val path = File(newFolder(), "instruction")
            if (!path.parentFile.exists()) {
                path.mkdirs()
            }
            path.toOkioPath()
        },
    ),
)

//
//    DataStoreFactory.create(
//    storage = OkioStorage(
//        fileSystem = FileSystem.SYSTEM,
//        serializer = UserDataJsonSerializer,
//        producePath = {
//            val path = newFile("user_preferences_test.pb")
//            path.toOkioPath()
//        },
//
//    ),
//    scope = coroutineScope,
// )
