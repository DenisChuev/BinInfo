package dc.bininfo.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dc.bininfo.BinApplication
import dc.bininfo.R
import dc.bininfo.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.bin_history)

        val adapter = BinListAdapter()
        binding.historyRecyclerview.adapter = adapter
        binding.historyRecyclerview.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            (application as BinApplication).repository.bins.collect { binList ->
                if (binList.isNotEmpty()) {
                    binding.historyRecyclerview.visibility = View.VISIBLE
                    binding.historyEmptyText.visibility = View.GONE
                    adapter.submitList(binList)
                } else {
                    binding.historyRecyclerview.visibility = View.GONE
                    binding.historyEmptyText.visibility = View.VISIBLE
                }
            }
        }

    }
}