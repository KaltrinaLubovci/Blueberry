package com.kl.blueberry.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.kl.blueberry.R;
import com.kl.blueberry.model.navigation_drawer.MenuItems;

/**
 * Created by Kaltrina Lubovci on 01,June,2020
 */
public class MainViewModel extends ViewModel {

    MutableLiveData<MenuItems[]> menuItemsList;

    public void refresh() {
        fetchHomeMenu();
    }

    private void fetchHomeMenu() {
        menuItemsList = new MutableLiveData<MenuItems[]>();
        MenuItems[] menuList = {new MenuItems("Home", R.drawable.home_on_icon),
                new MenuItems("Profile", R.drawable.profile_on_icon),
                new MenuItems("Search", R.drawable.search_blue),
                new MenuItems("About us", R.drawable.about_us_icon)};
        menuItemsList.setValue(menuList);
    }

}
