package dc.bininfo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dc.bininfo.api.BinInfo
import dc.bininfo.api.RetrofitClient
import dc.bininfo.dao.Bin
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity"
    lateinit var numberLuhn: TextView
    lateinit var numberLength: TextView
    lateinit var scheme: TextView
    lateinit var type: TextView
    lateinit var brand: TextView
    lateinit var prepaid: TextView
    lateinit var country: TextView
    lateinit var bankName: TextView
    lateinit var bankUrl: TextView
    lateinit var bankPhone: TextView
    lateinit var mainView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainView = findViewById(R.id.main_view)
        numberLuhn = findViewById(R.id.number_luhn_text)
        numberLength = findViewById(R.id.number_length_text)
        scheme = findViewById(R.id.scheme_text)
        type = findViewById(R.id.type_text)
        brand = findViewById(R.id.brand_text)
        prepaid = findViewById(R.id.prepaid_text)
        country = findViewById(R.id.country_text)
        bankName = findViewById(R.id.bank_name)
        bankUrl = findViewById(R.id.bank_url)
        bankPhone = findViewById(R.id.bank_phone)

        bankPhone.setOnClickListener { openDialer(bankPhone.text.toString()) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val search = menu?.findItem(R.id.action_search)
        val searchView = search?.actionView as SearchView
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

    @SuppressLint("SetTextI18n")
    private fun updateCardInfo(bin: Bin) {
        numberLength.text = bin.numberLength.toString()
        numberLuhn.text = bin.numberLuhn.toString()
        scheme.text = bin.scheme
        type.text = bin.type
        brand.text = bin.brand

        if (bin.prepaid == true) prepaid.text =
            getString(R.string.prepaid_yes_text) else prepaid.text = getString(
            R.string.prepaid_no_text
        )
        country.text =
            "${bin.countryEmoji} " +
                    "${bin.countryName} " +
                    "latitude: ${bin.countryLat} " +
                    "longitude: ${bin.countryLon}"


        if (bin.bankName != null) {
            bankName.text = "${bin.bankName ?: ""}, ${bin.bankCity ?: ""}"
            bankUrl.text = bin.bankUrl
            bankPhone.text = bin.bankPhone
        }
    }

    private fun searchBin(binNum: String) {
        lifecycleScope.launch {
            (application as BinApplication).repository.getBin(binNum).observe(this@MainActivity) {
                if (it != null) {
                    updateCardInfo(it)
                } else {
                    RetrofitClient.retrofitService.getBinInfo(binNum)
                        .enqueue(object : Callback<BinInfo> {
                            override fun onResponse(
                                call: Call<BinInfo>,
                                response: Response<BinInfo>
                            ) {
                                if (response.body() != null) {
                                    val bin: Bin = BinConverter.apiToDao(
                                        response.body()!!,
                                        binNum
                                    )
                                    updateCardInfo(bin)
                                    lifecycleScope.launch {
                                        (application as BinApplication).repository.addBin(bin)
                                    }

                                } else if (response.code() == 404) {
                                    Snackbar
                                        .make(
                                            mainView,
                                            getString(R.string.bin_not_found),
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                }
                            }

                            override fun onFailure(call: Call<BinInfo>, t: Throwable) {
                                Log.e(TAG, t.message, t)
                            }
                        })
                }
            }
        }
    }

    private fun openDialer(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${phone}}")
        startActivity(intent)
    }

}