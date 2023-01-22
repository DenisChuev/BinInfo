package dc.bininfo.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Bin::class],
    version = 1,
    exportSchema = true
)
abstract class BinDatabase : RoomDatabase() {
    abstract fun binDao(): BinDao

    companion object {
        @Volatile
        private var INSTANCE: BinDatabase? = null

        fun getDatabase(context: Context): BinDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): BinDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                BinDatabase::class.java,
                "bin_database"
            )
                .build()
        }
    }
}