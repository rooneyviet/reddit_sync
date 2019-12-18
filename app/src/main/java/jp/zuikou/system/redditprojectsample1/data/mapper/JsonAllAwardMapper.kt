package jp.zuikou.system.redditprojectsample1.data.mapper

import jp.zuikou.system.redditprojectsample1.data.model.response.AllAwarding
import jp.zuikou.system.redditprojectsample1.domain.model.AllAwardingEntity

object JsonAllAwardMapper : Mapper<AllAwarding, AllAwardingEntity>() {
    override fun transformFrom(source: AllAwardingEntity): AllAwarding {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transformTo(source: AllAwarding): AllAwardingEntity =
        AllAwardingEntity(
            awardType = source.awardType,
            coinPrice = source.coinPrice,
            coinReward = source.coinReward,
            count = source.count,
            daysOfDripExtension = source.daysOfDripExtension,
            daysOfPremium = source.daysOfPremium,
            description = source.description,
            endDate = source.endDate,
            iconHeight = source.iconHeight,
            iconUrl = source.iconUrl,
            iconWidth = source.iconWidth,
            id = source.id,
            isEnabled = source.isEnabled,
            name = source.name,
            resizedIcons = listOf(),
            startDate = source.startDate,
            subredditCoinReward = source.subredditCoinReward,
            subredditId = source.subredditId
        )
}