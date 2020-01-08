package jp.zuikou.system.redditprojectsample1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import jp.zuikou.system.redditprojectsample1.domain.repository.LoginRepository
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.navigation.MainHostFragment
import jp.zuikou.system.redditprojectsample1.presentation.navigation_drawer.DrawerLayoutPagedListAdapter
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.MainViewModel
import jp.zuikou.system.redditprojectsample1.util.SharedPreferenceSingleton
import jp.zuikou.system.redditprojectsample1.util.ThemeHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.profile_sidebar_layout.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseAuthActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration

    private val mainViewModel: MainViewModel by viewModel()

    private val drawerPagedListAdapter: DrawerLayoutPagedListAdapter by inject{
        parametersOf({subreddit: String -> subClicked(subreddit) }) }


    private val loginRepository by inject<LoginRepository>()

    companion object {
        const val REQUEST_CODE_LOGIN = 754
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        setupNightMode()
        initAndObserveData()
        setupDrawerAction()
    }

    private fun setupDrawerAction(){
        loginLayout.setOnClickListener {
            /*val url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI, SCOPE)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivityForResult(intent, REQUEST_CODE_LOGIN)*/

            val host: MainHostFragment = supportFragmentManager
                .findFragmentById(R.id.myNavHostFragment) as MainHostFragment
            host.navController.navigate(R.id.loginWebViewFragment)
        }
    }


    private fun setupNavigation(){
        val drawerLayout : DrawerLayout? = findViewById(R.id.drawerLayout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        val host: MainHostFragment = supportFragmentManager
            .findFragmentById(R.id.myNavHostFragment) as MainHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // TODO STEP 9.5 - Create an AppBarConfiguration with the correct top-level destinations
        // You should also remove the old appBarConfiguration setup above
//        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
//        appBarConfiguration = AppBarConfiguration(
//                setOf(R.id.home_dest, R.id.deeplink_dest),
//                drawerLayout)
        // TODO END STEP 9.5

        setupActionBar(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, bundle: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    private fun initAndObserveData(){
        lst_menu_items_recyclerview.layoutManager = LinearLayoutManager(this)
        lst_menu_items_recyclerview.adapter = drawerPagedListAdapter

        mainViewModel.getSubcribersList().observe(this,
            Observer<PagedList<RSubSubcribersEntity>> {
                drawerPagedListAdapter.submitList(it)
            })

        mainViewModel.getNetworkStateList().observe(this,
            Observer<NetworkState> {

            })

        subClicked()
    }

    private fun subClicked(subreddit: String = "") {
        val host: MainHostFragment = supportFragmentManager
            .findFragmentById(R.id.myNavHostFragment) as MainHostFragment

        val childFragments = host.childFragmentManager.fragments
        childFragments.forEach { fragment ->
            if (fragment is SubRedditFragment && fragment.isVisible) {
                fragment.observePostData(subreddit)
            }
        }

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }


    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)
        // TODO STEP 9.2 - Have Navigation UI Handle the item selection - make sure to delete
        //  the old return statement above
//        // Have the NavigationUI look for an action or destination matching the menu
//        // item id and navigate there if found.
//        // Otherwise, bubble up to the parent.
        return item.onNavDestinationSelected(findNavController(R.id.myNavHostFragment))
                || super.onOptionsItemSelected(item)
        // TODO END STEP 9.2
    }

    @SuppressLint("CheckResult")
    private fun setupNightMode(){
        darkModeSwitchMaterial.isChecked = SharedPreferenceSingleton.getCurrentThemePref() == ThemeHelper.DARK_MODE
        darkModeSwitchMaterial.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                SharedPreferenceSingleton.setCurrentThemePref(ThemeHelper.DARK_MODE)
                ThemeHelper.applyTheme(ThemeHelper.DARK_MODE)
            } else {
                SharedPreferenceSingleton.setCurrentThemePref(ThemeHelper.LIGHT_MODE)
                ThemeHelper.applyTheme(ThemeHelper.LIGHT_MODE)
            }
        }
    }



}
