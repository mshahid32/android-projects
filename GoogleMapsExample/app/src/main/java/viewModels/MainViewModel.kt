package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import models.Marker
import repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(private val repository: MainRepository) : ViewModel() {

    fun MyPost(): MutableLiveData<ArrayList<Marker>> {
        return repository.getLocation()
    }
}