import service.BalanceService
import service.CommandProcessorService
import service.ExpenseService

fun main(args: Array<String>) {
    val commands = InputParser.parse()

    val balanceService = BalanceService()
    val expenseService = ExpenseService(balanceService)
    val commandProcessorService = CommandProcessorService(expenseService, balanceService)

    commandProcessorService.processCommands(commands)
}