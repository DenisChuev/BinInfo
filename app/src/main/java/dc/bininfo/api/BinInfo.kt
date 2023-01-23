package dc.bininfo.api

data class BinInfo(
    var number: NumberInfo?,
    var scheme: String?,
    var type: String?,
    var brand: String?,
    var prepaid: Boolean?,
    var country: CountryInfo?,
    var bank: BankInfo?
)

data class NumberInfo(
    var length: Int,
    var luhn: Boolean
)

data class BankInfo(
    var name: String,
    var url: String,
    var phone: String,
    var city: String
)

data class CountryInfo(
    var numeric: String,
    var alpha2: String,
    var name: String,
    var emoji: String,
    var currency: String,
    var latitude: Double,
    var longitude: Double
)
