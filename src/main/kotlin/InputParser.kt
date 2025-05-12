import dto.*
import model.enums.SplitType

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

                    val splitType = SplitType.valueOf(args[index++])
                    when (splitType) {
                        SplitType.EQUAL -> {
                            commandDtoList.add(EqualExpensesDto(paidByUserId, amountPaid, splitAmongUserIds))
                        }
                        SplitType.EXACT -> {
                            val exactSplitAmount = mutableListOf<Double>()
                            repeat(splitAmongUsersCount) {
                                exactSplitAmount.add(args[index++].toDouble())
                            }
                            commandDtoList.add(ExactExpensesDto(paidByUserId, amountPaid, splitAmongUserIds, exactSplitAmount))
                        }
                        SplitType.PERCENT -> {
                            val splitPercent = mutableListOf<Double>()
                            repeat(splitAmongUsersCount) {
                                splitPercent.add(args[index++].toDouble())
                            }
                            commandDtoList.add(PercentExpensesDto(paidByUserId, amountPaid, splitAmongUserIds, splitPercent))
                        }
                        SplitType.SHARE -> {
                            val splitShare = mutableListOf<Int>()
                            repeat(splitAmongUsersCount) {
                                splitShare.add(args[index++].toInt())
                            }
                            commandDtoList.add(ShareExpensesDto(paidByUserId, amountPaid, splitAmongUserIds, splitShare))
                        }
                    }
                }
            }
        }

        return commandDtoList
    }
}