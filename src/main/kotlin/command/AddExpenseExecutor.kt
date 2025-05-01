package command

import dto.AddExpenseDto
import dto.CommandDto
import entity.Balances

abstract class AddExpenseExecutor : CommandExecutor {
    override fun execute(commandDto: CommandDto, balances: Balances) {
        if (commandDto !is AddExpenseDto) {
            throw IllegalArgumentException("Invalid command passed")
        }
        val userShares = calculateShare(commandDto, balances)
        userShares
            .filter {
                it.key != commandDto.paidByUserId
            }
            .forEach {
                val user = it.key
                val share = it.value
                balances.updateBalance(user, commandDto.paidByUserId, share)
            }
        balances.reBalance()
    }

    abstract fun calculateShare(commandDto: AddExpenseDto, balances: Balances): MutableMap<String, Double>

    fun updateUserBalanceUpdateAmount(userId: String, amount: Double, userBalanceUpdates: MutableMap<String, Double>) {
        val existingBalance = userBalanceUpdates[userId] ?: 0.0
        userBalanceUpdates[userId] = existingBalance + amount
    }
}