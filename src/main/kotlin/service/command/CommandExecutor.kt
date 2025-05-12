package service.command

import dto.CommandDto

interface CommandExecutor {

    fun execute(commandDto: CommandDto)
}