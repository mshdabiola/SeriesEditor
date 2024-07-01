package com.mshdabiola.serieseditor.di

import com.mshdabiola.composesubject.subjectModule
import com.mshdabiola.data.di.dataModule
import com.mshdabiola.detail.detailModule
import com.mshdabiola.main.mainModule
import com.mshdabiola.serieseditor.MainAppViewModel
import com.mshdabiola.setting.settingModule
import com.mshdabiola.template.composeExamModule
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(dataModule, detailModule, mainModule,subjectModule, composeExamModule, settingModule)
    viewModelOf(::MainAppViewModel)
}
