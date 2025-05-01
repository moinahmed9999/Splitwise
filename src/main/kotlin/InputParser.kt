import dto.*
import model.enums.ExpenseType

object InputParser {
    fun parse(): List<CommandDto> {
        val inputStream = object {}.javaClass.getResourceAsStream("/input.txt")
        val lines = inputStream?.bufferedReader()?.readLines()

        val commandDtoList = mutableListOf<CommandDto>()
        lines?.forEach { line ->
            if (line == "SHOW") {
                commandDtoList.add(ShowAllBalancesDto())
            } else {
                val args = line.split(" ")

                var index = 0
                val command = args[index++]

                if (command == "SHOW") {
                    val userId = args[index]
                    commandDtoList.add(ShowUserBalancesDto(userId))
                } else {
                    val paidByUserId = args[index++]
                    val amountPaid = args[index++].toDouble()

                    val splitAmongUsersCount = args[index++].toInt()
                    val splitAmongUserIds = mutableListOf<String>()
                    repeat(splitAmongUsersCount) {
                        splitAmongUserIds.add(args[index++])
                    }

                    val expenseType = ExpenseType.valueOf(args[index++])
                    when (expenseType) {
                        ExpenseType.EQUAL -> {
                            commandDtoList.add(AddEqualExpensesDto(paidByUserId, amountPaid, splitAmongUserIds))
                        }
                        ExpenseType.EXACT -> {
                            val exactSplitAmount = mutableListOf<Double>()
                            repeat(splitAmongUsersCount) {
                                exactSplitAmount.add(args[index++].toDouble())
                            }
                            commandDtoList.add(AddExactExpensesDto(paidByUserId, amountPaid, splitAmongUserIds, exactSplitAmount))
                        }
                        ExpenseType.PERCENT -> {
                            val splitPercent = mutableListOf<Double>()
                            repeat(splitAmongUsersCount) {
                                splitPercent.add(args[index++].toDouble())
                            }
                            commandDtoList.add(AddPercentExpensesDto(paidByUserId, amountPaid, splitAmongUserIds, splitPercent))
                        }
                    }
                }
            }
        }

        return commandDtoList
    }
}