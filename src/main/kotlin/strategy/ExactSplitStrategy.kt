package strategy

import entity.Amount
import model.split.ExactSplitDetail
import model.ExpenseShare
import model.split.SplitDetail

class ExactSplitStrategy : SplitStrategy {
    override fun split(amount: Amount, splitDetails: List<SplitDetail>): List<ExpenseShare> {
        return splitDetails.map {
            val shareAmount = (it as ExactSplitDetail).amount
            ExpenseShare(it.user, shareAmount)
        }
    }
}