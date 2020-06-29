package com.kl.blueberry.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.kl.blueberry.R;
import com.kl.blueberry.api.ApiService;
import com.kl.blueberry.databinding.HomeFragmentBinding;
import com.kl.blueberry.events.OpenActivityEvent;
import com.kl.blueberry.model.playlist.PlaylistTracksDataResponse;
import com.kl.blueberry.ui.playlist.PlaylistActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;

    ApiService apiService;



    public HomeFragment(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        binding.setViewModel(new HomeViewModel());

        binding.getViewModel().getMainPlaylist(apiService);
        observeViewModel();

        onClicks();
        return binding.getRoot();
    }


    private void observeViewModel(){

        binding.getViewModel().loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading){
                    binding.rlLoader.setVisibility(View.VISIBLE);
                } else {
                    binding.rlLoader.setVisibility(View.GONE);
                }
            }
        });

        binding.getViewModel().playlistTitle.observe(this,new Observer<String>(){
            @Override
            public void onChanged(String s) {
                binding.tvPlaylistName.setText(s);
            }
        });

        binding.getViewModel().playlistImage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ImageView ivPlaylist = binding.ivPlaylistImage;

                Glide.with(HomeFragment.this).load(s).into(ivPlaylist);
            }
        });
    }


    private void onClicks(){
        binding.cvPlaylistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenActivityEvent(new PlaylistActivity()));
            }
        });
    }


}
