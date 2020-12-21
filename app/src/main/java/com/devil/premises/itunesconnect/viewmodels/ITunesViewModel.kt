package com.devil.premises.itunesconnect.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devil.premises.itunesconnect.ITuneApplication
import com.devil.premises.itunesconnect.models.ITuneResponse
import com.devil.premises.itunesconnect.models.Result
import com.devil.premises.itunesconnect.repository.ITunesRepository
import com.devil.premises.itunesconnect.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ITunesViewModel(app: Application, private val iTunesRepository: ITunesRepository) : AndroidViewModel(app) {
    val searchITunes: MutableLiveData<Resource<ITuneResponse>> = MutableLiveData()

    fun searchITunes(searchQuery: String) = viewModelScope.launch {
        safeSearchITune(searchQuery)
    }

    private fun handleSearchITuneResponse(response: Response<ITuneResponse>): Resource<ITuneResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveResult(result: Result) = viewModelScope.launch {
        iTunesRepository.upsert(result)
    }

    fun  getSavedITunes() = iTunesRepository.getSavedITunes()

    fun  deleteResults(result: Result) = viewModelScope.launch {
        iTunesRepository.deleteResult(result)
    }

    private suspend fun safeSearchITune(searchQuery: String){
        searchITunes.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = iTunesRepository.searchITune(searchQuery)
                searchITunes.postValue(handleSearchITuneResponse(response))
            }else{
                searchITunes.postValue(Resource.Error("No internet connection"))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> searchITunes.postValue(Resource.Error("Network Failure"))
                else -> searchITunes.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    fun hasInternetConnection(): Boolean{
        val connectivityManager = getApplication<ITuneApplication>().getSystemService(
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}