package com.tenessine.intocleanarchitecture.domain

// UseCase sebagai induk dari interactor
// Layer lain harus berinteraksi dengan interface ini ketimbang dengan class anak
interface MessageUseCase {
    fun getMessage(name: String): MessageEntity
}