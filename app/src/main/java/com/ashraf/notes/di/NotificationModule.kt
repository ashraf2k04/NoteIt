package com.ashraf.notes.di

import android.content.Context
import com.ashraf.notes.notification.ReminderScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideReminderScheduler(
        @ApplicationContext context: Context
    ): ReminderScheduler = ReminderScheduler(context)
}
