package dc.bininfo.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin")
data class Bin(
    @PrimaryKey
    var bin_num: String,
    var scheme: String,
    var type: String,
    var brand: String,
    var prepaid: Boolean,
    var length: Int,
    var luhn: Boolean,
    var bank_name: String?,
    var bank_url: String?,
    var bank_phone: String?,
    var bank_city: String?,
    var country_name: String,
    var country_emoji: String,
    var longitude: Double,
    var latitude: Double
)