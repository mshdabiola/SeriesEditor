/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.generalmodel.Subject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
class ComposeSubjectViewModel (
    private val subjectId:Long,
    private val subjectRepository: ISubjectRepository
): ViewModel() {

    @OptIn(ExperimentalFoundationApi::class)
    val state =TextFieldState()

    init {
        viewModelScope.launch {
            println("subject id $subjectId")
            if (subjectId>0){
                val sub=subjectRepository
                    .getOne(subjectId)
                    .first()

                if (sub != null) {

                    state.edit {
                        append(sub.title)
                    }
                }

        }
    }

    }


    fun addSubject(){
        viewModelScope.launch {
            subjectRepository.upsert(
                Subject(
                    id=if (subjectId>0) subjectId else null,
                    title = state.text.toString()
                )
            )
        }
    }


}
