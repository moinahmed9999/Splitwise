package model

import entity.Amount
import entity.User

data class ExpenseBalance(
    val user: User,
    val amount: Amount,
)
