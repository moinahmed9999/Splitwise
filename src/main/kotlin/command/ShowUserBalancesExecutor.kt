package command

import dto.CommandDto
import dto.ShowUserBalancesDto
import entity.Balances

class ShowUserBalancesExecutor : CommandExecutor {
    override fun execute(commandDto: CommandDto, balances: Balances) {
        if (commandDto !is ShowUserBalancesDto) {
            throw IllegalArgumentException("Invalid command passed")
        }
        val validBalances = balances.userBalances
            .filter { it.value != 0.0 }
        val balancesToShow = validBalances
            .filter { it.key.owedBy == commandDto.userId || it.key.owedTo == commandDto.userId }
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