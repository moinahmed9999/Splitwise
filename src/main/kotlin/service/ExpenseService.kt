package service

import builder.ExpenseBuilder
import dto.ExpenseDto
import entity.BalancePair
import entity.Expense

class ExpenseService(
    private val balanceService: BalanceService,
) {

    private val expenses: MutableList<Expense> = mutableListOf()

    fun addExpense(expenseDto: ExpenseDto) {
        val expense = ExpenseBuilder.buildExpense(expenseDto)
        expenses.add(expense)

        balanceService.addExpenseBalances(expense.balances)
    }
}