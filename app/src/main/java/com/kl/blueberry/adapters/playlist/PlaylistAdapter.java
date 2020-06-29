package com.kl.blueberry.adapters.playlist;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.kl.blueberry.R;
import com.kl.blueberry.model.playlist.PlaylistTracksDataResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaltrina Lubovci on 15,June,2020
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private Context context;
    private ArrayList<PlaylistTracksDataResponse> playlistArrList;
    public ExoPlayer exoPlayer;
    public DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    public DefaultTrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

    public PlaylistAdapter(Context context, ArrayList<PlaylistTracksDataResponse> playlistList) {
        this.context = context;
        this.playlistArrList = playlistList;
    }

    public void setPlaylistData(List<PlaylistTracksDataResponse> newPlaylistList) {
        playlistArrList.clear();
        playlistArrList.addAll(newPlaylistList);
        notifyDataSetChanged();
    }

    public void closeAllPlayers() {
        for (int i = 0; i < playlistArrList.size(); i++) {
            if (playlistArrList.get(i).getPlaying()) {
                playlistArrList.get(i).setPlaying(false);
                if (exoPlayer != null) {
                    exoPlayer.stop();
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_playlist_item, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.bindViews(playlistArrList.get(position), position);

        if (playlistArrList.get(position).getPlaying()) {
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
        return playlistArrList.size();
    }


    class PlaylistViewHolder extends RecyclerView.ViewHolder {
        View view;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }


        ImageView closeAudio, playAudio;
        TextView tvSongName, tvRenderNumber;
        SimpleExoPlayerView exoPlayerView;
        RelativeLayout playAudioHolder;


        void bindViews(PlaylistTracksDataResponse playlistItem, int position) {
            tvRenderNumber = view.findViewById(R.id.tv_render_number);
            tvSongName = view.findViewById(R.id.tv_song_name);
            exoPlayerView = view.findViewById(R.id.player_view);
            closeAudio = view.findViewById(R.id.iv_close_audio);
            playAudioHolder = view.findViewById(R.id.ll_play_audio_holder);
            playAudio = view.findViewById(R.id.iv_play_icon);
            tvRenderNumber.setText(String.valueOf(position + 1));
            tvSongName.setText(context.getString(R.string.song_artist, playlistItem.getArtist().getName(), "-", playlistItem.getTitle()));

            closeAudio.setOnClickListener(onClick -> {
                playAudio.setVisibility(View.VISIBLE);
                playAudioHolder.setVisibility(View.GONE);
                closeAudio.setVisibility(View.GONE);
                if (exoPlayer != null) {
                    exoPlayer.stop();
                }
            });

            playAudio.setOnClickListener(onClick -> {
                for (int i = 0; i < playlistArrList.size(); i++) {
                    if (playlistArrList.get(i).getPlaying()) {
                        playlistArrList.get(i).setPlaying(false);
                        if (exoPlayer != null) {
                            exoPlayer.stop();
                        }
                    }
                }
                playlistArrList.get(position).setPlaying(true);
                playAudio(context, playlistItem.getPreview());
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
