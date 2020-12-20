package com.devil.premises.itunesconnect.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devil.premises.itunesconnect.models.ITuneResponse
import com.devil.premises.itunesconnect.repository.ITunesRepository
import com.devil.premises.itunesconnect.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ITunesViewModel(val itunesRepository: ITunesRepository) : ViewModel() {
    val searchITunes: MutableLiveData<Resource<ITuneResponse>> = MutableLiveData()

    fun searchITunes(searchQuery: String) = viewModelScope.launch {
        searchITunes.postValue(Resource.Loading())
        val response = itunesRepository.searchITune(searchQuery)
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