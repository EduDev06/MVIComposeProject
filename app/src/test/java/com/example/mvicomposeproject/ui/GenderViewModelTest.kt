package com.example.mvicomposeproject.ui

import app.cash.turbine.test
import com.example.mvicomposeproject.domain.model.GenderUser
import com.example.mvicomposeproject.domain.use_case.GetGenderUserUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.IllegalStateException


class GenderViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var getGenderUserUseCase: GetGenderUserUseCase

    private lateinit var viewModel: GenderViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = GenderViewModel(
            getGenderUserUseCase = getGenderUserUseCase
        )
    }

    @Test
    fun `should show user state when search name intent is called successfully`() = runTest {
        // Given
        val name = "Fabiana"
        val genderUser = GenderUser(1, name, "Female", 1F)
        val expectedUser = GenderUiState(genderUser)
        every { getGenderUserUseCase(name) } returns flowOf(Result.success(genderUser))

        // When
        viewModel.setIntent(GenderIntent.SearchGenderUser(name))

        // Then
        viewModel.uiState.test {
            val userUiState = awaitItem()
            assertThat(userUiState.isLoading).isFalse()
            assertThat(userUiState).isEqualTo(expectedUser)
        }
    }

    @Test
    fun `should null user gender state when the search intent return emptyFlow`() = runTest {
        // Given
        val name = "Fabiana"
        every { getGenderUserUseCase(name) } returns emptyFlow()

        // When
        viewModel.setIntent(GenderIntent.SearchGenderUser(name))

        // Then
        assertThat(viewModel.uiState.value.genderUser).isNull()
        assertThat(viewModel.uiState.value.isLoading).isTrue()
    }


    @Test
    fun `should show error when search intent returns a failure`() = runTest {
        // Given
        val name = "Fabiana"
        val expectedEffect = GenderEvent.ShowToast("Unexpected result")
        every { getGenderUserUseCase(name) } returns flowOf(
            Result.failure(IllegalStateException("Error"))
        )

        // When
        viewModel.setIntent(GenderIntent.SearchGenderUser(name))

        // Then
        assertThat(viewModel.uiState.value.genderUser).isNull()
        viewModel.effect.test {
            val effectItem = awaitItem()
            assertThat(effectItem).isEqualTo(expectedEffect)
        }
    }
}