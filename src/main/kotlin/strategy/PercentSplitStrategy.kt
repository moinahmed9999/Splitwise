package strategy

import entity.Amount
import model.split.PercentSplitDetail
import model.ExpenseShare
import model.split.SplitDetail

class PercentSplitStrategy : SplitStrategy {
    override fun split(amount: Amount, splitDetails: List<SplitDetail>): List<ExpenseShare> {
        return splitDetails.map {
            val percent = (it as PercentSplitDetail).percent
            val shareValue = (percent * amount.value) / 100.0
            val share = amount.copy(
                value = shareValue
            )
            ExpenseShare(it.user, share)
        }
    }
}