package dc.bininfo.api

import dc.bininfo.api.BinInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BinService {
    @GET("{bin}")
    fun getBinInfo(@Path("bin") bin: String): Call<BinInfo>
}