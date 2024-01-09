package com.example.englishwritinginviews.data.db

import android.util.Log
import com.example.englishwritinginviews.data.db.entities.QuestionDbModel
import com.example.englishwritinginviews.data.db.entities.toDomainModel
import com.example.englishwritinginviews.domain.QuestionDomain
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class BaseFlowResponse {
    inline fun safeFlowCall(crossinline flowFunction: () -> Flow<List<QuestionDbModel>>): Flow<List<QuestionDomain>> =
        flow {
            emitAll(flowFunction().map { flow -> flow.map { model -> model.toDomainModel() } })
        }
}
