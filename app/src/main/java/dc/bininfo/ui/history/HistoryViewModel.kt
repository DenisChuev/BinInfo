package dc.bininfo.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dc.bininfo.BinApplication
import dc.bininfo.BinRepository
import dc.bininfo.dao.Bin
import kotlinx.coroutines.flow.Flow

class HistoryViewModel(repository: BinRepository) : ViewModel() {
    val binList: Flow<List<Bin>> = repository.bins

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = (this[APPLICATION_KEY] as BinApplication).repository
                HistoryViewModel(
                    repository
                )
            }
        }
    }


}