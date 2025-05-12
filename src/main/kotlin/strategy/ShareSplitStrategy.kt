package strategy

import entity.Amount
import model.split.ShareSplitDetail
import model.ExpenseShare
import model.split.SplitDetail

class ShareSplitStrategy : SplitStrategy {
    override fun split(amount: Amount, splitDetails: List<SplitDetail>): List<ExpenseShare> {
        val totalShareCount = splitDetails.sumOf { (it as ShareSplitDetail).count }
        return splitDetails.map {
            val shareCount = (it as ShareSplitDetail).count
            val shareValue = (shareCount * amount.value) / totalShareCount
            val share = amount.copy(
                value = shareValue
            )
            ExpenseShare(it.user, share)
        }
    }
}