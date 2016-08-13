package com.example.android.miwok;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by farhan on 03-Aug-16.
 */
public class WordAdapter extends ArrayAdapter<Word>{
    private int mColorResourceId;
    /**constructor for initializing Words
     *
     * @param context for the current contex.
     * @param objects
     */
    public WordAdapter(Context context, List<Word> objects,int mColorResourceId){
        //as this is not a single text . The adapter is not using the second argument
        //so, it can be any value. Here, I used 0.
        super(context,0,objects);
        this.mColorResourceId = mColorResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Word localWord = getItem(position);
        TextView miwokTranslation = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTranslation.setText(localWord.getmMiwokTranslation());
        TextView defaultTranslation = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTranslation.setText(localWord.getmDefaultTranslation());
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_view);
        if(localWord.hasImage())
        {
            imageView.setImageResource(localWord.getImageResourceID());
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.GONE);
        }

        //Find the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }
}
