package com.kl.blueberry.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kl.blueberry.R;
import com.kl.blueberry.adapters.navigation_drawer.NavigationMenuAdapter;
import com.kl.blueberry.api.ApiService;
import com.kl.blueberry.data.AppPreferences;
import com.kl.blueberry.databinding.ActivityMainBinding;
import com.kl.blueberry.events.OpenActivityEvent;
import com.kl.blueberry.events.OpenActivityWithExtras;
import com.kl.blueberry.events.OpenFragmentEvent;
import com.kl.blueberry.events.ShowToastEvent;
import com.kl.blueberry.model.navigation_drawer.MenuItems;
import com.kl.blueberry.ui.home.HomeFragment;
import com.kl.blueberry.ui.profile.ProfileFragment;
import com.kl.blueberry.ui.search.SearchActivity;
import com.kl.blueberry.ui.splash_screen.SplashScreenActivity;
import com.kl.blueberry.utils.ParentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.kl.blueberry.utils.Usage.showToast;

public class MainActivity extends ParentActivity {

    @Inject
    ApiService apiService;
    @Inject
    AppPreferences appPreferences;
    RecyclerView rvNavigationMenu;
    ActivityMainBinding binding;
    CircleImageView ivProfile;
    NavigationMenuAdapter menuAdapter;
    ArrayList<MenuItems> menuItemsArrList;
    LinearLayout llLogout;
    Boolean isHomeFragment = false;

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

        llLogout = binding.lNavigationMenu.findViewById(R.id.ll_logout);
        ivProfile = binding.lNavigationMenu.findViewById(R.id.circle_iv_profile);
        openFragment(new HomeFragment(apiService));
        fillUsersDataSideMenu();
        observeViewModel();
        onClick();
    }

    private void fillUsersDataSideMenu() {
        TextView tvFullName, tvEmail;
        tvFullName = binding.lNavigationMenu.findViewById(R.id.tv_name);
        tvEmail = binding.lNavigationMenu.findViewById(R.id.tv_email);
        tvFullName.setText(appPreferences.getFullName());
        tvEmail.setText(appPreferences.getEmail());

        if (appPreferences.getImagePath() != null) {
            Glide.with(this).load(appPreferences.getImagePath()).into(ivProfile);
        }

    }

    private void openFragment(Fragment fragment) {
        if (fragment instanceof HomeFragment) {
            isHomeFragment = true;
        } else {
            isHomeFragment = false;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rl_fragment_holder, fragment);
        transaction.commit();
    }

    private void observeViewModel() {
        binding.getViewModel().menuItemsList.observe(this, new Observer<MenuItems[]>() {

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
            openFragment(new ProfileFragment(appPreferences));
            changeUIofTabs("profile");
        });

        binding.ivSideMenu.setOnClickListener(onClick -> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });

        binding.ivSearch.setOnClickListener(onClick -> {
            EventBus.getDefault().post(new OpenActivityEvent(new SearchActivity()));
        });

        llLogout.setOnClickListener(onClick -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater layoutInflater = getLayoutInflater();
            dialogBuilder.setView(layoutInflater.inflate(R.layout.dialog_layout, null));
            dialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    appPreferences.deletePrefs();
                    EventBus.getDefault().post(new OpenActivityEvent(new SplashScreenActivity()));
                    finish();
                }
            });
            dialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogBuilder.show();

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

    @Subscribe
    public void onEvent(OpenActivityEvent openActivityEvent) {
        Intent intent = new Intent(this, openActivityEvent.getActivity().getClass());
        startActivity(intent);
    }

    @Subscribe
    public void onEvent(OpenFragmentEvent event) {
        switch (event.getFragmentType()) {
            case "home":
                if (!isHomeFragment) {
                    openFragment(new HomeFragment(apiService));
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    binding.ivHome.callOnClick();
                } else {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case "profile":
                if (isHomeFragment) {
                    openFragment(new ProfileFragment(appPreferences));
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    binding.ivProfile.callOnClick();
                } else {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;

        }
    }

    @Subscribe
    public void onEvent(OpenActivityWithExtras openActivityWithExtras) {
        Intent intent = new Intent(this, openActivityWithExtras.getActivity().getClass());
        intent.putExtra("extras", openActivityWithExtras.getMessage());
        startActivity(intent);
    }

    @Subscribe
    public void onEvent(ShowToastEvent event) {
        showToast(this, event.getMessage());
    }

}
