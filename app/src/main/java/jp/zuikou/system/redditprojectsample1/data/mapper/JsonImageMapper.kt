package jp.zuikou.system.redditprojectsample1.data.mapper

import jp.zuikou.system.redditprojectsample1.data.model.response.JsonImage
import jp.zuikou.system.redditprojectsample1.domain.model.ImageEntity

object JsonImageMapper: Mapper<JsonImage, ImageEntity>() {

    override fun transformFrom(source: ImageEntity): JsonImage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transformTo(source: JsonImage): ImageEntity = ImageEntity(url = source.url ?: "", width = source.width ?: 0,
        height = source.width ?: 0)

}