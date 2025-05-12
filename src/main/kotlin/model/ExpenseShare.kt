package model

import entity.Amount
import entity.User

data class ExpenseShare(
    val user: User,
    val amount: Amount,
)
