package com.kl.blueberry.model.navigation_drawer;

/**
 * Created by Kaltrina Lubovci on 14,June,2020
 */
public class MenuItems {
    String menuName;
    int menuIcon;

    public MenuItems(String menuName, int menuIcon) {
        this.menuName = menuName;
        this.menuIcon = menuIcon;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getMenuIcon() {
        return menuIcon;
    }
}
