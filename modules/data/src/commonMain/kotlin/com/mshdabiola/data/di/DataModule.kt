package com.mshdabiola.data.di

import co.touchlab.kermit.Logger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.analytics.di.analyticsModule
import com.mshdabiola.data.repository.ExaminationRepository
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.data.repository.INetworkRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISeriesRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.ITopicCategory
import com.mshdabiola.data.repository.ITopicRepository
import com.mshdabiola.data.repository.IUserRepository
import com.mshdabiola.data.repository.InstructionRepository
import com.mshdabiola.data.repository.OfflineFirstUserDataRepository
import com.mshdabiola.data.repository.QuestionRepository
import com.mshdabiola.data.repository.RealINetworkRepository
import com.mshdabiola.data.repository.SeriesRepository
import com.mshdabiola.data.repository.SettingRepository
import com.mshdabiola.data.repository.SubjectRepository
import com.mshdabiola.data.repository.TopicCategoryRepository
import com.mshdabiola.data.repository.TopicRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.data.repository.UserRepository
import com.mshdabiola.datastore.di.datastoreModule
import com.mshdabiola.network.di.networkModule
import com.mshdabiola.seriesdatabase.di.databaseModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {

    includes(datastoreModule, databaseModule, networkModule, analyticsModule)
    single {
        // sync path together
        Dispatchers.IO
    } bind CoroutineDispatcher::class
    dataModule()
}

fun Module.dataModule() {
//    logger(
//        KermitKoinLogger(Logger.withTag("koin")),
//    )
    single {
        Logger(
            loggerConfigInit(platformLogWriter()),
            "SeriesEditorLogger,",
        )
    }
    singleOf(::RealINetworkRepository) bind INetworkRepository::class
    singleOf(::OfflineFirstUserDataRepository) bind UserDataRepository::class

    singleOf(::SettingRepository) bind ISettingRepository::class

    singleOf(::SubjectRepository) bind ISubjectRepository::class
    singleOf(::ExaminationRepository) bind IExaminationRepository::class
    singleOf(::TopicRepository) bind ITopicRepository::class
    singleOf(::InstructionRepository) bind IInstructionRepository::class
    singleOf(::QuestionRepository) bind IQuestionRepository::class
    singleOf(::SeriesRepository) bind ISeriesRepository::class
    singleOf(::UserRepository) bind IUserRepository::class
    singleOf(::TopicCategoryRepository) bind ITopicCategory::class
}
