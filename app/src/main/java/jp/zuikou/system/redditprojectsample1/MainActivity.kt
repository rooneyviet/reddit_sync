package jp.zuikou.system.redditprojectsample1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import jp.zuikou.system.redditprojectsample1.di.RetrofitObject
import jp.zuikou.system.redditprojectsample1.domain.model.RSubSubcribersEntity
import jp.zuikou.system.redditprojectsample1.presentation.data.datasource.NetworkState
import jp.zuikou.system.redditprojectsample1.presentation.navigation_drawer.DrawerLayoutPagedListAdapter
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseAuthActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration

    private val mainViewModel: MainViewModel by viewModel()

    private val drawerPagedListAdapter: DrawerLayoutPagedListAdapter by inject{
        parametersOf({subreddit: String -> subClicked(subreddit) }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getKoin().setProperty(RetrofitObject.RETROFIT_CHOOSE_NAMESPACE, RetrofitObject.RETROFIT_LOGGED_NAMESPACE)
        setContentView(R.layout.activity_main)

        val drawerLayout : DrawerLayout? = findViewById(R.id.drawerLayout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        //val drawerLayout : DrawerLayout? = findViewById(R.id.drawerLayout)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.myNavHostFragment) as NavHostFragment? ?: return

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

        //mainViewModel.refreshGetSubcribersList()



        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, bundle: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        initAndObserveData()

        //Glide.with(this).load("http://goo.gl/gEgYUd").into(navHeaderImage);
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

        //initSwipeToRefresh()

        //postsViewModel.refresh()
    }

    private fun subClicked(subreddit: String) {
        Toast.makeText(applicationContext, subreddit, Toast.LENGTH_LONG).show()
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        // TODO STEP 9.6 - Have NavigationUI handle what your ActionBar displays
//        // This allows NavigationUI to decide what label to show in the action bar
//        // By using appBarConfig, it will also determine whether to
//        // show the up arrow or drawer menu icon
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


}
