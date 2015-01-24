package com.agsystems.twittervote;

/**
 * Created by geovanni on 23/01/15.
 */
public class VoteTweet {
    private String text;
    private String hashtag1;
    private String hashtag2;

    public VoteTweet(String text, String hashtag1, String hashtag2){
        this.text = text;
        this.hashtag1 = hashtag1;
        this.hashtag2 = hashtag2;
    }

    public String getText() {
        return text;
    }

    public void setTexto(String text) {
        this.text = text;
    }

    public String getHashtag1() {
        return hashtag1;
    }

    public void setHashtag1(String hashtag1) {
        this.hashtag1 = hashtag1;
    }

    public String getHashtag2() {
        return hashtag2;
    }

    public void setHashtag2(String hashtag2) {
        this.hashtag2 = hashtag2;
    }
}
