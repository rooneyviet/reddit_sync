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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject
import jp.zuikou.system.redditprojectsample1.domain.model.PostEntity
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.data.model.SubRedditSortByDayEnum
import jp.zuikou.system.redditprojectsample1.presentation.data.model.SubRedditTypeEnum
import jp.zuikou.system.redditprojectsample1.presentation.ui.BaseFragment
import jp.zuikou.system.redditprojectsample1.presentation.ui.PostsPagedListAdapter
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.MainViewModel
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.PostsViewModel
import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton
import kotlinx.android.synthetic.main.fragment_sub_reddit.*
import kotlinx.android.synthetic.main.include_posts_list.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.Koin
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
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

    //private lateinit var mAdapter2: PostsPagedListAdapter


    private val postsAdapter: PostsPagedListAdapter by inject{ parametersOf({
        Timber.d("CLICKED")
        postsViewModel.retry()
    },
        {post: PostEntity, image: ImageView -> clickItem(post, image) }) }

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
        return inflater.inflate(R.layout.fragment_sub_reddit, container, false)
    }

    override fun refreshFragment() {
        observePostData(isReset = true)
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

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = postsAdapter

        observerNetworkState()

        observePostData()

        postsAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    recyclerView.scrollToPosition(0)
                }
            }
        })
    }

    private fun observerNetworkState(){
        postsViewModel.getNetworkState().observe(this,
            Observer<NetworkState> {
                if (postsAdapter.currentList.isNullOrEmpty() || swipeRefreshLayout.isRefreshing){
                    //setInitialLoadingState(it)
                }else {
                    postsAdapter.setNetworkState(it)
                }
            })
    }

    fun observePostData(subreddit: String? = shareMainViewModel.currentSubRedditRequestValue.subReddit,
                        type: SubRedditTypeEnum? = shareMainViewModel.currentSubRedditRequestValue.subType,
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



    /*override fun setInitialLoadingState(networkState: NetworkState?) {
        super.setInitialLoadingState(networkState)
        if (swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = networkState == NetworkState.LOADING
            progressBarLoading.visibility = View.GONE
        }
        postsAdapter.setNetworkState(NetworkState.LOADED)
    }*/

    private fun clickItem(post: PostEntity, image: ImageView) {

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
        // TODO: Update argument type and name
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
