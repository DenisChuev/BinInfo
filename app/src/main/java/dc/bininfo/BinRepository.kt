package dc.bininfo

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import dc.bininfo.api.RetrofitClient
import dc.bininfo.dao.Bin
import dc.bininfo.dao.BinDao
import kotlinx.coroutines.flow.Flow

class BinRepository(private val binDao: BinDao) {
    val bins: Flow<List<Bin>> = binDao.getBinHistory()

    @WorkerThread
    suspend fun addBin(bin: Bin) {
        binDao.addBin(bin)
    }

    suspend fun getBin(binNum: String): Bin? {
        var bin = binDao.getBin(binNum)
        if (bin == null) {
            try {
                val binInfo = RetrofitClient.retrofitService.getBinInfo(binNum)
                if (binInfo != null) {
                    bin = BinConverter.apiToDao(binInfo, binNum)
                    addBin(bin)
                }
            } catch (e: Exception) {
                Log.d("BinRepository", e.message, e)
            }

        }
        return bin
    }

}