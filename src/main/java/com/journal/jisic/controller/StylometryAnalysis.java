package com.journal.jisic.controller;

import com.journal.jisic.IOHandler.IOProperties;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author amendrashrestha
 */
public class StylometryAnalysis {

    List<String> functionWords;

    public StylometryAnalysis() {
        loadFunctionWords(IOProperties.FUNCTION_WORDS_PATH);
    }

    /**
     * Load the function words into list
     *
     * @param path
     */
    private void loadFunctionWords(String path) {
        functionWords = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));

            String strLine;
            while ((strLine = br.readLine()) != null) {
                functionWords.add(strLine);
            }
            br.close();
        } catch (IOException e) {
        }
    }

    /**
     * Extract words from text string, remove punctuation etc.
     *
     * @param text
     * @return
     */
    public List<String> extractWords(String text) {
        text = text.toLowerCase();
        List<String> wordList = new ArrayList<>();
        String[] words = text.split("\\s+");

        wordList.addAll(Arrays.asList(words));
        return wordList;
    }

    /**
     * Create a list containing the number of occurrences of the various
     * function words in the post (list of extracted words)
     *
     * @param words
     * @param wordSize
     * @return
     */
    public ArrayList<Float> countFunctionWords(List<String> words, int wordSize) {
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(functionWords.size(), 0.0f));	// Initialize to zero
        for (String word1 : words) {
            String word = word1.toLowerCase();
            if (functionWords.contains(word)) {
                int place = functionWords.indexOf(word);
                float value = tmpCounter.get(place);
                value++;
                tmpCounter.set(place, value);
            }
        }
        // "Normalize" the values by dividing with length of the post (nr of words in the post)
        for (int i = 0; i < tmpCounter.size(); i++) {
            Float wordCount = tmpCounter.get(i);
            if (wordCount != 0) {
                tmpCounter.set(i, wordCount / (float) wordSize);
            }
        }
//        System.out.println("Function Words: " + tmpCounter);
        return tmpCounter;
    }
    
    /**
     * Calculate the count of smilies in post
     *
     * @param words
     * @param wordSize
     * @return
     */
    public ArrayList<Float> countSmiley(List<String> words, int wordSize) {
        String[] ch = {":\')", ":-)", ";-)", ":P", ":D", ":X", "<3", ":)", ";)", ":@", ":*", ":|", ":$", "%)"};
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(ch.length, 0.0f));	// Initialize to zero

        for (int i = 0; i < ch.length; i++) {
//            System.out.println("*********");
//            System.out.println("Smile: " + ch[i]);
            int value = countOccurrencesOfSmiley(words, ch[i]);
            tmpCounter.set(i, (float) value);
        }

        for (int j = 0; j < tmpCounter.size(); j++) {
            tmpCounter.set(j, (tmpCounter.get(j) / (float) wordSize));
        }
//        System.out.println("Smiley: " + tmpCounter);

        return tmpCounter;
    }

    /**
     * Calculate the count of Hashtags in post
     *
     * @param post
     * @param wordSize
     * @return
     */
    public ArrayList<Float> countHashTags(String post, int wordSize) {
        post = post.toLowerCase();
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(1, 0.0f)); // Initialize to zero
//        float wordCount = extractWords(post).size();

        String hashStr = "(?:\\s|\\A)[#]+([A-Za-z0-9-_]+)";
        Pattern hPattern = Pattern.compile(hashStr);
        Matcher m = hPattern.matcher(post);

        int i = 0;
        while (m.find()) {
            i++;
        }
        tmpCounter.set(0, (i / (float) wordSize));
//        System.out.println("HashTags Words: " + tmpCounter);
        return tmpCounter;
    }

    /**
     * Calculate the count of URLs in post
     *
     * @param post
     * @param wordSize
     * @return
     */
    public ArrayList<Float> countURLs(String post, int wordSize) {
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(1, 0.0f));	// Initialize to zero
//        float wordCount = extractWords(post).size();

        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|"
                + "(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern uPattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = uPattern.matcher(post);

        int i = 0;
        while (m.find()) {
            i++;
        }
        tmpCounter.set(0, (i / (float) wordSize));
//        System.out.println("URLs Words: " + tmpCounter);
        return tmpCounter;
    }

    /**
     * Calculate the count of mention in post
     *
     * @param post
     * @param wordSize
     * @return
     */
    public ArrayList<Float> countMention(String post, int wordSize) {
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(1, 0.0f));	// Initialize to zero
//        float wordCount = extractWords(post).size();

        String mentionStr = "(?:\\s|\\A)[@]+([A-Za-z0-9-_]+)";
        Pattern mPattern = Pattern.compile(mentionStr);
        Matcher m = mPattern.matcher(post);

        int i = 0;
        while (m.find()) {
            i++;
        }
        tmpCounter.set(0, (i / (float) wordSize));
//        System.out.println("Mentions Words: " + tmpCounter);
        return tmpCounter;
    }

    /**
     * Create a list containing the number of occurrences of letters a to z in
     * the text
     *
     * @param post
     * @param realPost
     * @return
     */
    public ArrayList<Float> countCharactersAZ(String post, String realPost) {
        post = post.toLowerCase();	// Upper or lower case does not matter, so make all letters lower case first...
        char[] ch = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(ch.length, 0.0f));
        for (int i = 0; i < ch.length; i++) {
            int value = countOccurrences(post, ch[i]);
            tmpCounter.set(i, (float) value);
        }

        // "Normalize" the values by dividing with total nr of characters in the post (excluding white spaces)
        int length = realPost.replaceAll(" ", "").length();
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) length);
        }
//        System.out.println("Characters Words: " + tmpCounter);
        return tmpCounter;
    }

    /**
     * Create a list containing the number of special characters in the text
     *
     * @param post
     * @param realPost
     * @return
     */
    public ArrayList<Float> countSpecialCharacters(String post, String realPost) {
        post = post.toLowerCase();	// Upper or lower case does not matter, so make all letters lower case first...
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '?', '!', ',', ';', ':', '(', ')', '"', '-', '\''};
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(ch.length, 0.0f));
        for (int i = 0; i < ch.length; i++) {
            int value = countOccurrences(post, ch[i]);
            tmpCounter.set(i, (float) value);
        }

        // "Normalize" the values by dividing with total nr of characters in the post (excluding whitespaces)
        int length = realPost.replaceAll(" ", "").length();
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) length);
        }
//        System.out.println("Special Character Words: " + tmpCounter);
        return tmpCounter;
    }

    /**
     * Counts the frequency of various word lengths in the list of words.
     *
     * @param words
     * @param wordSize
     * @return
     */
    public ArrayList<Float> countWordLengths(List<String> words, int wordSize) {
        ArrayList<Float> tmpCounter = new ArrayList<>(Collections.nCopies(20, 0.0f));	// Where 20 corresponds to the number of word lengths of interest
        int wordLength = 0;
        for (String word : words) {
            wordLength = word.length();
            // We only care about wordLengths in the interval 1-20
            if (wordLength > 0 && wordLength <= 20) {
                float value = tmpCounter.get(wordLength - 1);	// Observe that we use wordLength-1 as index!
                value++;
                tmpCounter.set(wordLength - 1, value);
            }
        }

        // "Normalize" the values by dividing with length of the post (nr of words in the post)
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) wordSize);
        }
//        System.out.println("Word Length Words: " + tmpCounter);
        return tmpCounter;
    }

    /**
     * Count the number of occurrences of certain character in a String
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }

    /**
     * Count the number of occurrences of smilies in post
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int countOccurrencesOfSmiley(List<String> haystack, String needle) {
        int count = 0;

        for (String haystack1 : haystack) {
            if (haystack1.equals(needle)) {
                count++;
            }
        }
        return count;
    }
}
