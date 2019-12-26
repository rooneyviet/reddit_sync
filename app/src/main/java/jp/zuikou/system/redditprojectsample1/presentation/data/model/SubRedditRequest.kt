package jp.zuikou.system.redditprojectsample1.presentation.data.model

data class SubRedditRequest(val subReddit: String? = null ,
                            val subType: SubRedditType = SubRedditType.HOT) {

}

enum class SubRedditType(type: String){
    HOT("hot"),
    NEW("new"),
    RISING("rising"),
    TOP("top"),
    CONTROVERSIAL("controversial");
}

enum class SubRedditSortByDay(day: String){
    HOUR("hour"),
    TODAY("day"),
    THIS_WEEK("week"),
    THIS_MONTH("month"),
    THIS_YEAR("year"),
    ALL_THE_TIME("all");

}