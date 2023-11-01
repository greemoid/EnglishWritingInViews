package com.example.englishwritinginviews.data.db

import com.example.englishwritinginviews.data.db.entities.QuestionDbModel
import com.example.englishwritinginviews.data.db.entities.toDomainModel
import com.example.englishwritinginviews.domain.QuestionDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class BaseFlowResponse {
    inline fun safeFlowCall(crossinline flowFunction: () -> Flow<List<QuestionDbModel>>): Flow<List<QuestionDomain>> =
        flow {
            try {
                emitAll(flowFunction().map { flow -> flow.map { model -> model.toDomainModel() } })
            } catch (e: Exception) {
                throw Exception("Error: Cannot make safe call to database / $e")
            }
        }
}
