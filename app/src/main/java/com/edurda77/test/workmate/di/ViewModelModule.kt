package com.edurda77.test.workmate.di

import com.edurda77.test.workmate.ui.list_characters.CharactersScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
   viewModelOf(::CharactersScreenViewModel)
}
