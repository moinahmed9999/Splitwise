package dto

import model.enums.CommandType

data class ShowUserBalancesDto(
    val userId: String,
) : CommandDto(CommandType.SHOW_USER_BALANCES)
