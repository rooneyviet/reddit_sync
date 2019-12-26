package jp.zuikou.system.redditprojectsample1.presentation.data.model

data class SubRedditRequest(var subReddit: String? = null ,
                            var subType: SubRedditTypeEnum? = SubRedditTypeEnum.HOT,
                            var subSortDay: SubRedditSortByDayEnum? = null) {

}

enum class SubRedditTypeEnum(val type: String){
    HOT("hot"),
    NEW("new"),
    RISING("rising"),
    TOP("top"),
    CONTROVERSIAL("controversial");

    companion object {
        fun from(s: String): SubRedditTypeEnum? = values().find { it.type == s }
    }
}

enum class SubRedditSortByDayEnum(day: String){
    HOUR("hour"),
    TODAY("day"),
    THIS_WEEK("week"),
    THIS_MONTH("month"),
    THIS_YEAR("year"),
    ALL_THE_TIME("all");

}