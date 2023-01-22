package dc.bininfo

import android.app.Application
import dc.bininfo.dao.BinDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BinApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val db by lazy { BinDatabase.getDatabase(this) }
    val repository by lazy { BinRepository(db.binDao()) }
}