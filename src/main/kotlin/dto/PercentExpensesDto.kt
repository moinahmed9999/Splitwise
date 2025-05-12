package dto

import model.enums.SplitType

data class PercentExpensesDto(
    override val paidByUserId: String,
    override val amount: Double,
    override val splitAmongUserIds: List<String>,
    val splitPercent: List<Double>,
) : ExpenseDto(paidByUserId, amount, splitAmongUserIds, SplitType.PERCENT)
