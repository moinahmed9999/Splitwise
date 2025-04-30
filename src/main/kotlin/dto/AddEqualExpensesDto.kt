package dto

import model.enums.ExpenseType

data class AddEqualExpensesDto(
    override val paidByUserId: String,
    override val amount: Double,
    override val splitAmongUserIds: List<String>,
) : AddExpenseDto(paidByUserId, amount, splitAmongUserIds, ExpenseType.EQUAL)
