package com.devil.premises.itunesconnect.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devil.premises.itunesconnect.models.ITuneResponse
import com.devil.premises.itunesconnect.repository.ITunesRepository
import com.devil.premises.itunesconnect.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ITunesViewModel(private val iTunesRepository: ITunesRepository) : ViewModel() {
    val searchITunes: MutableLiveData<Resource<ITuneResponse>> = MutableLiveData()

    fun searchITunes(searchQuery: String) = viewModelScope.launch {
        searchITunes.postValue(Resource.Loading())
        val response = iTunesRepository.searchITune(searchQuery)
        searchITunes.postValue(handleSearchITuneResponse(response))
    }

    private fun handleSearchITuneResponse(response: Response<ITuneResponse>): Resource<ITuneResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}