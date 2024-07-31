package com.mshdabiola.datastore

import androidx.datastore.core.okio.OkioSerializer
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Question
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

val json = Json

object QuestionJsonSerializer : OkioSerializer<Map<Long, Question>> {

    override val defaultValue: Map<Long, Question>
        get() = mapOf()

    override suspend fun readFrom(source: BufferedSource): Map<Long, Question> {
        return json.decodeFromString<Map<Long, Question>>(source.readUtf8())
    }

    override suspend fun writeTo(t: Map<Long, Question>, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(
                json.encodeToString(
                    MapSerializer(
                        Long.serializer(),
                        Question.serializer(),
                    ),
                    t,
                ),
            )
        }
    }
}

object InstructionJsonSerializer : OkioSerializer<Map<Long, Instruction>> {

    override val defaultValue: Map<Long, Instruction>
        get() = mapOf()

    override suspend fun readFrom(source: BufferedSource): Map<Long, Instruction> {
        return json.decodeFromString<Map<Long, Instruction>>(source.readUtf8())
    }

    override suspend fun writeTo(t: Map<Long, Instruction>, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(
                json.encodeToString(
                    MapSerializer(
                        Long.serializer(),
                        Instruction.serializer(),
                    ),
                    t,
                ),
            )
        }
    }
}

object UserDataJsonSerializer : OkioSerializer<UserData> {

    override val defaultValue: UserData
        get() = UserData(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.LIGHT,
            useDynamicColor = false,
            shouldHideOnboarding = false,
            userId = 0,
        )

    override suspend fun readFrom(source: BufferedSource): UserData {
        return try {
            json.decodeFromString<UserData>(source.readUtf8())
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserData, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(json.encodeToString(UserData.serializer(), t))
        }
    }
}
