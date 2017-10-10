package com.vn.ezlearn.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.vn.ezlearn.R;
import com.vn.ezlearn.adapter.NavigationAdapter;
import com.vn.ezlearn.config.AppConfig;
import com.vn.ezlearn.config.AppConstant;
import com.vn.ezlearn.config.EzlearnService;
import com.vn.ezlearn.databinding.ActivityMainBinding;
import com.vn.ezlearn.fragment.CategoryMainFragment;
import com.vn.ezlearn.fragment.HomeFragment;
import com.vn.ezlearn.interfaces.NavigationItemSelected;
import com.vn.ezlearn.modelresult.BaseResult;
import com.vn.ezlearn.models.Category;
import com.vn.ezlearn.viewmodel.MainViewModel;
import com.vn.ezlearn.widgets.CRecyclerView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NavigationItemSelected {
    private Toolbar toolbar;
    private CRecyclerView rvNavigation;
    private ActivityMainBinding mainBinding;
    private MainViewModel mainViewModel;
    private List<Category> menuList;
    private NavigationAdapter navigationAdapter;
    private String currentId = AppConstant.HOME_ID;

    private EzlearnService apiService;
    private Subscription mSubscription;

    private BaseResult baseResultLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        mainBinding.setMainViewModel(mainViewModel);
        initUI();
        bindData();
        event();
    }

    private void event() {
        mainBinding.rlHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mainBinding.drawerLayout.closeDrawer(GravityCompat.START);
                }
                Intent intent;
                if (!AppConfig.getInstance(MainActivity.this).getToken().isEmpty()) {
                    intent = new Intent(MainActivity.this, UserProfile.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, LoginActivity.LOGIN_REQUEST);
                }

            }
        });
        mainBinding.lnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        apiService = MyApplication.with(this).getEzlearnService();
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
        mSubscription = apiService.getLogout()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {
                        if (baseResultLogout.success) {
                            if (baseResultLogout.data != null && baseResultLogout.data.message != null
                                    && !baseResultLogout.data.message.isEmpty()) {
                                Toast.makeText(MainActivity.this, baseResultLogout.data.message,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.logout_success),
                                        Toast.LENGTH_SHORT).show();
                            }
                            AppConfig.getInstance(MainActivity.this).clearData();
                            mainViewModel.updateProfile();
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.error_connect),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (baseResult != null) {
                            baseResultLogout = baseResult;
                        }
                    }
                });
    }

    private void bindData() {
        changeFragment(new HomeFragment());
        toolbar.setTitle(getString(R.string.title_home));
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvNavigation = mainBinding.rvNavigation;
        setSupportActionBar(toolbar);
        setupNavigation();
    }

    public TabLayout getTablayout() {
        return mainBinding.tabs;
    }

    private void setupNavigation() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mainBinding.drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mainBinding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

//        fakeData();
        menuList = new ArrayList<>();
        menuList.add(new Category(AppConstant.HOME_ID, getString(R.string.title_home), Category.TYPE_NORMAL));
        menuList.add(new Category(AppConstant.FREE_ID, getString(R.string.tryExam), Category.TYPE_NORMAL));
        menuList.add(new Category(AppConstant.OFFLINE_ID, getString(R.string.offlineExam), Category.TYPE_NORMAL));

        menuList.add(new Category(Category.TYPE_LINE));
        if (MyApplication.with(this).getCategoryResult() != null
                && MyApplication.with(this).getCategoryResult().data != null
                && MyApplication.with(this).getCategoryResult().data.size() > 0) {
            menuList.addAll(MyApplication.with(this).getCategoryResult().data);
        }
        menuList.add(new Category(Category.TYPE_LINE));
        menuList.add(new Category(AppConstant.OFFLINE_ID, getString(R.string.contact), Category.TYPE_NORMAL));
        navigationAdapter = new NavigationAdapter(this, menuList, this);
        mainBinding.rvNavigation.setAdapter(navigationAdapter);
//        mainBinding.rvNavigation.setDivider();
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!currentId.equals(AppConstant.HOME_ID)) {
                changeFragment(new HomeFragment());
                currentId = AppConstant.HOME_ID;
                mainViewModel.setVisiableTabBar(AppConstant.HOME_ID);
                toolbar.setTitle(getString(R.string.title_home));
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, getString(R.string.clickback), Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeFragment(Fragment targetFragment) {
        clearBackStack(getSupportFragmentManager());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void clearBackStack(FragmentManager manager) {
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager
                    .getBackStackEntryAt(0);
            manager.popBackStack(first.getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onSelected(String name, String id, List<Category> categoryList) {
        mainViewModel.setVisiableTabBar(id);
        if (!id.equals(currentId)) {
            if (!id.equals(AppConstant.HOME_ID)) {
                if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mainBinding.drawerLayout.closeDrawer(GravityCompat.START);
                    toolbar.setTitle(name);
                }
                CategoryMainFragment categoryMainFragment = new CategoryMainFragment();
                categoryMainFragment.setCategoryList(categoryList);
                changeFragment(categoryMainFragment);
            } else {

                if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mainBinding.drawerLayout.closeDrawer(GravityCompat.START);
                    toolbar.setTitle(name);
                }
                changeFragment(new HomeFragment());
            }

        }
        currentId = id;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == LoginActivity.LOGIN_REQUEST) {
                mainViewModel.updateProfile();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = null;
    }
}
