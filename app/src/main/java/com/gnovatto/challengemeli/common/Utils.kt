package com.gnovatto.challengemeli.common

class Utils {
    companion object {
        fun formatCondition(condition: String): String {
            return if (condition.lowercase() == "new") "Nuevo" else "Usado"
        }

        fun formatSold(soldQuantity: Int): String {
            return "$soldQuantity vendidos"
        }

    }
}