package dc.bininfo.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin")
data class Bin(
    @PrimaryKey
    var binNum: String,
    var scheme: String?,
    var type: String?,
    var brand: String?,
    var prepaid: Boolean?,
    var numberLength: Int?,
    var numberLuhn: Boolean?,
    var bankName: String?,
    var bankUrl: String?,
    var bankPhone: String?,
    var bankCity: String?,
    var countryName: String?,
    var countryEmoji: String?,
    var countryLongitude: Double?,
    var countryLatitude: Double?,
    var countryCurrency: String?
)