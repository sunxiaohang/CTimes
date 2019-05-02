package com.sunxiaohang.root.ctimes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sunxiaohang.root.ctimes.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private Bundle subscribedSubject;
    private StoriesFragment storiesFragment;
    private MoviesFragment moviesFragment;
    private MediaFragment mediaFragment;
    private AWordFragment aWordFragment;
    private Fragment currentFragment;
    private DrawerLayout drawerLayout;
    private NavigationView leftNavigatorView;
    private Toolbar toolbar;

    private final String FRAGMENT_TAG_STORIES = "FRAGMENT_TAG_STORIES";
    private final String FRAGMENT_TAG_BOOK = "FRAGMENT_TAG_BOOKS";
    private final String FRAGMENT_TAG_MEDIA = "FRAGMENT_TAG_MEDIA";
    private final String FRAGMENT_TAG_GEOGRAPHIC = "FRAGMENT_TAG_PHOTOGRAPHIC";
    private FragmentManager fragmentManager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_stories:
                    fragmentManager.beginTransaction().hide(currentFragment).show(storiesFragment).commit();
                    currentFragment = storiesFragment;
                    return true;
                case R.id.navigation_books:
                    fragmentManager.beginTransaction().hide(currentFragment).show(moviesFragment).commit();
                    currentFragment= moviesFragment;
                    return true;
                case R.id.navigation_media:
                    fragmentManager.beginTransaction().hide(currentFragment).show(mediaFragment).commit();
                    currentFragment = mediaFragment;
                    return true;
                case R.id.navigation_geographic:
                    fragmentManager.beginTransaction().hide(currentFragment).show(aWordFragment).commit();
                    currentFragment = aWordFragment;
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_top_extension,menu);
        menu.setGroupVisible(0,true);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        subscribedSubject = getIntent().getBundleExtra("args");
        BottomNavigationView navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.main_content_toolbar);
        toolbar.inflateMenu(R.menu.navigation);
        setSupportActionBar(toolbar);
        leftNavigatorView = findViewById(R.id.main_navigator_left_container);
        //top left navigator click listener
        leftNavigatorView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()){
                    case R.id.action_inbox:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.action_github:
                        intent = new Intent(MainActivity.this,NavigatorDetailsActivity.class);
                        intent.putExtra("requestUrl", Utils.GITHUBADDRESS);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent);
                        return true;
                    case R.id.action_blog:
                        intent = new Intent(MainActivity.this,NavigatorDetailsActivity.class);
                        intent.putExtra("requestUrl", Utils.GITHUBBLOG);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent);
                        return true;
                    case R.id.action_android:
                        intent = new Intent(MainActivity.this,NavigatorDetailsActivity.class);
                        intent.putExtra("requestUrl", Utils.ANDROID);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent);
                        return true;
                    case R.id.action_about:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.action_settings:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.action_exit:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        return true;
                }
                return false;
            }
        });
        drawerLayout = findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigator,R.string.close_navigator);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(savedInstanceState != null){
            storiesFragment = (StoriesFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG_STORIES);
            moviesFragment = (MoviesFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG_BOOK);
            mediaFragment = (MediaFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG_MEDIA);
            aWordFragment = (AWordFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG_GEOGRAPHIC);
        }else initialFragment();
        fragmentManager.beginTransaction().add(R.id.main_container,storiesFragment,FRAGMENT_TAG_STORIES)
                .add(R.id.main_container, moviesFragment,FRAGMENT_TAG_BOOK)
                .add(R.id.main_container,mediaFragment,FRAGMENT_TAG_MEDIA)
                .add(R.id.main_container, aWordFragment,FRAGMENT_TAG_GEOGRAPHIC)
                .hide(moviesFragment)
                .hide(mediaFragment)
                .hide(aWordFragment)
                .commit();
        currentFragment = storiesFragment;
        //申请浮窗权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
            }
        }
    }

    private void initialFragment() {
        //storiesFragment
        storiesFragment = new StoriesFragment();
        storiesFragment.setArguments(subscribedSubject);
        //booksFragment
        moviesFragment = new MoviesFragment();
        //mediaFragment
        mediaFragment = new MediaFragment();
        //aWordFragment
        aWordFragment = new AWordFragment();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
