package com.edurda77.test.workmate.domain.repository

import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    val isConnected: Flow<Boolean>
}