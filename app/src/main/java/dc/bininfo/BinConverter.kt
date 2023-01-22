package dc.bininfo

import dc.bininfo.api.BinInfo
import dc.bininfo.dao.Bin

object  BinConverter {
    fun apiToDao(binInfo: BinInfo, binNum: String): Bin {
        return Bin(
            binNum,
            binInfo.scheme,
            binInfo.type,
            binInfo.brand,
            binInfo.prepaid,
            binInfo.number.length,
            binInfo.number.luhn,
            binInfo.bank.name,
            binInfo.bank.url,
            binInfo.bank.phone,
            binInfo.bank.city,
            binInfo.country.name,
            binInfo.country.emoji,
            binInfo.country.longitude,
            binInfo.country.latitude
        )
    }
}