package com.example.android.miwok;

/**
 * Created by farhan on 03-Aug-16.
 * {@link Word} represents a vocabulary word that the user want to learn.
 * IT contains a default translation and a Miwok translation for that word.
 */
public class Word {
    private static final int NO_IMAGE_PROVIDED = -1;
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceID = NO_IMAGE_PROVIDED;
    private int mSoundResourceID;


    /**
     *
     * @param mDefaultTranslation
     * @param mMiwokTranslation
     * @param mSoundResourceID
     */
    public Word(String mDefaultTranslation, String mMiwokTranslation,int mSoundResourceID){
        this.mDefaultTranslation=mDefaultTranslation;
        this.mMiwokTranslation=mMiwokTranslation;
        this.mSoundResourceID = mSoundResourceID;
    }

    /**
     *
      * @param mDefaultTranslation
     * @param mMiwokTranslation
     * @param mImageResourceID
     * @param mSoundResourceID
     */
    public Word(String mDefaultTranslation, String mMiwokTranslation,int mImageResourceID,int mSoundResourceID)
    {
        this.mDefaultTranslation=mDefaultTranslation;
        this.mMiwokTranslation=mMiwokTranslation;
        this.mImageResourceID=mImageResourceID;
        this.mSoundResourceID = mSoundResourceID;
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
    public int getmSoundResourceID(){return mSoundResourceID;}
}
