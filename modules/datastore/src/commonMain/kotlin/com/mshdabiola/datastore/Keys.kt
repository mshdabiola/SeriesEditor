package com.mshdabiola.datastore

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
)

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
