package service

import entity.Amount
import entity.Balance
import entity.BalancePair
import entity.User
import model.ExpenseBalance
import model.ExpenseShare
import java.util.*

class BalanceService {

    private val balances: MutableMap<BalancePair, Balance> = mutableMapOf()
    
    fun showAllBalances() {
        val balancesToShow = balances.values
            .filter { it.amount.value != 0.0 }
            .toList()
            .sortedWith(compareBy({ it.pair.owedBy.id }, { it.pair.owedTo.id }))
        showBalances(balancesToShow)
    }

    fun showUserBalances(user: User) {
        val balancesToShow = balances.values
            .filter { it.amount.value != 0.0 }
            .filter { it.pair.owedBy.id == user.id || it.pair.owedTo.id == user.id }
            .toList()
            .sortedWith(compareBy({ it.pair.owedBy.id }, { it.pair.owedTo.id }))
        showBalances(balancesToShow)
    }

    fun addExpenseBalances(balances: Map<User, ExpenseBalance>) {
        // PriorityQueue for creditors (max-heap based on amount)
        val creditors = PriorityQueue<Pair<User, Double>>(compareByDescending { it.second })
        // PriorityQueue for debtors (max-heap based on debt)
        val debtors = PriorityQueue<Pair<User, Double>>(compareByDescending { it.second })

        for ((user, share) in balances) {
            val value = share.amount.value
            when {
                value > 0 -> creditors.add(user to value)
                value < 0 -> debtors.add(user to -value) // Convert to positive for comparison
            }
        }

        while (creditors.isNotEmpty() && debtors.isNotEmpty()) {
            val (creditor, creditAmount) = creditors.poll()
            val (debtor, debtAmount) = debtors.poll()

            val minAmount = minOf(creditAmount, debtAmount)

            val pair = BalancePair(owedBy = debtor, owedTo = creditor)
            addBalance(pair, Amount(minAmount))

            // Remaining balance after settlement
            val remainingCredit = creditAmount - minAmount
            val remainingDebt = debtAmount - minAmount

            if (remainingCredit > 0.0) creditors.add(creditor to remainingCredit)
            if (remainingDebt > 0.0) debtors.add(debtor to remainingDebt)
        }

        reBalance()
    }
    
    private fun showBalances(balances: List<Balance>) {
        if (balances.isEmpty()) {
            println("No balances")
        } else {
            balances.forEach {
                println("${it.pair.owedBy.id} owes ${it.pair.owedTo.id}: ${it.amount.value}")
            }
        }
    }

    private fun addBalance(pair: BalancePair, amount: Amount) {
        val existingAmountValue = balances[pair]?.amount?.value ?: 0.0
        val newAmount = amount.copy(
            value = amount.value + existingAmountValue
        )
        balances[pair] = Balance(pair, newAmount)
    }

    private fun updateBalance(pair: BalancePair, amount: Amount) {
        balances[pair] = Balance(pair, amount)
    }

    private fun reBalance() {
        val visited = mutableSetOf<BalancePair>()

        for (pair in balances.keys) {
            if (pair in visited) continue

            val balance = balances[pair] ?: continue
            if (balance.amount.value == 0.0) continue

            val reversePair = BalancePair(pair.owedTo, pair.owedBy)
            val reverseBalance = balances[reversePair] ?: continue

            val forwardValue = balance.amount.value
            val reverseValue = reverseBalance.amount.value

            when {
                forwardValue > reverseValue -> {
                    updateBalance(pair, Amount(forwardValue - reverseValue))
                    updateBalance(reversePair, Amount(0.0))
                }
                reverseValue > forwardValue -> {
                    updateBalance(reversePair, Amount(reverseValue - forwardValue))
                    updateBalance(pair, Amount(0.0))
                }
                else -> {
                    // Both are equal
                    updateBalance(pair, Amount(0.0))
                    updateBalance(reversePair, Amount(0.0))
                }
            }

            visited.add(pair)
            visited.add(reversePair)
        }
    }
}