package jp.zuikou.system.redditprojectsample1.data.mapper

import jp.zuikou.system.redditprojectsample1.data.model.response.subcribers.JsonSubcribersResponse
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity

object JsonRSubSubcribersMapper : Mapper<JsonSubcribersResponse, RSubSubcribersEntity>() {
    override fun transformFrom(source: RSubSubcribersEntity): JsonSubcribersResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transformTo(source: JsonSubcribersResponse): RSubSubcribersEntity {
        return RSubSubcribersEntity(
            accountsActive = source.accountsActive,
            accountsActiveIsFuzzed = source.accountsActiveIsFuzzed,
            activeUserCount = source.activeUserCount,
            advertiserCategory = source.advertiserCategory,
            allOriginalContent = source.allOriginalContent,
            allowDiscovery = source.allowDiscovery,
            allowImages = source.allowImages,
            allowVideogifs = source.allowVideogifs,
            allowVideos = source.allowVideos,
            bannerBackgroundColor = source.bannerBackgroundColor,
            bannerBackgroundImage = source.bannerBackgroundImage,
            bannerImg = source.bannerImg,
            bannerSize = source.bannerSize,
            canAssignLinkFlair = source.canAssignLinkFlair,
            canAssignUserFlair = source.canAssignUserFlair,
            collapseDeletedComments = source.collapseDeletedComments,
            commentScoreHideMins = source.commentScoreHideMins,
            communityIcon = source.communityIcon,
            created = source.created,
            createdUtc = source.createdUtc,
            description = source.description,
            descriptionHtml = source.descriptionHtml,
            disableContributorRequests = source.disableContributorRequests,
            displayName = source.displayName,
            displayNamePrefixed = source.displayNamePrefixed,
            emojisCustomSize = source.emojisCustomSize,
            emojisEnabled = source.emojisEnabled,
            freeFormReports = source.freeFormReports,
            hasMenuWidget = source.hasMenuWidget,
            headerImg = source.headerImg,
            headerSize = source.headerSize,
            headerTitle = source.headerTitle,
            hideAds = source.hideAds,
            iconImg = source.iconImg,
            iconSize = source.iconSize,
            id = source.id,
            isCrosspostableSubreddit = source.isCrosspostableSubreddit,
            isEnrolledInNewModmail = source.isEnrolledInNewModmail,
            keyColor = source.keyColor,
            lang = source.lang,
            linkFlairEnabled = source.linkFlairEnabled,
            linkFlairPosition = source.linkFlairPosition,
            mobileBannerImage = source.mobileBannerImage,
            name = source.name,
            notificationLevel = source.notificationLevel,
            originalContentTagEnabled = source.originalContentTagEnabled,
            over18 = source.over18,
            primaryColor = source.primaryColor,
            publicDescription = source.publicDescription,
            publicDescriptionHtml = source.publicDescriptionHtml,
            publicTraffic = source.publicTraffic,
            quarantine = source.quarantine,
            restrictCommenting = source.restrictCommenting,
            restrictPosting = source.restrictPosting,
            showMedia = source.showMedia,
            showMediaPreview = source.showMediaPreview,
            spoilersEnabled = source.spoilersEnabled,
            submissionType = source.submissionType,
            submitLinkLabel = source.submitLinkLabel,
            submitText = source.submitText,
            submitTextHtml = source.submitTextHtml,
            submitTextLabel = source.submitTextLabel,
            subredditType = source.subredditType,
            subscribers = source.subscribers,
            suggestedCommentSort = source.suggestedCommentSort,
            title = source.title,
            url = source.url,
            userCanFlairInSr = source.userCanFlairInSr,
            userFlairBackgroundColor = source.userFlairBackgroundColor,
            userFlairCssClass = source.userFlairCssClass,
            userFlairEnabledInSr = source.userFlairEnabledInSr,
            userFlairPosition = source.userFlairPosition,
            userFlairRichtext = source.userFlairRichtext,
            userFlairTemplateId = source.userFlairTemplateId,
            userFlairText = source.userFlairText,
            userFlairTextColor = source.userFlairTextColor,
            userFlairType = source.userFlairType,
            userHasFavorited = source.userHasFavorited,
            userIsBanned = source.userIsBanned,
            userIsContributor = source.userIsContributor,
            userIsModerator = source.userIsModerator,
            userIsMuted = source.userIsMuted,
            userIsSubscriber = source.userIsSubscriber,
            userSrFlairEnabled = source.userSrFlairEnabled,
            userSrThemeEnabled = source.userSrThemeEnabled,
            videostreamLinksCount = source.videostreamLinksCount,
            whitelistStatus = source.whitelistStatus,
            wikiEnabled = source.wikiEnabled,
            wls = source.wls
        )
    }
}