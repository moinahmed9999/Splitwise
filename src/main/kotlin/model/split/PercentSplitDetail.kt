package model.split

import entity.User
import model.enums.SplitType

class PercentSplitDetail(
    override val user: User,
    val percent: Double,
) : SplitDetail(user, SplitType.PERCENT)