package jp.zuikou.system.redditprojectsample1.presentation.navigation_drawer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.zuikou.system.redditprojectsample1.R
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import jp.zuikou.system.redditprojectsample1.util.extension.loadImageCircle
import kotlinx.android.synthetic.main.rrsubscriberslayout.view.*

class DrawerLayoutPagedListAdapter(private val subClicked: (subreddit: String)-> Unit):
    PagedListAdapter<RSubSubcribersEntity,DrawerLayoutPagedListAdapter.SubcribersDrawerViewHolder>(SubscribersDiffCallback) {

     private var clickedPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubcribersDrawerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val mView = layoutInflater.inflate(R.layout.rrsubscriberslayout, parent, false)

        return SubcribersDrawerViewHolder(mView, parent.context)
    }

    override fun onBindViewHolder(
        holder: SubcribersDrawerViewHolder,
        position: Int
    ) {
        holder.bindData(getItem(position), position, subClicked)
    }

    fun setClickedPosition(position: Int){
        notifyDataSetChanged()
        clickedPosition = position
    }

    inner class SubcribersDrawerViewHolder(itemvView: View, private val mContext: Context): RecyclerView.ViewHolder(itemvView) {
        fun bindData (rSubSubcribersEntity: RSubSubcribersEntity?,
                      position: Int,
                      subClicked: (subreddit: String)-> Unit) {
            itemView.subcribersText.text = rSubSubcribersEntity?.displayName
            itemView.setOnClickListener {
                rSubSubcribersEntity?.displayNamePrefixed?.let { prefixDisplaySub ->
                    clickedPosition = position
                    notifyDataSetChanged()
                    subClicked.invoke(prefixDisplaySub)
                }
            }

            if(rSubSubcribersEntity?.iconImg !=null && rSubSubcribersEntity.iconImg.isNotEmpty()){
                itemView.subcribersImage.loadImageCircle(rSubSubcribersEntity.iconImg)
            } else {
                rSubSubcribersEntity?.communityIcon?.let {
                    itemView.subcribersImage.loadImageCircle(it)
                }
            }

            if(clickedPosition == position){
                itemView.rrsubscribersMainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.rrsubscriberTextSelectedBackgroundColor))
            } else {
                itemView.rrsubscribersMainLayout.setBackgroundColor(0)
            }
            /*rSubSubcribersEntity?.communityIcon?.let {

                itemView.subcribersImage.loadImageCircle(it)
            } ?: kotlin.run {
                rSubSubcribersEntity?.iconImg?.let {
                    itemView.subcribersImage.loadImageCircle(it)
                }
            }*/

        }

    }

    companion object {
        val SubscribersDiffCallback = object : DiffUtil.ItemCallback<RSubSubcribersEntity>() {
            override fun areItemsTheSame(oldItem: RSubSubcribersEntity, newItem: RSubSubcribersEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RSubSubcribersEntity, newItem: RSubSubcribersEntity): Boolean {
                return oldItem == newItem
            }
        }
    }



}