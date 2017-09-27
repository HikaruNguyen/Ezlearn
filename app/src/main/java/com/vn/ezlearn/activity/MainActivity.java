package com.vn.ezlearn.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vn.ezlearn.R;
import com.vn.ezlearn.adapter.NavigationAdapter;
import com.vn.ezlearn.databinding.ActivityMainBinding;
import com.vn.ezlearn.fragment.CategoryMainFragment;
import com.vn.ezlearn.fragment.HomeFragment;
import com.vn.ezlearn.interfaces.NavigationItemSelected;
import com.vn.ezlearn.models.ItemMenu;
import com.vn.ezlearn.models.ItemMenuChild;
import com.vn.ezlearn.widgets.CRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationItemSelected {
    private Toolbar toolbar;
    private CRecyclerView rvNavigation;
    private ActivityMainBinding mainBinding;
    private List<ItemMenu> menuList;
    private NavigationAdapter navigationAdapter;
    private String currentId = "-1";

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mainBinding.drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mainBinding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

//        fakeData();
        menuList = new ArrayList<>();
        menuList.add(new ItemMenu("-1", "TRANG CHỦ", ItemMenu.TYPE_NORMAL));
        if (MyApplication.with(this).getCategoryResult() != null
                && MyApplication.with(this).getCategoryResult().data != null
                && MyApplication.with(this).getCategoryResult().data.size() > 0) {
            for (ItemMenu itemMenu : MyApplication.with(this).getCategoryResult().data) {
                if (itemMenu.children != null && itemMenu.children.size() > 0) {
                    itemMenu.typeMenu = ItemMenu.TYPE_PARENT;
                } else {
                    itemMenu.typeMenu = ItemMenu.TYPE_NORMAL;
                }
                menuList.add(itemMenu);
            }
        } else {
            fakeData();
        }
        navigationAdapter = new NavigationAdapter(this, menuList, this);
        mainBinding.rvNavigation.setAdapter(navigationAdapter);
        mainBinding.rvNavigation.setDivider();
    }

    private void fakeData() {
        ItemMenu itemMenu = new ItemMenu("2", "TIẾNG ANH PHỔ THÔNG", ItemMenu.TYPE_PARENT, "1");
        itemMenu.children.add(new ItemMenuChild("2", "LỚP 6", ItemMenu.TYPE_CHILD, "2"));
        itemMenu.children.add(new ItemMenuChild("2", "LỚP 7", ItemMenu.TYPE_CHILD, "2"));
        itemMenu.children.add(new ItemMenuChild("2", "LỚP 8", ItemMenu.TYPE_CHILD, "2"));
        itemMenu.children.add(new ItemMenuChild("2", "LỚP 9", ItemMenu.TYPE_CHILD, "2"));
        itemMenu.children.add(new ItemMenuChild("2", "LỚP 10", ItemMenu.TYPE_CHILD, "2"));
        itemMenu.children.add(new ItemMenuChild("2", "LỚP 11", ItemMenu.TYPE_CHILD, "2"));
        itemMenu.children.add(new ItemMenuChild("2", "LỚP 12", ItemMenu.TYPE_CHILD, "2"));
        menuList.add(itemMenu);

        itemMenu = new ItemMenu("3", "THI CHUYÊN 10", ItemMenu.TYPE_PARENT, "1");
        itemMenu.children.add(new ItemMenuChild("2", "ĐỀ THI", ItemMenu.TYPE_CHILD, "3"));
        itemMenu.children.add(new ItemMenuChild("2", "NGỮ ÂM", ItemMenu.TYPE_CHILD, "3"));
        itemMenu.children.add(new ItemMenuChild("2", "NGỮ PHÁP", ItemMenu.TYPE_CHILD, "3"));
        menuList.add(itemMenu);

        itemMenu = new ItemMenu("4", "THI THPTQG", ItemMenu.TYPE_PARENT, "1");
        itemMenu.children.add(new ItemMenuChild("2", "Kết hợp câu", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "Từ trái nghĩa", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "Từ đồng nghĩa", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "Từ đồng nghĩa", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "Tìm lỗi sai", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "Điền từ", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "Điền từ", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "Giao tiếp", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "Ngữ âm", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "Ngữ pháp", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "Đọc hiểu", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "Đề thi", ItemMenu.TYPE_CHILD, "4"));
        menuList.add(itemMenu);

        itemMenu = new ItemMenu("4", "IELTS", ItemMenu.TYPE_PARENT, "1");
        itemMenu.children.add(new ItemMenuChild("2", "3.5+", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "5.5+", ItemMenu.TYPE_CHILD, "4"));
        itemMenu.children.add(new ItemMenuChild("2", "6.5+", ItemMenu.TYPE_CHILD, "4"));
        menuList.add(itemMenu);

        itemMenu = new ItemMenu("5", "TOEFL Junior", ItemMenu.TYPE_PARENT, "1");
        itemMenu.children.add(new ItemMenuChild("2", "650+", ItemMenu.TYPE_CHILD, "5"));
        itemMenu.children.add(new ItemMenuChild("2", "750+", ItemMenu.TYPE_CHILD, "5"));
        itemMenu.children.add(new ItemMenuChild("2", "850+", ItemMenu.TYPE_CHILD, "5"));
        menuList.add(itemMenu);

        itemMenu = new ItemMenu("6", "TOEIC", ItemMenu.TYPE_PARENT, "1");
        itemMenu.children.add(new ItemMenuChild("2", "350+", ItemMenu.TYPE_CHILD, "6"));
        itemMenu.children.add(new ItemMenuChild("2", "550+", ItemMenu.TYPE_CHILD, "6"));
        itemMenu.children.add(new ItemMenuChild("2", "650+", ItemMenu.TYPE_CHILD, "6"));
        menuList.add(itemMenu);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!currentId.equals("-1")) {
                changeFragment(new HomeFragment());
                currentId = "-1";
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
    public void onSelected(String name, String id) {
        if (!id.equals(currentId)) {
            if (!id.equals("-1")) {
                if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mainBinding.drawerLayout.closeDrawer(GravityCompat.START);
                    toolbar.setTitle(name);
                }
                changeFragment(new CategoryMainFragment());
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
}
