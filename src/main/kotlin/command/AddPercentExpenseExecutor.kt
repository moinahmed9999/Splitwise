package command

import dto.AddExpenseDto
import dto.AddPercentExpensesDto
import entity.Balances

class AddPercentExpenseExecutor : AddExpenseExecutor() {

    override fun calculateShare(commandDto: AddExpenseDto, balances: Balances): MutableMap<String, Double> {
        if (commandDto !is AddPercentExpensesDto) {
            throw IllegalArgumentException("Invalid command passed")
        }
        val userBalanceUpdates = mutableMapOf<String, Double>()
        IntRange(0, commandDto.splitAmongUserIds.size - 1).forEach {
            val user = commandDto.splitAmongUserIds[it]
            val percentShare = commandDto.splitPercent[it]
            val share = (percentShare * commandDto.amount) / 100.0
            updateUserBalanceUpdateAmount(user, share, userBalanceUpdates)
        }
        return userBalanceUpdates
    }
}