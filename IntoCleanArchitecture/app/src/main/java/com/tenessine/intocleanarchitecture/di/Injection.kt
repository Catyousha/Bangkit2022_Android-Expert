package com.tenessine.intocleanarchitecture.di

import com.tenessine.intocleanarchitecture.data.IMessageDataSource
import com.tenessine.intocleanarchitecture.data.MessageDataSource
import com.tenessine.intocleanarchitecture.data.MessageRepository
import com.tenessine.intocleanarchitecture.domain.IMessageRepository
import com.tenessine.intocleanarchitecture.domain.MessageInteractor
import com.tenessine.intocleanarchitecture.domain.MessageUseCase

object Injection {
    fun provideUseCase(): MessageUseCase {
        val messageRepository = provideRepository()
        return MessageInteractor(messageRepository)
    }

    private fun provideRepository(): IMessageRepository {
       val messageDataSource = provideDataSource()
        return MessageRepository(messageDataSource)
    }

    private fun provideDataSource(): IMessageDataSource {
        return MessageDataSource()
    }
}