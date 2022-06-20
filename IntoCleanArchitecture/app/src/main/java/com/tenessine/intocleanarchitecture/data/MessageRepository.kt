package com.tenessine.intocleanarchitecture.data

import com.tenessine.intocleanarchitecture.domain.IMessageRepository
import com.tenessine.intocleanarchitecture.domain.MessageEntity

class MessageRepository(
    // interaksi ke layer datasource melalui interface
    private val messageDataSource: IMessageDataSource
) : IMessageRepository {
    override fun getWelcomeMessage(name: String): MessageEntity {
        return messageDataSource.getMessageFromSource(name)
    }
}