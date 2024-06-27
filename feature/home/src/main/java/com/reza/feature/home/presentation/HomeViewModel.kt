//package com.reza.feature.home.presentation
//
//import androidx.lifecycle.ViewModel
//import com.reza.common.domain.model.ResultState
//import com.reza.feature.home.domain.usecase.ContinentImageUseCase
//import com.reza.feature.home.domain.usecase.ContinentsUseCase
//import com.reza.threading.common.MainDispatcher
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.CoroutineExceptionHandler
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//internal class HomeViewModel @Inject constructor(
//    private val continentsUseCase: ContinentsUseCase,
//    private val continentsImageUseCase: ContinentImageUseCase,
//    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
//) : ViewModel() {
//
//    private val _homeState = MutableStateFlow(HomeState())
//    val homeState = _homeState.asStateFlow()
//
//    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
//        _homeState.update { state ->
//            state.copy(
//                errorMessage = exception.message ?: "Something went wrong, please try again!"
//            )
//        }
//    }
//
//    init {
//        getContinents()
//    }
//
//    private fun getContinents() {
//        viewModelScope.launch(mainDispatcher + exceptionHandler) {
//            // Loading state
//            _homeState.update { state ->
//                state.copy(
//                    isLoading = true
//                )
//            }
//
//            // Getting continents
//            when (val result = continentsUseCase.getContinents()) {
//                is ResultState.Success -> {
//                    _homeState.update { state ->
//                        state.copy(
//                            continents = result.data.map {
//                                ContinentView(
//                                    continent = it,
//                                    imageResource = continentsImageUseCase.findContinentImage(
//                                        it.name ?: ""
//                                    )
//                                )
//                            }, isLoading = false, errorMessage = null
//                        )
//                    }
//                }
//
//                is ResultState.Failure -> {
//                    _homeState.update { state ->
//                        state.copy(
//                            isLoading = false, errorMessage = result.error
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//    fun consumeErrorMessage() {
//        _homeState.update { state ->
//            state.copy(errorMessage = null)
//        }
//    }
//
//    fun onEvent(event: HomeEvent) {
//        when (event) {
//            is HomeEvent.GetContinents -> getContinents()
//        }
//    }
//}