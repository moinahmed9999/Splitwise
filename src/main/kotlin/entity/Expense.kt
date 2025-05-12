package entity

import model.ExpenseBalance
import model.ExpenseContribution
import model.enums.SplitType
import model.ExpenseShare
import model.split.SplitDetail

data class Expense(
    val name: String? = null,
    val amount: Amount,
    val contributions: Map<User, ExpenseContribution>,
    val splitType: SplitType,
    val splitDetails: Map<User, SplitDetail>,
    val shares: Map<User, ExpenseShare>,
    val balances: Map<User, ExpenseBalance>,
    val notes: String? = null,
    val images: List<String>? = null,
)