package com.tenessine.intocleanarchitecture.domain

class MessageInteractor(
    // interaksi ke layer repository melalui interface
    private val messageRepository: IMessageRepository
) : MessageUseCase {

    override fun getMessage(name: String): MessageEntity {
        return messageRepository.getWelcomeMessage(name)
    }
}