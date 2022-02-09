package me.davidcosta.github.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.github.model.RepositoryVO
import me.davidcosta.github.repository.SearchRepository
import me.davidcosta.github.utils.LoadState
import me.davidcosta.github.utils.ViewData
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    val repositoryVOListData:LiveData<ViewData<List<RepositoryVO>>>
        get() = _repositoryVOListData

    val hasNextPageData: LiveData<Boolean>
        get() = _hasNextPageData

    val repositoryVOList: List<RepositoryVO>?
        get() = _repositoryVOListData.value?.data

    val hasRepositoryData: Boolean
        get() = repositoryVOList?.isNotEmpty() == true

    private val _repositoryVOListData: MutableLiveData<ViewData<List<RepositoryVO>>> =
        MutableLiveData()

    private val _hasNextPageData: MutableLiveData<Boolean> =
        MutableLiveData()

    internal val hasNextPage: Boolean
        get() = _hasNextPageData.value ?: true

    private var nextPage = 1
    internal var isLoading = false

    fun loadRepositories() {
        if (isLoading) return
        searchRepository
            .searchRepositories(nextPage)
            .doOnSubscribe {
                isLoading = true
                _repositoryVOListData
                    .postValue(
                        ViewData(
                            status = LoadState.LOADING,
                            data = repositoryVOList.orEmpty()
                        )
                    )
            }
            .doOnError {
                _repositoryVOListData
                    .postValue(
                        ViewData(
                            status = LoadState.ERROR,
                            data = repositoryVOList.orEmpty(),
                            error = it
                        )
                    )
                isLoading = false

            }
            .onErrorComplete()
            .subscribe { triple ->
                val hasNextPage = triple.first
                val repositories = triple.third

                nextPage = triple.second ?: -1

                _repositoryVOListData
                    .postValue(
                        ViewData(
                            status = LoadState.SUCCESS,
                            data = repositories
                        )
                    )

                _hasNextPageData.postValue(hasNextPage)
                isLoading = false
            }
    }

    fun loadMoreRepositories() {
        if (isLoading || !hasNextPage) return

        searchRepository
            .searchRepositories(nextPage)
            .doOnSubscribe {
                isLoading = true
                _repositoryVOListData
                    .postValue(
                        ViewData(
                            status = LoadState.LOADING,
                            data = repositoryVOList.orEmpty()
                        )
                    )
            }
            .doOnError {
                _repositoryVOListData
                    .postValue(
                        ViewData(
                            status = LoadState.ERROR,
                            data = repositoryVOList.orEmpty(),
                            error = it
                        )
                    )
                isLoading = false
            }
            .onErrorComplete()
            .subscribe { triple ->
                val hasNextPage = triple.first
                val repositories = triple.third

                nextPage = triple.second ?: -1

                _repositoryVOListData
                    .postValue(
                        ViewData(
                            status = LoadState.SUCCESS,
                            data = repositoryVOList?.plus(repositories)
                                ?: repositories
                        )
                    )

                _hasNextPageData.postValue(hasNextPage)
                isLoading = false
            }
    }
}