package factory

import model.enums.SplitType
import strategy.*

object SplitStrategyFactory {

    fun getSplitStrategy(splitType: SplitType): SplitStrategy {
        return when(splitType) {
            SplitType.EQUAL -> EqualSplitStrategy()
            SplitType.EXACT -> ExactSplitStrategy()
            SplitType.PERCENT -> PercentSplitStrategy()
            SplitType.SHARE -> ShareSplitStrategy()
        }
    }
}