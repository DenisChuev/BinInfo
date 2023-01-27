package dc.bininfo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dc.bininfo.BinApplication
import dc.bininfo.BinRepository
import dc.bininfo.dao.Bin
import kotlinx.coroutines.launch

class MainViewModel(private val repository: BinRepository) : ViewModel() {
    val bin = MutableLiveData<Bin?>(null)

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BinApplication).repository
                MainViewModel(
                    repository
                )
            }
        }
    }

    fun searchBin(binNum: String): MutableLiveData<Bin?> {
        viewModelScope.launch {
            bin.value = repository.getBin(binNum)
        }
        return bin
    }

}