package model.split

import entity.Amount
import entity.User
import model.enums.SplitType

class ExactSplitDetail(
    override val user: User,
    val amount: Amount,
) : SplitDetail(user, SplitType.EXACT)