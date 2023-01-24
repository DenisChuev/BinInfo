package dc.bininfo.ui.history

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dc.bininfo.R
import dc.bininfo.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModels { HistoryViewModel.Factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.bin_history)

        val adapter = HistoryListAdapter()
        binding.historyRecyclerview.adapter = adapter
        binding.historyRecyclerview.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            viewModel.binList.collect { binList ->
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