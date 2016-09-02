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

public class FamilyActivity extends AppCompatActivity {
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
        getSupportActionBar().setTitle("Family Title");

        /**
         * Array list for Family Members
         */
        final ArrayList<Word> familyMembers = new ArrayList<>();
        familyMembers.add(new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        familyMembers.add(new Word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        familyMembers.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        familyMembers.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        familyMembers.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        familyMembers.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        familyMembers.add(new Word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        familyMembers.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        familyMembers.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        familyMembers.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        // initializing audio manager
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //Request audio focus for playback
        result = audioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        /**List view for displaying word
         *
         */
        ListView familyMember = (ListView) findViewById(R.id.list);
        WordAdapter adapter = new WordAdapter(this,familyMembers,R.color.category_family);
        familyMember.setAdapter(adapter);
        familyMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word familyMember = familyMembers.get(position);
                releasedMediaPlayer();
                mediaPlayer = MediaPlayer.create(FamilyActivity.this,familyMember.getmSoundResourceID());
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    Log.v("FamilyActivity","start");
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
