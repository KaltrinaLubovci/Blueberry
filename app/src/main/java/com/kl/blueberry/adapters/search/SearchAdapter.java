package com.kl.blueberry.adapters.search;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.kl.blueberry.R;
import com.kl.blueberry.model.search_music.SearchMusicDataResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaltrina Lubovci on 22,June,2020
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private ArrayList<SearchMusicDataResponse> searchMusicResponseArrList;
    public ExoPlayer exoPlayer;
    public DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    public DefaultTrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

    public SearchAdapter(Context context, ArrayList<SearchMusicDataResponse> searchResponseList) {
        this.context = context;
        this.searchMusicResponseArrList = searchResponseList;
    }

    public void setSearchData(List<SearchMusicDataResponse> newResponseList) {
        searchMusicResponseArrList.clear();
        searchMusicResponseArrList.addAll(newResponseList);
        notifyDataSetChanged();
    }

    public void closeAllPlayers() {
        for (int i = 0; i < searchMusicResponseArrList.size(); i++) {
            if (searchMusicResponseArrList.get(i).getPlaying()) {
                searchMusicResponseArrList.get(i).setPlaying(false);
                if (exoPlayer != null) {
                    exoPlayer.stop();
                }
            }
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_rv_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bindViews(searchMusicResponseArrList.get(position), position);

        if (searchMusicResponseArrList.get(position).getPlaying()) {
            holder.playAudio.setVisibility(View.GONE);
            holder.closeAudio.setVisibility(View.VISIBLE);
            holder.playAudioHolder.setVisibility(View.VISIBLE);
            holder.retryPlayer();
        } else {
            holder.playAudio.setVisibility(View.VISIBLE);
            holder.closeAudio.setVisibility(View.GONE);
            holder.playAudioHolder.setVisibility(View.GONE);
            holder.stopPlayer();
        }
    }

    @Override
    public int getItemCount() {
        return searchMusicResponseArrList.size();
    }


    class SearchViewHolder extends RecyclerView.ViewHolder {
        View view;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }


        ImageView closeAudio, playAudio, ivSongImage;
        TextView tvSongName, tvRenderNumber, tvGoToDeezer;
        SimpleExoPlayerView exoPlayerView;
        RelativeLayout playAudioHolder;
        WebView wwDezzer;


        void bindViews(SearchMusicDataResponse searchItem, int position) {
            tvRenderNumber = view.findViewById(R.id.tv_render_number_search);
            tvSongName = view.findViewById(R.id.tv_song_name_search);
            exoPlayerView = view.findViewById(R.id.player_view_search);
            closeAudio = view.findViewById(R.id.iv_close_audio_search);
            playAudioHolder = view.findViewById(R.id.ll_play_audio_holder_search);
            playAudio = view.findViewById(R.id.iv_play_icon_search);
            ivSongImage = view.findViewById(R.id.iv_song_image_search);
            tvGoToDeezer = view.findViewById(R.id.tv_go_to_deezer_search);
            wwDezzer = view.findViewById(R.id.webview_deezer_search);
            Glide.with(context).load(searchItem.getAlbum().getCover_medium()).into(ivSongImage);
            tvRenderNumber.setText(String.valueOf(position + 1));
            tvSongName.setText(context.getString(R.string.song_artist, searchItem.getArtist().getName(), "-", searchItem.getTitle()));

            tvGoToDeezer.setOnClickListener(onClick -> {
                wwDezzer.loadUrl(searchItem.getLink());
            });

            closeAudio.setOnClickListener(onClick -> {
                playAudio.setVisibility(View.VISIBLE);
                playAudioHolder.setVisibility(View.GONE);
                closeAudio.setVisibility(View.GONE);
                if (exoPlayer != null) {
                    exoPlayer.stop();
                }
            });

            playAudio.setOnClickListener(onClick -> {
                for (int i = 0; i < searchMusicResponseArrList.size(); i++) {
                    if (searchMusicResponseArrList.get(i).getPlaying()) {
                        searchMusicResponseArrList.get(i).setPlaying(false);
                        if (exoPlayer != null) {
                            exoPlayer.stop();
                        }
                    }
                }
                searchMusicResponseArrList.get(position).setPlaying(true);
                playAudio(context, searchItem.getPreview());
                if (exoPlayer != null) {
                    exoPlayer.retry();
                }
                notifyDataSetChanged();
            });

        }


        void playAudio(Context context, String audioUrl) {
            try {
                exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
                Uri audioUri = Uri.parse(audioUrl);
                ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer_blueberry")).createMediaSource(audioUri);
                Player.EventListener exoListener = new Player.EventListener() {
                    @Override
                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                        if (playbackState == Player.STATE_ENDED) {
                            closeAudio.setVisibility(View.GONE);
                            playAudio.setVisibility(View.VISIBLE);
                        }
                    }
                };
                exoPlayer.addListener(exoListener);
                exoPlayerView.setPlayer(exoPlayer);
                exoPlayer.prepare(mediaSource);
                exoPlayerView.setControllerShowTimeoutMs(0);
                exoPlayerView.setControllerHideOnTouch(false);
                exoPlayer.setPlayWhenReady(true);


            } catch (Exception e) {
                Log.d("PlaylistAdapter", "Exoplayer error: " + e);
            }
        }

        void stopPlayer() {
            if (exoPlayer != null) {
//                exoPlayer.stop();
//                exoPlayer.release();
            }
        }

        void retryPlayer() {
            if (exoPlayer != null) {
                exoPlayer.retry();
            }
        }
    }
}
