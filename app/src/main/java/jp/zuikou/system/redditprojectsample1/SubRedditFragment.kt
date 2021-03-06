package jp.zuikou.system.redditprojectsample1

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navOptions
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.master.exoplayer.MasterExoPlayerHelper
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.data.model.PostVoteRequest
import jp.zuikou.system.redditprojectsample1.presentation.data.model.SubRedditRequest
import jp.zuikou.system.redditprojectsample1.presentation.data.model.SubRedditSortByDayEnum
import jp.zuikou.system.redditprojectsample1.presentation.data.model.SubRedditTypeEnum
import jp.zuikou.system.redditprojectsample1.presentation.ui.BaseFragment
import jp.zuikou.system.redditprojectsample1.presentation.ui.ImagePreviewDialogFragment
import jp.zuikou.system.redditprojectsample1.presentation.ui.PostsPagedListAdapter
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.MainViewModel
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.PostsViewModel
import jp.zuikou.system.redditprojectsample1.util.CustomLinearLayoutManager
import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton
import kotlinx.android.synthetic.main.fragment_sub_reddit.*
import kotlinx.android.synthetic.main.include_posts_list.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SubRedditFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SubRedditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubRedditFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private val postsViewModel: PostsViewModel by viewModel()

    private lateinit var shareMainViewModel: MainViewModel

    val currentSubRedditRequestValue : SubRedditRequest
        get() = shareMainViewModel.currentSubRedditRequestLiveData.value?: SubRedditRequest("")

    //private lateinit var selector: PressablePlayerSelector

    //private lateinit var mAdapter2: PostsPagedListAdapter

    private lateinit var customLinearLayoutManager: CustomLinearLayoutManager

    private val dialogFragment = ImagePreviewDialogFragment()


    private val postsAdapter: PostsPagedListAdapter by inject{ parametersOf({
        postsViewModel.retry()

        //retryCallback: () -> Unit,
        //                  clickItem: (post: PostEntity, image: ImageView) -> Unit,
        //                  upvoteDownvote: (postVoteRequest: PostVoteRequest) -> Unit,
        //                    imageLongPress: (imageUrl: String) -> Unit,
        //                    imageClickPress: (imageUrl: String) -> Unit
    },
        {post: PostEntity, image: ImageView -> clickItem(post, image) },
        {postVoteRequest: PostVoteRequest -> upvoteDowvoteItem(postVoteRequest)},
        {imageUrl: String, isLongPress: Boolean, sharedImageView: ImageView -> longPressImage(imageUrl, isLongPress, sharedImageView)},
        {imageUrl: String, sharedImageView: ImageView -> singlePressImage(imageUrl, sharedImageView)})}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            shareMainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }
        getKoin().setProperty(PROPERTY_PAGED_LIST, getPagedListConfig())
        //GlobalContext.get().koin.setProperty(PROPERTY_PAGED_LIST, getPagedListConfig())
        // Inflate the layout for this fragment
        //SharedPreferenceSingleton.setAccessTokenEntityNull()
        Timber.d("JBFDJFBDF "+SharedPreferenceSingleton.getAccessTokenEntity()?.accessToken)
        return inflater.inflate(R.layout.fragment_sub_reddit, container, false)
    }

    override fun refreshFragment(isReset: Boolean) {
        observePostData(isReset = isReset)
        observerNetworkState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO STEP 6 - Set NavOptions
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        initSwipeToRefresh()

        val masterExoPlayerHelper =this.activity?.let {
            MasterExoPlayerHelper(mContext = it, id = R.id.masterExoPlayer, useController = false, autoPlay = true)
        }
        masterExoPlayerHelper?.makeLifeCycleAware(this)

        customLinearLayoutManager = CustomLinearLayoutManager(this.context)

        recyclerView.layoutManager = customLinearLayoutManager
        recyclerView.adapter = postsAdapter

        masterExoPlayerHelper?.attachToRecyclerView(recyclerView)
        //(recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        //observerNetworkState()

        //observePostData()

        refreshFragment(false)
        postsAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    recyclerView.scrollToPosition(0)
                }
            }
        })

        observerLikeUpvoteDownvote()
    }

    private fun observerLikeUpvoteDownvote(){
        postsViewModel.postVoteLiveData.observe(this, Observer {postVoteRequest->
            postVoteRequest?.let {
                postsAdapter.currentList?.get(postVoteRequest.clickedPosition)?.likes = postVoteRequest.isUpvote
                //postsAdapter.submitList(postsAdapter.currentList)
                postVoteRequest.isUpvote?.let {
                    if(it){
                        postsAdapter.currentList?.get(postVoteRequest.clickedPosition)?.score = postVoteRequest.postItem.score?.plus(1)
                    } else {
                        postsAdapter.currentList?.get(postVoteRequest.clickedPosition)?.score = postVoteRequest.postItem.score?.minus(1)
                    }
                }
                postsAdapter.notifyItemChanged(postVoteRequest.clickedPosition, postsAdapter.currentList?.get(postVoteRequest.clickedPosition))
                postsViewModel.postVoteLiveData.postValue(null)
            }
        })
    }

    private fun observerNetworkState(){
        postsViewModel.getNetworkState().observe(this,
            Observer<NetworkState> {
                if (postsAdapter.currentList.isNullOrEmpty() || swipeRefreshLayout.isRefreshing){
                    setInitialLoadingState(it)
                }else {
                    postsAdapter.setNetworkState(it)
                }
            })
    }

    fun observePostData(subreddit: String? = currentSubRedditRequestValue.subReddit,
                        type: SubRedditTypeEnum? = currentSubRedditRequestValue.subType,
                        subRedditSortByDayEnum: SubRedditSortByDayEnum? = null,
                        isReset: Boolean = false) {
        shareMainViewModel.saveCurrentSubCurrentRequest(subreddit, type, subRedditSortByDayEnum)
        postsViewModel.getPosts(subreddit, type?.type, isReset).observe(this,
            Observer<PagedList<PostEntity>> {
                swipeRefreshLayout.isRefreshing = false
                postsAdapter.submitList(it)
            })
    }

    private fun initSwipeToRefresh() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        swipeRefreshLayout.setOnRefreshListener {
            observePostData()
        }
    }

    private fun getPagedListConfig() =
        PagedList.Config.Builder()
            .setPageSize(15)
            .setEnablePlaceholders(false)
            .build()

    override fun setInitialLoadingState(networkState: NetworkState?) {
        super.setInitialLoadingState(networkState)
        if (swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = networkState == NetworkState.LOADING
            progressBarLoading.visibility = View.GONE
        }
        //postsAdapter.setNetworkState(NetworkState.LOADED)
    }

    private fun clickItem(post: PostEntity, image: ImageView) {

    }

    private fun longPressImage(imageUrl: String, isLongPressOn: Boolean, sharedImageView: ImageView) {
        val ft = childFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("imageUrl", imageUrl);
        dialogFragment.arguments = bundle

        val prev = childFragmentManager.findFragmentByTag("imagedialog")
        if(isLongPressOn){
            customLinearLayoutManager.setScrollEnabled(false)
            if (prev != null) {
                ft.remove(prev)
            }
            //ft.addToBackStack(null)
            if(!dialogFragment.isAdded) dialogFragment.show(ft, "imagedialog")
        } else {
            if (prev != null) {
                ft.remove(prev).commit()
            }
            customLinearLayoutManager.setScrollEnabled(true)
        }
    }

    private fun singlePressImage(imageUrl: String, sharedImageView: ImageView) {
        val ft = childFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("imageUrl", imageUrl);
        dialogFragment.arguments = bundle
        val prev = childFragmentManager.findFragmentByTag("imagedialog")
        if (prev != null) {
            ft.remove(prev)
        }
        //ft.addToBackStack(null)
        if(!dialogFragment.isAdded) dialogFragment.show(ft, "imagedialog")
    }


    private fun upvoteDowvoteItem(postVoteRequest: PostVoteRequest) {
        postsViewModel.votePost(postVoteRequest)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }*/

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        //const val PROPERTY_PAGED_LIST = "pagedListDetail"
        const val PROPERTY_PAGED_LIST = "pagedList"
        private const val DURATION_TRANSITION = 380L
        private const val DELAY_TRANSITION = 200L
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SubRedditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String? = null, param2: String? = null) =
            SubRedditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
