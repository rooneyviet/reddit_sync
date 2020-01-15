package jp.zuikou.system.redditprojectsample1.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.zuikou.system.redditprojectsample1.R
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import kotlinx.android.synthetic.main.list_item_network_state.view.*

class NetworkStateViewHolder(val view: View, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {

    init {
        itemView.buttonRetry.setOnClickListener { retryCallback() }
    }

    fun bindTo(networkState: NetworkState?) {
        //error message
        //itemView.textViewError.visibility = if (networkState?.failure != null) View.VISIBLE else View.GONE
        /*networkState?.failure?.let {
            itemView.textViewError.text = it.getMessage(itemView.context)
        }*/

        //itemView.textViewError.visibility = if (networkState?.failure != null) View.VISIBLE else View.GONE


        //itemView.textViewError.visibility = if (networkState != null && networkState == NetworkState.NO_INTERNET) View.VISIBLE else View.GONE

        //loading and retry
        itemView.buttonRetry.visibility = if (networkState == NetworkState.NO_INTERNET) View.VISIBLE else View.GONE
        itemView.progressBarLoading.visibility = if (networkState == NetworkState.LOADING) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_network_state, parent, false)
            return NetworkStateViewHolder(view, retryCallback)
        }
    }

}