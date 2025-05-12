package model

import entity.Amount
import entity.User

data class ExpenseContribution(
    val user: User,
    val amount: Amount,
)
