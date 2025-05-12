package dto

import model.enums.BalanceType

class ShowAllBalancesDto : ShowBalanceDto(BalanceType.ALL_BALANCES)
