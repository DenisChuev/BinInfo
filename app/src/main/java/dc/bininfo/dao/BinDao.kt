package dc.bininfo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BinDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBin(bin: Bin)

    @Query("SELECT * FROM bin ORDER BY bin_num ASC")
    fun getBinHistory(): Flow<List<Bin>>
}