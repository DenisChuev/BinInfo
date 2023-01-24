package dc.bininfo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dc.bininfo.BinApplication
import dc.bininfo.BinRepository
import dc.bininfo.dao.Bin
import dc.bininfo.ui.history.HistoryViewModel

class MainViewModel(repository: BinRepository) : ViewModel() {
    private val binNum = MutableLiveData("")
    val bin: LiveData<Bin> = repository.getBin(binNum.value!!)

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

    fun searchBin(binNum: String): LiveData<Bin> {
        this.binNum.value = binNum
        return bin
    }

}