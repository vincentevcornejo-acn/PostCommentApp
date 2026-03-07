package com.codingchallenge.postcommentapp.domain.usecases

import com.codingchallenge.postcommentapp.domain.repositories.LoginSessionRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SetLoginStatusUseCaseTest {

    private val repository: LoginSessionRepository = mock()

    private lateinit var setLoginStatusUseCase: SetLoginStatusUseCase

    @Before
    fun setUp() {
        setLoginStatusUseCase = SetLoginStatusUseCase(repository)
    }

    @Test
    fun `should store login status as true when invoked with true`() = runTest {
        val status = true
        setLoginStatusUseCase(status)
        verify(repository).storeLoginStatus(status)
    }

    @Test
    fun `should store login status as false when invoked with false`() = runTest {
        val status = false
        setLoginStatusUseCase(status)
        verify(repository).storeLoginStatus(status)
    }
}