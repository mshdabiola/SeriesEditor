package com.mshdabiola.detail

import com.mshdabiola.detail.instruction.InstructionViewModel
import com.mshdabiola.detail.question.QuestionViewModel
import com.mshdabiola.detail.topic.TopicViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { param ->
        QuestionViewModel(param[0], param[1], get(), get(), get(), get(), get())
    }
    viewModel { param ->
        TopicViewModel(param[0], get())
    }
    viewModel { param ->
        InstructionViewModel(param[0], get(), get())
    }
}
