package entity

data class Balances(
    val userBalances: MutableMap<BalancePair, Double>,
) {
    fun updateBalance(owedBy: String, owedTo: String, amount: Double) {
        val balancePair = BalancePair(owedBy, owedTo)
        val existingBalance = userBalances[balancePair] ?: 0.0
        userBalances[balancePair] = existingBalance + amount
    }

    fun reBalance() {
        val balancePairs = userBalances.keys
        balancePairs.forEach { balancePair ->
            val share = userBalances.getValue(balancePair)
            if (share == 0.0) {
                return
            }
            val reverseBalancePair = BalancePair(balancePair.owedTo, balancePair.owedBy)
            if (userBalances.containsKey(reverseBalancePair)) {
                val reverseBalanceShare = userBalances.getValue(reverseBalancePair)
                if (share > reverseBalanceShare) {
                    userBalances[balancePair] = share - reverseBalanceShare
                    userBalances[reverseBalancePair] = 0.0
                } else {
                    userBalances[reverseBalancePair] = reverseBalanceShare - share
                    userBalances[balancePair] = 0.0
                }
            }
        }
    }
}