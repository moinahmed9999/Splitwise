package strategy

import entity.Amount
import model.ExpenseShare
import model.split.SplitDetail

class EqualSplitStrategy : SplitStrategy {
    override fun split(amount: Amount, splitDetails: List<SplitDetail>): List<ExpenseShare> {
        val participantsCount = splitDetails.size
        val equalShareValue = amount.value / participantsCount
        val equalShare = amount.copy(
            value = equalShareValue,
        )
        return splitDetails.map {
            ExpenseShare(it.user, equalShare)
        }
    }
}