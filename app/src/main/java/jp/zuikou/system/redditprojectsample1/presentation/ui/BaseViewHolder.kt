package jp.zuikou.system.redditprojectsample1.presentation.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open fun onBind(payload: Any?) {
    }
}