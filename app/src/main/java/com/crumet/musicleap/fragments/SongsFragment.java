package com.crumet.musicleap.fragments;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crumet.musicleap.R;
import com.crumet.musicleap.modal.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongsFragment extends Fragment {

    RecyclerView recyclerSongs;
    ArrayList<Song> songList = new ArrayList<>();

    public SongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_songs, container, false);
        recyclerSongs = v.findViewById(R.id.list_songs);
        getSongList();
        Collections.sort(songList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        SongAdapter adapter = new SongAdapter(songList, getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerSongs.setAdapter(adapter);
        recyclerSongs.setLayoutManager(linearLayoutManager);
        return v;
    }
    private void getSongList() {
        ContentResolver musicResolver = getActivity().getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int durationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                long thisDuration = musicCursor.getLong(durationColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist, thisDuration));
            }
            while (musicCursor.moveToNext());
        }
    }




    public class SongAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<Song> songList;
        private Context context;

        SongAdapter(ArrayList<Song> songList, Context context) {
            this.songList = songList;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.model_song, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder h, @SuppressLint("RecyclerView") final int position) {
            final Song song = songList.get(position);
            //h.imageView.setImageResource(song.getAlbumArt());
            h.textTitle.setText(song.getTitle());
            h.textArtist.setText(song.getArtist());
            //   h.textDuration.setText((int) song.getDuration());
            h.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*musicSrv.setSong(position);
                    musicSrv.playSong();*/
                }
            });
        }

        @Override
        public int getItemCount() {
            return songList.size();

        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textTitle, textArtist,textDuration;
        MyViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.album_art);
            textTitle = v.findViewById(R.id.song_title);
            textArtist = v.findViewById(R.id.song_artist);
            textDuration = v.findViewById(R.id.song_duration);
        }
    }
}
