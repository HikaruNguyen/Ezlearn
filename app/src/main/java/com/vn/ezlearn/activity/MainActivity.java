package com.vn.ezlearn.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vn.ezlearn.R;
import com.vn.ezlearn.adapter.NavigationAdapter;
import com.vn.ezlearn.databinding.ActivityMainBinding;
import com.vn.ezlearn.fragment.CategoryFragment;
import com.vn.ezlearn.fragment.HomeFragment;
import com.vn.ezlearn.model.ItemMenu;
import com.vn.ezlearn.model.ItemMenuChild;
import com.vn.ezlearn.utils.NavigationItemSelected;
import com.vn.ezlearn.widget.CRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationItemSelected {
    private Toolbar toolbar;
    private CRecyclerView rvNavigation;
    private ActivityMainBinding mainBinding;
    private List<ItemMenu> menuList;
    private NavigationAdapter navigationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initUI();
        bindData();

    }

    private void bindData() {
        changeFragment(new HomeFragment());
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvNavigation = mainBinding.rvNavigation;
        setSupportActionBar(toolbar);
        setupNavigation();

    }

    private void setupNavigation() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        menuList = new ArrayList<>();
        menuList.add(new ItemMenu(1, "TRANG CHỦ", ItemMenu.TYPE_NORMAL));
        ItemMenu itemMenu = new ItemMenu(2, "TRUNG HỌC CƠ ", ItemMenu.TYPE_PARENT, 1);
        itemMenu.menuChildList.add(new ItemMenuChild(5, "LỚP 6", ItemMenu.TYPE_CHILD, 2));
        itemMenu.menuChildList.add(new ItemMenuChild(6, "LỚP 7", ItemMenu.TYPE_CHILD, 2));
        itemMenu.menuChildList.add(new ItemMenuChild(7, "LỚP 8", ItemMenu.TYPE_CHILD, 2));
        itemMenu.menuChildList.add(new ItemMenuChild(8, "LỚP 9", ItemMenu.TYPE_CHILD, 2));
        itemMenu.menuChildList.add(new ItemMenuChild(9, "THI VÀO 10", ItemMenu.TYPE_CHILD, 2));
        menuList.add(itemMenu);

        itemMenu = new ItemMenu(3, "TRUNG HỌC PHỔ THÔNG", ItemMenu.TYPE_PARENT, 1);
        itemMenu.menuChildList.add(new ItemMenuChild(16, "LỚP 10", ItemMenu.TYPE_CHILD, 3));
        itemMenu.menuChildList.add(new ItemMenuChild(17, "LỚP 11", ItemMenu.TYPE_CHILD, 3));
        itemMenu.menuChildList.add(new ItemMenuChild(18, "LỚP 12", ItemMenu.TYPE_CHILD, 3));
        itemMenu.menuChildList.add(new ItemMenuChild(18, "THI THPTQG", ItemMenu.TYPE_CHILD, 3));
        menuList.add(itemMenu);

        itemMenu = new ItemMenu(4, "KỸ NĂNG", ItemMenu.TYPE_PARENT, 1);
        itemMenu.menuChildList.add(new ItemMenuChild(19, "Ngữ pháp", ItemMenu.TYPE_CHILD, 4));
        itemMenu.menuChildList.add(new ItemMenuChild(20, "Từ vựng", ItemMenu.TYPE_CHILD, 4));
        menuList.add(itemMenu);

        itemMenu = new ItemMenu(4, "IELTS", ItemMenu.TYPE_PARENT, 1);
        itemMenu.menuChildList.add(new ItemMenuChild(19, "3.5+", ItemMenu.TYPE_CHILD, 4));
        itemMenu.menuChildList.add(new ItemMenuChild(20, "5.5+", ItemMenu.TYPE_CHILD, 4));
        itemMenu.menuChildList.add(new ItemMenuChild(20, "6.5+", ItemMenu.TYPE_CHILD, 4));
        menuList.add(itemMenu);

        itemMenu = new ItemMenu(4, "TOEFL Junior", ItemMenu.TYPE_PARENT, 1);
        itemMenu.menuChildList.add(new ItemMenuChild(19, "650+", ItemMenu.TYPE_CHILD, 4));
        itemMenu.menuChildList.add(new ItemMenuChild(20, "750+", ItemMenu.TYPE_CHILD, 4));
        itemMenu.menuChildList.add(new ItemMenuChild(20, "850+", ItemMenu.TYPE_CHILD, 4));
        menuList.add(itemMenu);

        itemMenu = new ItemMenu(4, "TOEIC", ItemMenu.TYPE_PARENT, 1);
        itemMenu.menuChildList.add(new ItemMenuChild(19, "350+", ItemMenu.TYPE_CHILD, 4));
        itemMenu.menuChildList.add(new ItemMenuChild(20, "550+", ItemMenu.TYPE_CHILD, 4));
        itemMenu.menuChildList.add(new ItemMenuChild(20, "650+", ItemMenu.TYPE_CHILD, 4));
        menuList.add(itemMenu);

        navigationAdapter = new NavigationAdapter(this, menuList, this);
        mainBinding.rvNavigation.setAdapter(navigationAdapter);
        mainBinding.rvNavigation.setDivider();
    }

    @Override
    public void onBackPressed() {
        if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
    public void onSelected() {
        changeFragment(new CategoryFragment());
        if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        }

    }
}
