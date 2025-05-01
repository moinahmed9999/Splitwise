package command

import dto.AddExactExpensesDto
import dto.AddExpenseDto
import entity.Balances

class AddExactExpenseExecutor : AddExpenseExecutor() {

    override fun calculateShare(commandDto: AddExpenseDto, balances: Balances): MutableMap<String, Double> {
        if (commandDto !is AddExactExpensesDto) {
            throw IllegalArgumentException("Invalid command passed")
        }
        val userBalanceUpdates = mutableMapOf<String, Double>()
        IntRange(0, commandDto.splitAmongUserIds.size - 1).forEach {
            val user = commandDto.splitAmongUserIds[it]
            val share = commandDto.exactSplitAmount[it]
            updateUserBalanceUpdateAmount(user, share, userBalanceUpdates)
        }
        return userBalanceUpdates
    }
}