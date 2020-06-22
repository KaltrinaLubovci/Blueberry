package com.kl.blueberry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kl.blueberry.R;
import com.kl.blueberry.adapters.navigation_drawer.NavigationMenuAdapter;
import com.kl.blueberry.api.ApiService;
import com.kl.blueberry.databinding.ActivityMainBinding;
import com.kl.blueberry.events.OpenActivityEvent;
import com.kl.blueberry.events.OpenActivityWithExtras;
import com.kl.blueberry.model.navigation_drawer.MenuItems;
import com.kl.blueberry.ui.home.HomeFragment;
import com.kl.blueberry.ui.playlist.PlaylistActivity;
import com.kl.blueberry.ui.profile.ProfileFragment;
import com.kl.blueberry.ui.search.SearchActivity;
import com.kl.blueberry.utils.ParentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends ParentActivity {

    @Inject
    ApiService apiService;
    RecyclerView rvNavigationMenu;
    ActivityMainBinding binding;
    NavigationMenuAdapter menuAdapter;
    ArrayList<MenuItems> menuItemsArrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(new MainViewModel());

        menuItemsArrList = new ArrayList<MenuItems>();

        binding.getViewModel().refresh();
        menuAdapter = new NavigationMenuAdapter(MainActivity.this, new ArrayList<>());
        rvNavigationMenu = binding.lNavigationMenu.findViewById(R.id.nav_menu_options);
        rvNavigationMenu.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvNavigationMenu.setAdapter(menuAdapter);

        openFragment(new HomeFragment(apiService));
        observeViewModel();
        onClick();
//
//        binding.btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //make api call
//                binding.getViewModel().search(MainActivity.this, apiService, "eminem");
//            }
//        });
    }


    private void openFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rl_fragment_holder, fragment);
        transaction.commit();
    }

    private void observeViewModel() {
        binding.getViewModel().menuItemsList.observe(this, new Observer<MenuItems[]>(){

            @Override
            public void onChanged(MenuItems[] menuItems) {
                menuItemsArrList.addAll(Arrays.asList(menuItems));
                menuAdapter.setMenuData(menuItemsArrList);

            }
        });
    }

    private void onClick() {

        binding.ivHome.setOnClickListener(onClick -> {
            openFragment(new HomeFragment(apiService));
            changeUIofTabs("home");
        });

        binding.ivProfile.setOnClickListener(onClick -> {
            openFragment(new ProfileFragment());
            changeUIofTabs("profile");
        });

        binding.ivSideMenu.setOnClickListener(onClick -> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });

        binding.ivSearch.setOnClickListener(onClick -> {
            EventBus.getDefault().post(new OpenActivityEvent(new SearchActivity()));
        });
    }

    private void changeUIofTabs(String clickedTab) {
        binding.ivHome.setImageResource(R.drawable.home_off_icon);
        binding.ivProfile.setImageResource(R.drawable.profile_off_icon);

        switch (clickedTab) {
            case "home":
                binding.ivHome.setImageResource(R.drawable.home_on_icon);
                break;
            case "profile":
                binding.ivProfile.setImageResource(R.drawable.profile_on_icon);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OpenActivityEvent openActivityEvent){
        Intent intent = new Intent(this, openActivityEvent.getActivity().getClass());
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OpenActivityWithExtras openActivityWithExtras){
        Intent intent = new Intent(this, openActivityWithExtras.getActivity().getClass());
        intent.putExtra("extras", openActivityWithExtras.getMessage());
        startActivity(intent);
    }
}
