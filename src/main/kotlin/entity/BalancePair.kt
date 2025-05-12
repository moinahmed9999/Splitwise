package entity

data class BalancePair(
    val owedBy: User,
    val owedTo: User,
)
