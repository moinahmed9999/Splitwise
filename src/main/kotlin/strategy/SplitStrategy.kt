package strategy

import entity.Amount
import model.ExpenseShare
import model.split.SplitDetail

interface SplitStrategy {
    fun split(
        amount: Amount,
        splitDetails: List<SplitDetail>,
    ): List<ExpenseShare>
}