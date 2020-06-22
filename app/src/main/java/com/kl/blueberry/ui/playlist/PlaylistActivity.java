package com.kl.blueberry.ui.playlist;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kl.blueberry.R;
import com.kl.blueberry.adapters.playlist.PlaylistAdapter;
import com.kl.blueberry.api.ApiService;
import com.kl.blueberry.databinding.PlaylistActivityBinding;
import com.kl.blueberry.events.OpenActivityEvent;
import com.kl.blueberry.model.playlist.PlaylistTracksDataResponse;
import com.kl.blueberry.ui.home.HomeFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class PlaylistActivity extends AppCompatActivity {

    PlaylistActivityBinding binding;
    ArrayList<PlaylistTracksDataResponse> playTracksArrList = new ArrayList<>();
    RecyclerView rvPlaylist;
    PlaylistAdapter playlistAdapter;
    WebView webView;

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.playlist_activity);
        binding.setViewModel(new PlaylistViewModel());

        binding.getViewModel().getMainPlaylist(apiService);
        playlistAdapter = new PlaylistAdapter(PlaylistActivity.this, new ArrayList<>());
        rvPlaylist = binding.rvPlaylistTracks;
        rvPlaylist.setLayoutManager(new LinearLayoutManager(PlaylistActivity.this));
        rvPlaylist.setAdapter(playlistAdapter);
        observeViewModel();
        onClicks();

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
                binding.tvTitle.setText(s);
            }
        });

        binding.getViewModel().playlistTracks.observe(this, new Observer<List<PlaylistTracksDataResponse>>() {
            @Override
            public void onChanged(List<PlaylistTracksDataResponse> playlistTracksDataResponses) {
                playTracksArrList.addAll(playlistTracksDataResponses);
                playlistAdapter.setPlaylistData(playTracksArrList);
                System.out.println("trackssss sizeeee " + playTracksArrList.size());
            }
        });

    }

    private void onClicks(){
        binding.ivBack.setOnClickListener(onClick -> onBackPressed());

        binding.tvGoToDeezer.setOnClickListener(onClick -> {
            webView = binding.webviewDeezer;
            webView.loadUrl("https://www.deezer.com/en/playlist/1963962142");
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        playlistAdapter.closeAllPlayers();
    }
}
