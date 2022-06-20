package com.tenessine.intocleanarchitecture.data

import com.tenessine.intocleanarchitecture.domain.MessageEntity

class MessageDataSource : IMessageDataSource {
    override fun getMessageFromSource(name: String): MessageEntity {
        return MessageEntity("Hello $name")
    }

}