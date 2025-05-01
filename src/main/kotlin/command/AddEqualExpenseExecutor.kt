package command

import dto.AddExpenseDto
import entity.Balances

class AddEqualExpenseExecutor : AddExpenseExecutor() {

    override fun calculateShare(commandDto: AddExpenseDto, balances: Balances): MutableMap<String, Double> {
        val userBalanceUpdates = mutableMapOf<String, Double>()
        val equalSplit = commandDto.amount / commandDto.splitAmongUserIds.size
        commandDto.splitAmongUserIds.forEach { user ->
            updateUserBalanceUpdateAmount(user, equalSplit, userBalanceUpdates)
        }
        return userBalanceUpdates
    }
}