package dto

import model.enums.BalanceType
import model.enums.CommandType
import model.enums.SplitType

abstract class ShowBalanceDto(
    val balanceType: BalanceType,
) : CommandDto(CommandType.SHOW_BALANCES)
