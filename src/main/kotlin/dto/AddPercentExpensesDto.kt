package dto

import model.enums.ExpenseType

data class AddPercentExpensesDto(
    override val paidByUserId: String,
    override val amount: Double,
    override val splitAmongUserIds: List<String>,
    val splitPercent: List<Double>,
) : AddExpenseDto(paidByUserId, amount, splitAmongUserIds, ExpenseType.PERCENT)
