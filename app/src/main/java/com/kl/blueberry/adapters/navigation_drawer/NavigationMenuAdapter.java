package com.kl.blueberry.adapters.navigation_drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kl.blueberry.R;
import com.kl.blueberry.events.OpenActivityEvent;
import com.kl.blueberry.model.navigation_drawer.MenuItems;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaltrina Lubovci on 13,June,2020
 */
public class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuAdapter.NavigationMenuViewHolder>{

    private Context context;
    private ArrayList<MenuItems> menuItemsList;

    public NavigationMenuAdapter(Context context, ArrayList<MenuItems> menuItemsList) {
        this.context = context;
        this.menuItemsList = menuItemsList;
    }

    public void setMenuData(List<MenuItems> newMenuItems){
        menuItemsList.clear();
        menuItemsList.addAll(newMenuItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NavigationMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_rv_item, parent, false);
        return new NavigationMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationMenuViewHolder holder, int position) {
        holder.bindViews(menuItemsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return menuItemsList.size();
    }

    class NavigationMenuViewHolder extends RecyclerView.ViewHolder{
        View view;
        public NavigationMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }


        ImageView ivItemIcon;
        TextView tvItemName;
        LinearLayout llCell;


        void bindViews(MenuItems menuItem, int position){
            ivItemIcon = view.findViewById(R.id.iv_menu_item_icon);
            tvItemName = view.findViewById(R.id.tv_menu_item_name);
            llCell = view.findViewById(R.id.ll_item_holder);

            ivItemIcon.setImageResource(menuItem.getMenuIcon());
            tvItemName.setText(menuItem.getMenuName());

            llCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    EventBus.getDefault().post(OpenActivityEvent());
                    //open screens from side menu
                }
            });
        }
    }
}
