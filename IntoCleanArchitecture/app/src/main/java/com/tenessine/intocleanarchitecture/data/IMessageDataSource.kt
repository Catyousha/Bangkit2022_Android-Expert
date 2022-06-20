package com.tenessine.intocleanarchitecture.data

import com.tenessine.intocleanarchitecture.domain.MessageEntity

interface IMessageDataSource {
    fun getMessageFromSource(name: String): MessageEntity
}