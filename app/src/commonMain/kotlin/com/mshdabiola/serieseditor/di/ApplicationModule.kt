package com.mshdabiola.serieseditor.di

import com.mshdabiola.composeexam.composeExamModule
import com.mshdabiola.composeinstruction.ciModule
import com.mshdabiola.composequestion.cqModule
import com.mshdabiola.composesubject.subjectModule
import com.mshdabiola.composetopic.ctModule
import com.mshdabiola.data.di.dataModule
import com.mshdabiola.examinations.examModule
import com.mshdabiola.instructions.instructionsModule
import com.mshdabiola.login.loginModule
import com.mshdabiola.questions.questionsModule
import com.mshdabiola.serieseditor.MainAppViewModel
import com.mshdabiola.setting.settingModule
import com.mshdabiola.topics.topicModule
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(
        dataModule,
        examModule,
        com.mshdabiola.subjects.subjectModule,
        com.mshdabiola.main.mainModule,
        subjectModule,
        composeExamModule,
        settingModule,
        cqModule,
        ciModule,
        questionsModule,
        instructionsModule,
        ctModule,
        topicModule,
        loginModule,
    )
    viewModelOf(::MainAppViewModel)
}
