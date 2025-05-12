package service

import dto.CommandDto
import service.command.CommandExecutorFactory

class CommandProcessorService(
    expenseService: ExpenseService,
    balanceService: BalanceService,
) {

    private val commandExecutorFactory = CommandExecutorFactory(expenseService, balanceService)

    fun processCommands(commands: List<CommandDto>) {
        commands.forEach {
            commandExecutorFactory.getCommandExecutor(it).execute(it)
        }
    }
}