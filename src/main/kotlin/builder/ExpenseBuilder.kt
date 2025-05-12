package builder

import dto.*
import entity.Amount
import entity.Expense
import entity.User
import factory.SplitStrategyFactory
import model.ExpenseBalance
import model.ExpenseContribution
import model.enums.SplitType
import model.split.*

object ExpenseBuilder {

    fun buildExpense(expenseDto: ExpenseDto): Expense {
        val amount = Amount(expenseDto.amount)
        val contributedBy = User(expenseDto.paidByUserId)
        val contributionList = listOf(
            ExpenseContribution(contributedBy, amount)
        )
        val contributions = contributionList.associateBy { it.user }

        val splitDetailList = when(expenseDto.splitType) {
            SplitType.EQUAL -> equalSplitExpense(expenseDto as EqualExpensesDto)
            SplitType.EXACT -> exactSplitExpense(expenseDto as ExactExpensesDto)
            SplitType.PERCENT -> percentSplitExpense(expenseDto as PercentExpensesDto)
            SplitType.SHARE -> shareSplitExpense(expenseDto as ShareExpensesDto)
        }
        val splitDetails = splitDetailList.associateBy { it.user }

        val splitStrategy = SplitStrategyFactory.getSplitStrategy(expenseDto.splitType)
        val expenseShareList = splitStrategy.split(amount, splitDetailList)
        val shares = expenseShareList.associateBy { it.user }

        val allUsers = contributions.keys + shares.keys
        val balanceList = allUsers
            .map { user ->
                val contributionAmountValue = contributions[user]?.amount?.value ?: 0.0
                val shareAmountValue = shares[user]?.amount?.value ?: 0.0
                val balanceAmountValue = contributionAmountValue - shareAmountValue
                ExpenseBalance(user, Amount(balanceAmountValue))
            }
            .filter { it.amount.value != 0.0 }
        val balances = balanceList.associateBy { it.user }

        return Expense(
            amount = amount,
            contributions = contributions,
            splitType = expenseDto.splitType,
            splitDetails = splitDetails,
            shares = shares,
            balances = balances,
        )
    }

    private fun equalSplitExpense(addEqualExpensesDto: EqualExpensesDto): List<SplitDetail> {
        return addEqualExpensesDto.splitAmongUserIds
            .map {
                EqualSplitDetail(User(it))
            }
    }

    private fun exactSplitExpense(addExactExpensesDto: ExactExpensesDto): List<SplitDetail> {
        val splitDetailList = mutableListOf<ExactSplitDetail>()
        IntRange(0, addExactExpensesDto.splitAmongUserIds.size - 1).forEach { idx ->
            val userId = addExactExpensesDto.splitAmongUserIds[idx]
            val splitAmountValue = addExactExpensesDto.exactSplitAmount[idx]
            val splitDetail = ExactSplitDetail(User(userId), Amount(splitAmountValue))
            splitDetailList.add(splitDetail)
        }
        return splitDetailList
    }

    private fun percentSplitExpense(addPercentExpensesDto: PercentExpensesDto): List<SplitDetail> {
        val splitDetailList = mutableListOf<PercentSplitDetail>()
        IntRange(0, addPercentExpensesDto.splitAmongUserIds.size - 1).forEach { idx ->
            val userId = addPercentExpensesDto.splitAmongUserIds[idx]
            val splitPercent = addPercentExpensesDto.splitPercent[idx]
            val splitDetail = PercentSplitDetail(User(userId), splitPercent)
            splitDetailList.add(splitDetail)
        }
        return splitDetailList
    }

    private fun shareSplitExpense(addShareExpensesDto: ShareExpensesDto): List<SplitDetail> {
        val splitDetailList = mutableListOf<ShareSplitDetail>()
        IntRange(0, addShareExpensesDto.splitAmongUserIds.size - 1).forEach { idx ->
            val userId = addShareExpensesDto.splitAmongUserIds[idx]
            val splitShare = addShareExpensesDto.splitShare[idx]
            val splitDetail = ShareSplitDetail(User(userId), splitShare)
            splitDetailList.add(splitDetail)
        }
        return splitDetailList
    }
}