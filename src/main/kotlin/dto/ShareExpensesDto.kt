package dto

import model.enums.SplitType

data class ShareExpensesDto(
    override val paidByUserId: String,
    override val amount: Double,
    override val splitAmongUserIds: List<String>,
    val splitShare: List<Int>,
) : ExpenseDto(paidByUserId, amount, splitAmongUserIds, SplitType.SHARE)
