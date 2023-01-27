package dc.bininfo.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dc.bininfo.BinApplication
import dc.bininfo.R
import dc.bininfo.dao.Bin
import dc.bininfo.databinding.ActivityMainBinding
import dc.bininfo.ui.history.HistoryActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private var longitude: String? = null
    private var latitude: String? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bankPhone.setOnClickListener { openDialer(binding.bankPhone.text.toString()) }
        binding.countryText.setOnClickListener { openMap() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val search = menu?.findItem(R.id.action_search)
        searchView = search?.actionView as SearchView
        searchView.queryHint = getString(R.string.action_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchBin(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateCardInfo(bin: Bin) {
        binding.numberLengthText.text = bin.numberLength.toString()
        binding.numberLuhnText.text = bin.numberLuhn.toString()
        binding.schemeText.text = bin.scheme
        binding.typeText.text = bin.type
        binding.brandText.text = bin.brand

        if (bin.prepaid == true) binding.prepaidText.text =
            getString(R.string.prepaid_yes_text) else binding.prepaidText.text = getString(
            R.string.prepaid_no_text
        )
        binding.countryText.text = "${bin.countryEmoji} ${bin.countryName}"

        binding.bankName.text = "${bin.bankName ?: ""}, ${bin.bankCity ?: ""}"
        binding.bankUrl.text = bin.bankUrl
        binding.bankPhone.text = bin.bankPhone
    }

    private fun openMap() {
        if (longitude != null && latitude != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${latitude},${longitude}"))
            startActivity(intent)
        }
    }

    private fun searchBin(binNum: String) {
        viewModel.searchBin(binNum).observe(this@MainActivity) {
            searchView.isIconified = true

            if (it != null) {
                latitude = it.countryLatitude.toString()
                longitude = it.countryLongitude.toString()
                updateCardInfo(it)
            } else {
                Snackbar
                    .make(
                        binding.root,
                        getString(R.string.bin_not_found),
                        Snackbar.LENGTH_SHORT
                    ).show()
            }
        }
    }

    private fun openDialer(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${phone}}")
        startActivity(intent)
    }

}