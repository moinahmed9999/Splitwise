package dto

import model.enums.BalanceType

data class ShowUserBalancesDto(
    val userId: String,
) : ShowBalanceDto(BalanceType.USER_BALANCES)
