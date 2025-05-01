package command

import dto.CommandDto
import entity.Balances

interface CommandExecutor {

    fun execute(commandDto: CommandDto, balances: Balances)
}