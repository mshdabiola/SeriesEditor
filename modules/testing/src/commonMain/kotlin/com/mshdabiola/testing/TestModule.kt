/*
 *abiola 2022
 */

package com.mshdabiola.testing

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
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.testing.repository.FakeExaminationRepository
import com.mshdabiola.testing.repository.FakeInstructionRepository
import com.mshdabiola.testing.repository.FakeNetworkRepository
import com.mshdabiola.testing.repository.FakeOfflineFirstUserDataRepository
import com.mshdabiola.testing.repository.FakeQuestionRepository
import com.mshdabiola.testing.repository.FakeSeriesRepository
import com.mshdabiola.testing.repository.FakeSettingRepository
import com.mshdabiola.testing.repository.FakeSubjectRepository
import com.mshdabiola.testing.repository.FakeTopicCategoryRepository
import com.mshdabiola.testing.repository.FakeTopicRepository
import com.mshdabiola.testing.repository.FakeUserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
val testDispatcherModule =
    module {
        single {
            UnconfinedTestDispatcher()
        } bind CoroutineDispatcher::class
    }

val dataTestModule = module {
    includes( testDispatcherModule)
    singleOf(::FakeNetworkRepository) bind INetworkRepository::class
    singleOf(::FakeOfflineFirstUserDataRepository) bind UserDataRepository::class

    singleOf(::FakeSettingRepository) bind ISettingRepository::class

    singleOf(::FakeSubjectRepository) bind ISubjectRepository::class
    singleOf(::FakeExaminationRepository) bind IExaminationRepository::class
    singleOf(::FakeTopicRepository) bind ITopicRepository::class
    singleOf(::FakeInstructionRepository) bind IInstructionRepository::class
    singleOf(::FakeQuestionRepository) bind IQuestionRepository::class
    singleOf(::FakeSeriesRepository) bind ISeriesRepository::class
    singleOf(::FakeUserRepository) bind IUserRepository::class
    singleOf(::FakeTopicCategoryRepository) bind ITopicCategory::class
}
