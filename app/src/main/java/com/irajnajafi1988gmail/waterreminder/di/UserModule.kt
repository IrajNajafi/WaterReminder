package com.irajnajafi1988gmail.waterreminder.di

import com.irajnajafi1988gmail.waterreminder.data.repository.UserRepository
import com.irajnajafi1988gmail.waterreminder.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository
}