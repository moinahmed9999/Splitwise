package command

import dto.CommandDto
import dto.ShowAllBalancesDto
import entity.Balances

class ShowAllBalancesExecutor : CommandExecutor {
    override fun execute(commandDto: CommandDto, balances: Balances) {
        if (commandDto !is ShowAllBalancesDto) {
            throw IllegalArgumentException("Invalid command passed")
        }
        val balancesToShow = balances.userBalances
            .filter { it.value != 0.0 }
            .toList()
            .sortedWith(compareBy({ it.first.owedBy }, { it.first.owedTo }))
        if (balancesToShow.isEmpty()) {
            println("No balances")
        } else {
            balancesToShow.forEach {
                println("${it.first.owedBy} owes ${it.first.owedTo}: ${it.second}")
            }
        }
    }
}