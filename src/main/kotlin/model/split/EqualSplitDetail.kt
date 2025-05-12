package model.split

import entity.User
import model.enums.SplitType

class EqualSplitDetail(
    override val user: User,
) : SplitDetail(user, SplitType.EQUAL)