package com.vn.ezlearn.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.vn.ezlearn.R
import com.vn.ezlearn.adapter.NavigationAdapter
import com.vn.ezlearn.config.AppConfig
import com.vn.ezlearn.config.AppConstant
import com.vn.ezlearn.config.EzlearnService
import com.vn.ezlearn.databinding.ActivityMainBinding
import com.vn.ezlearn.fragment.CategoryFragment
import com.vn.ezlearn.fragment.CategoryMainFragment
import com.vn.ezlearn.fragment.HomeFragment
import com.vn.ezlearn.interfaces.NavigationItemSelected
import com.vn.ezlearn.modelresult.BaseResult
import com.vn.ezlearn.models.Category
import com.vn.ezlearn.models.ContentByCategory
import com.vn.ezlearn.viewmodel.MainViewModel
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity(), NavigationItemSelected {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var menuList: ArrayList<Category>? = null
    private var navigationAdapter: NavigationAdapter? = null
    private var currentId = AppConstant.HOME_ID

    private var apiService: EzlearnService? = null
    private var mSubscription: Subscription? = null

    private var baseResultLogout: BaseResult? = null

    val tabLayout: TabLayout
        get() = mainBinding.tabs


    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = MainViewModel(this)
        mainBinding.mainViewModel = mainViewModel
        initUI()
        bindData()
        event()
    }

    private fun event() {
        mainBinding.rlHeader.setOnClickListener {
            if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            val intent: Intent
            if (!AppConfig.getInstance(this@MainActivity).token.isEmpty()) {
                intent = Intent(this@MainActivity, UserProfile::class.java)
                startActivity(intent)
            } else {
                intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivityForResult(intent, LoginActivity.LOGIN_REQUEST)
            }
        }
        mainBinding.lnBottom.setOnClickListener { logout() }
    }

    private fun logout() {
        apiService = MyApplication.with(this).getEzlearnService()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed)
            mSubscription!!.unsubscribe()
        mSubscription = apiService!!.logout
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<BaseResult>() {
                    override fun onCompleted() {
                        if (baseResultLogout!!.success) {
                            if (baseResultLogout!!.data != null
                                    && !baseResultLogout!!.data!!.message.isEmpty()) {
                                Toast.makeText(this@MainActivity,
                                        baseResultLogout!!.data!!.message,
                                        Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@MainActivity, getString(R.string.logout_success),
                                        Toast.LENGTH_SHORT).show()
                            }
                            AppConfig.getInstance(this@MainActivity).clearData()
                            mainViewModel.updateProfile()
                        } else {
                            Toast.makeText(this@MainActivity, getString(R.string.error_connect),
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(baseResult: BaseResult?) {
                        if (baseResult != null) {
                            baseResultLogout = baseResult
                        }
                    }
                })
    }

    private fun bindData() {
        changeFragment(HomeFragment())
        mainBinding.toolbar!!.title = getString(R.string.nav_home)
    }

    private fun initUI() {
        setSupportActionBar(mainBinding.toolbar!!)
        setupNavigation()
    }

    private fun setupNavigation() {
        val toggle = ActionBarDrawerToggle(
                this, mainBinding.drawerLayout, mainBinding.toolbar!!, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        mainBinding.drawerLayout.setDrawerListener(toggle)
        toggle.syncState()

        //        fakeData();
        menuList = ArrayList()
        menuList!!.add(Category(AppConstant.HOME_ID.toString(), getString(R.string.nav_home),
                Category.TYPE_NORMAL))
        menuList!!.add(Category(AppConstant.FREE_ID.toString(), getString(R.string.nav_sample_exam),
                Category.TYPE_NORMAL))
        menuList!!.add(Category(AppConstant.OFFLINE_ID.toString(), getString(R.string.nav_offline_exam),
                Category.TYPE_NORMAL))

        menuList!!.add(Category(Category.TYPE_LINE))
        if (MyApplication.with(this).categoryResult != null
                && MyApplication.with(this).categoryResult!!.data != null
                && MyApplication.with(this).categoryResult!!.data!!.isNotEmpty()) {
            menuList!!.addAll(MyApplication.with(this).categoryResult!!.data!!)
        }
        menuList!!.add(Category(Category.TYPE_LINE))
        menuList!!.add(Category(AppConstant.OFFLINE_ID.toString(), getString(R.string.nav_contact),
                Category.TYPE_NORMAL))
        navigationAdapter = NavigationAdapter(this, menuList!!, this)
        mainBinding.rvNavigation.adapter = navigationAdapter
        //        mainBinding.rvNavigation.setDivider();
    }

    override fun onBackPressed() {
        if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (currentId != AppConstant.HOME_ID) {
                changeFragment(HomeFragment())
                currentId = AppConstant.HOME_ID
                mainViewModel.setVisiableTabBar(AppConstant.HOME_ID.toString())
                mainBinding.toolbar!!.title = getString(R.string.nav_home)
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                    return
                }

                this.doubleBackToExitPressedOnce = true
                Toast.makeText(this, getString(R.string.click_back_again), Toast.LENGTH_SHORT).show()

                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    private fun changeFragment(targetFragment: Fragment) {
        clearBackStack(supportFragmentManager)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }

    private fun clearBackStack(manager: FragmentManager) {
        if (manager.backStackEntryCount > 0) {
            val first = manager
                    .getBackStackEntryAt(0)
            manager.popBackStack(first.id,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onSelected(name: String, id: String, categoryList: List<Category>?) {
        mainViewModel.setVisiableTabBar(id)
        if (id.toInt() != currentId) {
            if (id.toInt() != AppConstant.HOME_ID) {
                if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    mainBinding.toolbar!!.title = name
                }
                if (id.toInt() > 0) {
                    val categoryMainFragment = CategoryMainFragment()
                    if (categoryList != null) {
                        categoryMainFragment.setCategoryList(categoryList)
                    }
                    changeFragment(categoryMainFragment)
                } else {
                    changeFragment(CategoryFragment.newInstance(id.toInt(),
                            ContentByCategory.CONTENT_TYPE_EXAM))
                }

            } else {

                if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    mainBinding.toolbar!!.title = name
                }
                changeFragment(HomeFragment())
            }

        }
        currentId = id.toInt()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LoginActivity.LOGIN_REQUEST) {
                mainViewModel.updateProfile()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) mSubscription!!.unsubscribe()
        mSubscription = null
    }
}
