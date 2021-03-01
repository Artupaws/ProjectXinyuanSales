package project.xinyuan.sales.helper

import java.text.NumberFormat
import java.util.*

class Helper {

    fun convertToFormatMoneyIDR(formatMoney: String): String? {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 0
        formatter.currency = Currency.getInstance("IDR")
        return formatter.format(formatMoney.toDouble())
    }

    fun changeFormatMoneyToValue(formatRupiah: String): String {
        val xRupiah = formatRupiah.substring(0)
        val doubleRupiah = xRupiah.substring(0)
        val valueRupiah = doubleRupiah.replace("[-+.^:,]".toRegex(), "")
        return valueRupiah.replace(" ".toRegex(), "")
    }

    fun changeFormatMoney(formatRupiah: String?): String? {
        val xRupiah = formatRupiah?.substring(1)
        val doubleRupiah = xRupiah?.substring(2)
        val valueRupiah = doubleRupiah?.replace("[-+.^:,]".toRegex(), "")
        return valueRupiah?.replace(" ".toRegex(), "")
    }

}