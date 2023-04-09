package com.shevy.todol.di

import com.shevy.todol.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taskModule = module {
    viewModel { TaskViewModel(application = get()) }
}