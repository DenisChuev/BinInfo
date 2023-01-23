package dc.bininfo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import dc.bininfo.api.BinInfo
import dc.bininfo.dao.Bin
import dc.bininfo.dao.BinDao
import kotlinx.coroutines.flow.Flow

class BinRepository(private val binDao: BinDao) {
    val bins: Flow<List<Bin>> = binDao.getBinHistory()

    @WorkerThread
    suspend fun addBin(bin: Bin) {
        binDao.addBin(bin)
    }

    fun getBin(binNum: String): LiveData<Bin> {
        return binDao.getBin(binNum)
    }

}