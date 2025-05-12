package service.command

import dto.CommandDto
import model.enums.CommandType
import service.BalanceService
import service.ExpenseService

class CommandExecutorFactory(
    expenseService: ExpenseService,
    balanceService: BalanceService,
)  {

    private val showAllBalancesExecutor = ShowBalanceExecutor(balanceService)
    private val addExpenseExecutor = AddExpenseExecutor(expenseService)

    fun getCommandExecutor(commandDto: CommandDto): CommandExecutor {
        return when(commandDto.commandType) {
            CommandType.SHOW_BALANCES -> showAllBalancesExecutor
            CommandType.ADD_EXPENSE -> addExpenseExecutor
        }
    }
}