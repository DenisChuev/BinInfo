package dc.bininfo.api

import retrofit2.http.GET
import retrofit2.http.Path

interface BinService {
    @GET("{bin}")
    suspend fun getBinInfo(@Path("bin") bin: String): BinInfo?
}