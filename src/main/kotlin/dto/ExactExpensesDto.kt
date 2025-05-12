package dto

import model.enums.SplitType

data class ExactExpensesDto(
    override val paidByUserId: String,
    override val amount: Double,
    override val splitAmongUserIds: List<String>,
    val exactSplitAmount: List<Double>,
) : ExpenseDto(paidByUserId, amount, splitAmongUserIds, SplitType.EXACT)
