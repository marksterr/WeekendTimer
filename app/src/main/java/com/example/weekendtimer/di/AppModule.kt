package com.example.weekendtimer.di

import com.example.weekendtimer.data.MealRepo
import com.example.weekendtimer.data.MealRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun provideMealRepo(impl: MealRepoImpl): MealRepo
}