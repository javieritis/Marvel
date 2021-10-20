package com.marvel.characters.library.injector.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

@Qualifier
annotation class MainCoroutineScope

@Qualifier
annotation class IoCoroutineContext

@Qualifier
annotation class MainImmediateCoroutineContext

@Qualifier
annotation class MainCoroutineContext

@Qualifier
annotation class DefaultCoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    @MainCoroutineScope
    fun provideMainCoroutineScope(@MainCoroutineContext mainCoroutineContext: CoroutineContext) =
        CoroutineScope(SupervisorJob() + mainCoroutineContext)

    @Provides
    @IoCoroutineContext
    fun provideIoCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @MainCoroutineContext
    fun provideMainCoroutineContext(): CoroutineContext = Dispatchers.Main

    @Provides
    @MainImmediateCoroutineContext
    fun provideMainImmediateCoroutineContext(): CoroutineContext = Dispatchers.Main.immediate

    @Provides
    @DefaultCoroutineContext
    fun provideDefaultCoroutineContext(): CoroutineContext = Dispatchers.Default
}
