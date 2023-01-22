package dc.bininfo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dc.bininfo.api.BinInfo
import dc.bininfo.api.RetrofitClient
import dc.bininfo.dao.BinDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity"
    lateinit var numberLuhn: TextView
    lateinit var numberLength: TextView
    lateinit var searchInput: EditText
    lateinit var searchBtn: ImageButton
    lateinit var scheme: TextView
    lateinit var type: TextView
    lateinit var brand: TextView
    lateinit var prepaid: TextView
    lateinit var country: TextView
    lateinit var bankName: TextView
    lateinit var bankUrl: TextView
    lateinit var bankPhone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberLuhn = findViewById(R.id.number_luhn_text)
        numberLength = findViewById(R.id.number_length_text)
        searchInput = findViewById(R.id.search_input)
        searchBtn = findViewById(R.id.search_button)
        scheme = findViewById(R.id.scheme_text)
        type = findViewById(R.id.type_text)
        brand = findViewById(R.id.brand_text)
        prepaid = findViewById(R.id.prepaid_text)
        country = findViewById(R.id.country_text)
        bankName = findViewById(R.id.bank_name)
        bankUrl = findViewById(R.id.bank_url)
        bankPhone = findViewById(R.id.bank_phone)

        searchBtn.setOnClickListener { searchBinInfo() }
//        bankPhone.setOnClickListener { openDialer(bankPhone.text.toString()) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
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
    private fun updateCardInfo(binInfo: BinInfo) {
        numberLength.text = binInfo.number.length.toString()
        numberLuhn.text = binInfo.number.luhn.toString()
        scheme.text = binInfo.scheme
        type.text = binInfo.type
        brand.text = binInfo.brand

        if (binInfo.prepaid) prepaid.text = "Да" else prepaid.text = "Нет"
        country.text =
            "${binInfo.country.emoji} " +
                    "${binInfo.country.name} " +
                    "latitude: ${binInfo.country.latitude} " +
                    "longitude: ${binInfo.country.longitude}"


        if (binInfo.bank != null) {
            bankName.text = "${binInfo.bank?.name ?: ""}, ${binInfo.bank?.city ?: ""}"
            bankUrl.text = binInfo.bank.url
            bankPhone.text = binInfo.bank.phone
        }
    }

    private fun searchBinInfo() {
        RetrofitClient.retrofitService.getBinInfo(searchInput.text.toString())
            .enqueue(object : Callback<BinInfo> {
                override fun onResponse(call: Call<BinInfo>, response: Response<BinInfo>) {

                    if (response.body() != null) {
                        updateCardInfo(response.body()!!)
                        lifecycleScope.launch {
                            (application as BinApplication).repository.addBin(
                                BinConverter.apiToDao(
                                    response.body()!!,
                                    searchInput.text.toString()
                                )
                            )
                        }

                    } else if (response.code() == 404) {
                        Toast.makeText(
                            applicationContext,
                            "БИН не найден",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<BinInfo>, t: Throwable) {
                    Log.e(TAG, t.message, t)
                }
            })
    }

    private fun openDialer(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${phone}}")
        startActivity(intent)
    }

}