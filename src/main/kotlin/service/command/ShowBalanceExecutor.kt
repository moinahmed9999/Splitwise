package service.command

import dto.CommandDto
import dto.ShowBalanceDto
import dto.ShowUserBalancesDto
import entity.User
import model.enums.BalanceType
import service.BalanceService

class ShowBalanceExecutor(
    private val balanceService: BalanceService,
) : CommandExecutor {
    override fun execute(commandDto: CommandDto) {
        if (commandDto !is ShowBalanceDto) {
            throw IllegalArgumentException("Invalid command passed")
        }
        when(commandDto.balanceType) {
            BalanceType.ALL_BALANCES -> balanceService.showAllBalances()
            BalanceType.USER_BALANCES -> {
                val user = User((commandDto as ShowUserBalancesDto).userId)
                balanceService.showUserBalances(user)
            }
        }
    }
}