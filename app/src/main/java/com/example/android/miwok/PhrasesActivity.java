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

public class PhrasesActivity extends AppCompatActivity {
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

        //Array list for phrases
        final ArrayList<Word> phrase = new ArrayList<>();
        phrase.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        phrase.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        phrase.add(new Word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        phrase.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        phrase.add(new Word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        phrase.add(new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        phrase.add(new Word("Yes, I’m coming.","әәnәm",R.raw.phrase_yes_im_coming));
        phrase.add(new Word("I’m coming.","chiwiiṭә",R.raw.phrase_im_coming));
        phrase.add(new Word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        phrase.add(new Word("Come here.","әnni'nem",R.raw.phrase_come_here));

        // initializing audio manager
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //Request audio focus for playback
        result = audioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        //Word Adapter for displaying color
        WordAdapter adapter = new WordAdapter(this,phrase,R.color.category_phrases);
        ListView phraseList = (ListView) findViewById(R.id.list);
        phraseList.setAdapter(adapter);
        phraseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word color = phrase.get(position);
                releasedMediaPlayer();
                mediaPlayer = MediaPlayer.create(PhrasesActivity.this,color.getmSoundResourceID());
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    Log.v("PhrasesActivity","start");
                    mediaPlayer.start();
                }
                mediaPlayer.setOnCompletionListener(onCompletionListener);
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
