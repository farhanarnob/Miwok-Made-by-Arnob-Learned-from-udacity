package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    //variable for audio focus gain or not
    private int result;

    /**
     * Creating audioFocusChangListener so that we can take action to control what is be change on media player if other audio will play
     */
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
                    || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
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

    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //create an array of words
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("tem", "na'accha", R.drawable.number_ten, R.raw.number_ten));

        //For audio focus initialization
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Request audio focus for playback
        result = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);


        ListView listView = (ListView) rootView.findViewById(R.id.list);
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                // Release the media player if it currently exists because we are about to play
                // a different sound file.
                releasedMediaPlayer();
                mediaPlayer = MediaPlayer.create(getActivity(), word.getmSoundResourceID());

                //Checking for audio focusing
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    Log.v("NumbersActivity", "start");
                    mediaPlayer.start();
                }
                // Setup a listener on the media player, so that we can stop and release the
                // media player once finished the playing.
                mediaPlayer.setOnCompletionListener(onCompletionListener);

            }
        });


        return rootView;
    }

    private void releasedMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        //If I release media player, We have no need of focusing audio player
        audioManager.abandonAudioFocus(audioFocusChangeListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        releasedMediaPlayer();
    }
}
