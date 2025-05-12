package model.split

import entity.User
import model.enums.SplitType

abstract class SplitDetail(
    open val user: User,
    val splitType: SplitType
)