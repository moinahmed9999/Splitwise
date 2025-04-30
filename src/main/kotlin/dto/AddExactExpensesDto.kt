package dto

import model.enums.ExpenseType

data class AddExactExpensesDto(
    override val paidByUserId: String,
    override val amount: Double,
    override val splitAmongUserIds: List<String>,
    val exactSplitAmount: List<Double>,
) : AddExpenseDto(paidByUserId, amount, splitAmongUserIds, ExpenseType.EXACT)
