package dc.bininfo

import android.os.Bundle
import android.transition.Visibility
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dc.bininfo.dao.BinDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {
    private val binDatabase by lazy { BinDatabase.getDatabase(this).binDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        supportActionBar?.title = "История BIN"

        val emptyHistoryText = findViewById<TextView>(R.id.history_empty_text)
        val historyRecycler = findViewById<RecyclerView>(R.id.history_recyclerview)
        val adapter = BinListAdapter()
        historyRecycler.adapter = adapter
        historyRecycler.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            (application as BinApplication).repository.bins.collect { binList ->
                if (binList.isNotEmpty()) {
                    historyRecycler.visibility = View.VISIBLE
                    emptyHistoryText.visibility = View.GONE
                    adapter.submitList(binList)
                } else {
                    historyRecycler.visibility = View.GONE
                    emptyHistoryText.visibility = View.VISIBLE
                }
            }
        }

    }
}