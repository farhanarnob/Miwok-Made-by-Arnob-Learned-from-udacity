package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    AudioManager audioManager;

    //variable for audio focus gain or not
    private int result;

    /**
     * Creating audioFocusChangListener so that we can take action to control what is be change on media player if other audio will play
     */
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
                    || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releasedMediaPlayer();
            }
        }
    };
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releasedMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        //creating up navigation action

        //Array list for color word
        final ArrayList<Word> colors = new ArrayList<>();
        colors.add(new Word("red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        colors.add(new Word("green","chokokki",R.drawable.color_green,R.raw.color_green));
        colors.add(new Word("brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        colors.add(new Word("gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        colors.add(new Word("black","kululli",R.drawable.color_black,R.raw.color_black));
        colors.add(new Word("white","kelelli",R.drawable.color_white,R.raw.color_white));
        colors.add(new Word("dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        colors.add(new Word("mustard yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        // initializing audio manager
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //Request audio focus for playback
        result = audioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        //Word Adapter for displaying color
        WordAdapter adapter = new WordAdapter(this,colors,R.color.category_colors);
        final ListView colorList = (ListView) findViewById(R.id.list);
        colorList.setAdapter(adapter);
        colorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word color = colors.get(position);
                mediaPlayer = MediaPlayer.create(ColorsActivity.this,color.getmSoundResourceID());
                mediaPlayer.start();
                colorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Word word = colors.get(position);
                        // Release the media player if it currently exists because we are about to play
                        // a different sound file.
                        releasedMediaPlayer();
                        mediaPlayer = MediaPlayer.create(ColorsActivity.this,word.getmSoundResourceID());

                        if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                            Log.v("ColorsActivity","start");
                            mediaPlayer.start();
                        }
                        // Setup a listener on the media player, so that we can stop and release the
                        // media player once finished the playing.
                        mediaPlayer.setOnCompletionListener(onCompletionListener);

                    }
                });
            }
        });
    }
    private void releasedMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
            audioManager.abandonAudioFocus(audioFocusChangeListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        //
        releasedMediaPlayer();
    }
}
