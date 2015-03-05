package com.journal.jisic.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ITE
 */
public class Alias {

    private List<Float> featureVector;
    private int nrOfFeatures;
    private int nrOfSwedishFeatures;
    private int user;
    private String type;
    private ArrayList<ArrayList<Float>> featureVectorPostList;
    public List<String> posts;
    public List<String> postTime;
    public String singlePost;
    private List postDate;

    public Alias(int userID) throws SQLException {
        this.user = userID;
        featureVector = new ArrayList<Float>();
        featureVectorPostList = new ArrayList<ArrayList<Float>>();
    }

    public Alias() {
        setNrOfFeatures(361); //english feature vector size
        setNrOfSwedishFeatures(456); //swedish feature vector size
        
    }


    public ArrayList<ArrayList<Float>> initializeFeatureVectorPostList() {
        ArrayList<ArrayList<Float>> list = new ArrayList<ArrayList<Float>>();
        for (int j = 0; j < posts.size(); j++) {
            ArrayList<Float> featList = new ArrayList<Float>();
            for (int i = 0; i < nrOfFeatures; i++) {
                featList.add(0.0f);
            }
            list.add(featList);
        }
        return list;
    }
    
    public ArrayList<ArrayList<Float>> initializeFeatureVectorPostListForSwe() {
        ArrayList<ArrayList<Float>> list = new ArrayList<ArrayList<Float>>();
        for (int j = 0; j < posts.size(); j++) {
            ArrayList<Float> featList = new ArrayList<Float>();
            for (int i = 0; i < nrOfSwedishFeatures; i++) {
                featList.add(0.0f);
            }
            list.add(featList);
        }
        return list;
    }

    public int getUserID() {
        return user;
    }

    public void setUserID(int userID) {
        this.user = userID;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    public String getSinglePost() {
        return singlePost;
    }

    public void setSinglePost(String post) {
        this.singlePost = post;
    }

    public void addPost(String post) {
        posts.add(post);
    }

    public void addToFeatureVectorPostList(ArrayList<Float> freqDist, int startIndex, int index) {
        for (Float freqDist1 : freqDist) {
            featureVectorPostList.get(index).set(startIndex, freqDist1);
            startIndex++;
        }
    }

    public List<Float> getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(List<Float> featureVector) {
        this.featureVector = featureVector;
    }

    public ArrayList<ArrayList<Float>> getFeatureVectorPosList() {
        return featureVectorPostList;
    }

    public void setFeatureVectorPosList(ArrayList<ArrayList<Float>> list) {
        featureVectorPostList = list;
    }

    public void setFeatureValue(int index, float newValue) {
        featureVector.set(index, newValue);
    }

    public int getNrOfFeatures() {
        return nrOfFeatures;
    }

    private void setNrOfFeatures(int nrOfFeatures) {
        this.nrOfFeatures = nrOfFeatures;
    }

    public void setPostTime(List postTime) {
        this.postTime = postTime;
    }

    public List getPostTime() {
        return postTime;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    public void setPostDate(List postDate) {
        this.postDate = postDate;
    }

    public List getPostDate() {
        return postDate;
    }

    /**
     * @return the nrOfSwedishFeatures
     */
    public int getNrOfSwedishFeatures() {
        return nrOfSwedishFeatures;
    }

    /**
     * @param nrOfSwedishFeatures the nrOfSwedishFeatures to set
     */
    private void setNrOfSwedishFeatures(int nrOfSwedishFeatures) {
        this.nrOfSwedishFeatures = nrOfSwedishFeatures;
    }
}
