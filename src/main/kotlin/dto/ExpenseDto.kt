package dto

import model.enums.CommandType
import model.enums.SplitType

abstract class ExpenseDto(
    open val paidByUserId: String,
    open val amount: Double,
    open val splitAmongUserIds: List<String>,
    val splitType: SplitType,
) : CommandDto(CommandType.ADD_EXPENSE)
