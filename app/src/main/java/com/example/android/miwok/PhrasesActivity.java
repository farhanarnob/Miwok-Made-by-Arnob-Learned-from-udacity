package com.example.android.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Array list for phrases
        ArrayList<Word> colors = new ArrayList<>();
        colors.add(new Word("Where are you going?","minto wuksus"));
        colors.add(new Word("What is your name?","tinnә oyaase'nә"));
        colors.add(new Word("My name is...","oyaaset..."));
        colors.add(new Word("How are you feeling?","michәksәs?"));
        colors.add(new Word("I’m feeling good.","kuchi achit"));
        colors.add(new Word("Are you coming?","әәnәs'aa?"));
        colors.add(new Word("Yes, I’m coming.","әәnәm"));
        colors.add(new Word("I’m coming.","chiwiiṭә"));
        colors.add(new Word("Let’s go.","yoowutis"));
        colors.add(new Word("Come here.","әnni'nem"));


        //Word Adapter for displaying color
        WordAdapter adapter = new WordAdapter(this,colors,R.color.category_phrases);
        ListView phrase = (ListView) findViewById(R.id.list);
        phrase.setAdapter(adapter);
    }
}
