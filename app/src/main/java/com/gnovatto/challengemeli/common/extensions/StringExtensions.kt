package com.gnovatto.challengemeli.common.extensions

fun String.formatPrice(currency: String) : String{
    val amountFormated = formatNumber(this)
    return when (currency) {
        "ARS" -> "$ $amountFormated"
        "USD" -> "u\$d $amountFormated"
        else -> "$currency $amountFormated"
    }
}

private fun formatNumber(number: String): String {
    val decimalSeparator = ','
    val thousandsSeparator = '.'

    val parts = number.split('.')
    val wholePart = parts[0].toLongOrNull()?.toString()?.reversed()
    val decimalPart = parts.getOrNull(1)

    val formattedNumber = StringBuilder()
    if (wholePart != null) {
        for (i in wholePart.indices) {
            formattedNumber.append(wholePart[i])
            if ((i + 1) % 3 == 0 && (i + 1) != wholePart.length) {
                formattedNumber.append(thousandsSeparator)
            }
        }
    }
    formattedNumber.reverse()
    if (decimalPart != null) {
        formattedNumber.append(decimalSeparator)
        formattedNumber.append(decimalPart)
    }
    return formattedNumber.toString()
}