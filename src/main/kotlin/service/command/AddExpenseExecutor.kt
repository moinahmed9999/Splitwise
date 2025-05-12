package service.command

import dto.ExpenseDto
import dto.CommandDto
import service.ExpenseService

class AddExpenseExecutor(
    private val expenseService: ExpenseService,
) : CommandExecutor {
    override fun execute(commandDto: CommandDto) {
        if (commandDto !is ExpenseDto) {
            throw IllegalArgumentException("Invalid command passed")
        }
        expenseService.addExpense(commandDto)
    }
}