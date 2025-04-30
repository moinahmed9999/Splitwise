package dto

import model.enums.CommandType
import model.enums.ExpenseType

abstract class AddExpenseDto(
    open val paidByUserId: String,
    open val amount: Double,
    open val splitAmongUserIds: List<String>,
    val expenseType: ExpenseType,
) : CommandDto(CommandType.ADD_EXPENSE)
