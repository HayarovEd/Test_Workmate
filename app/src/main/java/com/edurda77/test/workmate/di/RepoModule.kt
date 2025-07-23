package com.edurda77.test.workmate.di

import com.edurda77.test.workmate.data.repository.RemoteRepositoryImpl
import com.edurda77.test.workmate.data.repository.ServiceRepositoryImpl
import com.edurda77.test.workmate.domain.repository.RemoteRepository
import com.edurda77.test.workmate.domain.repository.ServiceRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repoModule = module {
    singleOf(::RemoteRepositoryImpl) { bind<RemoteRepository>() }
    singleOf(::ServiceRepositoryImpl) { bind<ServiceRepository>() }
  // singleOf(::LocalRepositoryImpl) { bind<LocalRepository>() }

}
