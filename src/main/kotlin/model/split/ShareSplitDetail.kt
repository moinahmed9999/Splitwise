package model.split

import entity.User
import model.enums.SplitType

class ShareSplitDetail(
    override val user: User,
    val count: Int,
) : SplitDetail(user, SplitType.SHARE)