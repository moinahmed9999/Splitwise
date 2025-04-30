package dto

import model.enums.CommandType

abstract class CommandDto(
    val commandType: CommandType,
)