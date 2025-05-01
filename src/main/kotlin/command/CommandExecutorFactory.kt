package command

import dto.AddExpenseDto
import dto.CommandDto
import model.enums.CommandType
import model.enums.ExpenseType

object CommandExecutorFactory {
    fun getCommandExecutor(commandDto: CommandDto): CommandExecutor {
        return when(commandDto.commandType) {
            CommandType.SHOW_ALL_BALANCES -> ShowAllBalancesExecutor()
            CommandType.SHOW_USER_BALANCES -> ShowUserBalancesExecutor()
            CommandType.ADD_EXPENSE -> {
                when((commandDto as AddExpenseDto).expenseType) {
                    ExpenseType.EQUAL -> AddEqualExpenseExecutor()
                    ExpenseType.EXACT -> AddExactExpenseExecutor()
                    ExpenseType.PERCENT -> AddPercentExpenseExecutor()
                }
            }
        }
    }
}