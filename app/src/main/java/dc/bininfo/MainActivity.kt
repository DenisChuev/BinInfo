package dc.bininfo

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dc.bininfo.api.BinInfo
import dc.bininfo.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resp = findViewById<TextView>(R.id.response_text)
        val numberLuhn = findViewById<TextView>(R.id.number_luhn_text)
        val numberLength = findViewById<TextView>(R.id.number_length_text)
        val searchInput = findViewById<EditText>(R.id.search_input)
        val searchBtn = findViewById<ImageButton>(R.id.search_button)
        searchBtn.setOnClickListener {
            RetrofitClient.retrofitService.getBinInfo(searchInput.text.toString())
                .enqueue(object : Callback<BinInfo> {
                    override fun onResponse(call: Call<BinInfo>, response: Response<BinInfo>) {

                        if (response.body() != null) {
                            val binInfo = response.body()
                            resp.text = binInfo.toString()
                            numberLength.text = binInfo?.number?.length.toString()
                            numberLuhn.text = binInfo?.number?.luhn.toString()
                        } else if (response.code() == 404) {
                            Toast.makeText(
                                applicationContext,
                                "БИН не найден",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BinInfo>, t: Throwable) {
                        Log.e("MainActivity", t?.message, t)
                    }
                })

        }

    }
}