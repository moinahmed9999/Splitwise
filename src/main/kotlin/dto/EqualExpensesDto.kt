package dto

import model.enums.SplitType

data class EqualExpensesDto(
    override val paidByUserId: String,
    override val amount: Double,
    override val splitAmongUserIds: List<String>,
) : ExpenseDto(paidByUserId, amount, splitAmongUserIds, SplitType.EQUAL)
