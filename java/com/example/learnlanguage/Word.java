package com.example.learnlanguage;

public class Word {

    private String miwokTrans;
    private String defaultTrans;
    private int imageResourceId = NO_IMAGE_PROVIDED;
    private int audioResourceId;

    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String defaultTrans, String miwokTrans, int imageResourceId, int audioResourceId) {
        this.miwokTrans = miwokTrans;
        this.defaultTrans = defaultTrans;
        this.imageResourceId = imageResourceId;
        this.audioResourceId = audioResourceId;
    }

    public Word(String defaultTrans, String miwokTrans, int audioResourceId) {
        this.miwokTrans = miwokTrans;
        this.defaultTrans = defaultTrans;
        this.audioResourceId = audioResourceId;
    }

    public String getMiwokTrans() {
        return miwokTrans;
    }

    public  String getDefaultTrans() {
        return defaultTrans;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }

    public boolean hasImage() {
        return imageResourceId != NO_IMAGE_PROVIDED;
    }

}
