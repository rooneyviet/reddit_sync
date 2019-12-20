package jp.zuikou.system.redditprojectsample1.presentation.navigation_drawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.zuikou.system.redditprojectsample1.R
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import kotlinx.android.synthetic.main.rrsubscriberslayout.view.*

class DrawerLayoutPagedListAdapter(private val subClicked: (subreddit: String)-> Unit):
    PagedListAdapter<RSubSubcribersEntity,DrawerLayoutPagedListAdapter.SubcribersDrawerViewHolder>(SubscribersDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubcribersDrawerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val mView = layoutInflater.inflate(R.layout.rrsubscriberslayout, parent, false)

        return SubcribersDrawerViewHolder(mView)
    }

    override fun onBindViewHolder(
        holder: SubcribersDrawerViewHolder,
        position: Int
    ) {
        holder.bindData(getItem(position), subClicked)
    }

    class SubcribersDrawerViewHolder(itemvView: View): RecyclerView.ViewHolder(itemvView) {
        fun bindData (rSubSubcribersEntity: RSubSubcribersEntity?,
                      subClicked: (subreddit: String)-> Unit) {
            itemView.subcribersText.text = rSubSubcribersEntity?.displayName
            itemView.setOnClickListener {
                rSubSubcribersEntity?.displayNamePrefixed?.let { prefixDisplaySub ->
                    subClicked.invoke(prefixDisplaySub)
                }
            }
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