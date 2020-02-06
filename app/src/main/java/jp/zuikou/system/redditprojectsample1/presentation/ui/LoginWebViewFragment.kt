package jp.zuikou.system.redditprojectsample1.presentation.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.zuikou.system.redditprojectsample1.R
import jp.zuikou.system.redditprojectsample1.config.AppConfig.AUTH_URL
import jp.zuikou.system.redditprojectsample1.config.AppConfig.CLIENT_ID
import jp.zuikou.system.redditprojectsample1.config.AppConfig.REDIRECT_URI
import jp.zuikou.system.redditprojectsample1.config.AppConfig.SCOPE
import jp.zuikou.system.redditprojectsample1.config.AppConfig.STATE
import jp.zuikou.system.redditprojectsample1.domain.repository.LoginRepository
import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton
import jp.zuikou.system.redditprojectsample1.util.rx.LoginLogoutChangeEvent
import jp.zuikou.system.redditprojectsample1.util.rx.RxBus
import kotlinx.android.synthetic.main.fragment_login_web_view.*
import org.koin.android.ext.android.inject
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LoginWebViewFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LoginWebViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginWebViewFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val loginRepository by inject<LoginRepository>()
    //private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI, SCOPE)
        loginWebview.loadUrl(url)
        loginWebview.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                //super.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val state = request?.url?.getQueryParameter("state")
                if (state == STATE) {
                    val code = request?.url?.getQueryParameter("code")
                    if(code!=null){
                        getAccessToken(code)
                        return true
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        onbackPressedListener()
    }
    override fun refreshFragment(isReset: Boolean) {

    }

    private fun onbackPressedListener(){
        val callback = object : OnBackPressedCallback(true /** true means that the callback is enabled */) {
            override fun handleOnBackPressed() {
                // Show your dialog and handle navigation
                findNavController().popBackStack(R.id.subRedditFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_login_web_view, container, false)
    }

    @SuppressLint("CheckResult")
    private fun getAccessToken(code: String?){
        loginRepository.getAccessToken(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("KLSFHLHFLF ${it.accessToken}")
                loginViewModel.authenticate(true)
                RxBus.send(LoginLogoutChangeEvent(true))
                SharedPreferenceSingleton.setAccessTokenEntity(it)
                findNavController().popBackStack(R.id.subRedditFragment, false)
            },{
                loginViewModel.authenticate(false)
            })
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }*/

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }*/

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginWebViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginWebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
