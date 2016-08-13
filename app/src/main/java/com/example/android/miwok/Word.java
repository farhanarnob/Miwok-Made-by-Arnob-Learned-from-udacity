package com.example.android.miwok;

/**
 * Created by farhan on 03-Aug-16.
 * {@link Word} represents a vocabulary word that the user want to learn.
 * IT contains a default translation and a Miwok translation for that word.
 */
public class Word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceID = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     *
     * @param mDefaultTranslation
     * @param mMiwokTranslation
     */
    public Word(String mDefaultTranslation, String mMiwokTranslation){
        this.mDefaultTranslation=mDefaultTranslation;
        this.mMiwokTranslation=mMiwokTranslation;
    }

    /**
     *
      * @param mDefaultTranslation
     * @param mMiwokTranslation
     * @param mImageResourceID
     */
    public Word(String mDefaultTranslation, String mMiwokTranslation,int mImageResourceID)
    {
        this.mDefaultTranslation=mDefaultTranslation;
        this.mMiwokTranslation=mMiwokTranslation;
        this.mImageResourceID=mImageResourceID;
    }
    /**
     * get the default translation of the work;
     */
    public String getmDefaultTranslation(){
        return mDefaultTranslation;
    }
    /**
     * get the Miwok Translation
     */
    public String getmMiwokTranslation()
    {
        return mMiwokTranslation;
    }
    /**
     * get the image ID
     */
    public int getImageResourceID(){return mImageResourceID;}
    public boolean hasImage(){
        return mImageResourceID != NO_IMAGE_PROVIDED;
    }

}
