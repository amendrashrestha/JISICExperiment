package com.journal.jisic.IOHandler;

import com.journal.jisic.controller.StylometryAnalysis;
import com.journal.jisic.controller.TimeAnalysis;
import com.journal.jisic.model.Alias;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import com.journal.jisic.model.Posts;
import com.journal.jisic.model.ReturnSortedUserList;
import com.journal.jisic.model.User;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author ITE
 */
public class IOReadWrite {

    public IOReadWrite() {
    }

    public void writeToFile(String fileName, String content) throws IOException {
        String getUserFolderName = getFolderName(fileName);
        CreateDirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH, getUserFolderName);
        String fileLocation = IOProperties.INDIVIDUAL_USER_FILE_PATH + getUserFolderName;
        String tempfileName = fileName + IOProperties.USER_FILE_EXTENSION;
        String completeFileNameNPath = fileLocation + "/" + tempfileName;
        File file = new File(completeFileNameNPath);
        file.createNewFile();
        try (PrintWriter out = new PrintWriter(new FileWriter(completeFileNameNPath, true))) {
            out.append(content + IOProperties.DATA_SEPERATOR);
        }
    }

    public String getFolderName(String userId) {
        String folderName = "";
        int userID = Integer.valueOf(userId);

        if (userID > 0 && userID <= 25000) {
            folderName = "25K";
        } else if (userID > 25000 && userID <= 50000) {
            folderName = "50K";
        } else if (userID > 50000 && userID <= 100000) {
            folderName = "100K";
        } else if (userID > 100000 && userID <= 150000) {
            folderName = "150K";
        } else if (userID > 150000 && userID <= 200000) {
            folderName = "200K";
        } else if (userID > 200000 && userID <= 250000) {
            folderName = "250K";
        } else if (userID > 300000 && userID <= 350000) {
            folderName = "300K";
        } else if (userID > 350000 && userID <= 400000) {
            folderName = "350K";
        } else if (userID > 400000 && userID <= 450000) {
            folderName = "400K";
        } else if (userID > 450000 && userID <= 500000) {
            folderName = "450K";
        }
        return folderName;
    }

    public void CreateDirectory(String path, String folderName) {
        File directory = new File(path + "/" + folderName); //for mac use / and for windows use "\\"
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /*
     * Delete directory if it exists and will create new
     */
    public void checkAndCreateDirectory(String path, String folderName) {
        File directory = new File(path + "/" + folderName); //for mac use / and for windows use "\\"
        if (directory.exists()) {
            deleteDir(directory);
        }
        directory.mkdirs();
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete(); // The directory is empty now and can be deleted.
    }

    public File checkAndCreateFile(String fileName) throws IOException {
        File file = new File(fileName);
        /* if (file.exists()) {
         file.delete();
         }*/
        file.createNewFile();
        return file;
    }

    public File CreateFile(String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        return file;
    }

    public List<String> getAllDirectories(String basePath) {
        File file = new File(basePath);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
        //System.out.print(Arrays.toString(directories));
        return Arrays.asList(directories);
    }

    public String readTxtFileAsString(String basePath, String directoryName, String fileName, String extension) throws FileNotFoundException, IOException {

        StringBuilder stringBuilder = new StringBuilder();
        File file;
        BufferedReader reader = null;
        try {
            file = new File(basePath + directoryName + "/" + fileName + extension);
            reader = new BufferedReader(new FileReader(file));
            String line = null;

            if (file.exists()) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        } finally {
            reader.close();
        }
        String a = stringBuilder.substring(0, (stringBuilder.length() - (IOProperties.DATA_SEPERATOR).length()));
        return a;
    }

    public User convertTxtFileToUserObj(String basePath, String directoryName, String fileName, String extension) throws FileNotFoundException, IOException {
        String userPostAsString = readTxtFileAsString(basePath, directoryName, fileName, extension);
        String temp[];
        User user = new User();
        List postList = new ArrayList();
        user.setId(Integer.valueOf(fileName));
        if (userPostAsString.contains(IOProperties.DATA_SEPERATOR)) {
            temp = userPostAsString.split(IOProperties.DATA_SEPERATOR);
        } else {
            temp = new String[1];
            temp[0] = userPostAsString;

        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")
                    || temp[i].length() == 8) {
                temp[i] = temp[i] + "  ";
            }
            Posts posts = new Posts();
            String time = temp[i].substring(0, 8);
            String date = temp[i].substring(9, 19);
            String content = temp[i].substring(20);
            if (time.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                posts.setTime(time);
                posts.setDate(date);
                posts.setContent(content);
                postList.add(posts);
            } else {
            }
        }
        user.setUserPost(postList);
        return user;
    }

    public List getAllFilesInADirectory(String directoryName) {
        List returnList = new ArrayList();
        File folder = new File(directoryName);
        File[] listOfFiles = folder.listFiles();
        for (File listOfFile : listOfFiles) {
            String a = listOfFile.getName();
            returnList.add(a.substring(0, a.length() - 4));
        }
        return returnList;
    }

    /**
     * Return users as an object and returns only those users who has posted
     * more than 60 messages in discussion board
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<User> getAllUsersAsObject() throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        System.out.println(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        List directoryList = ioRW.getAllDirectories(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        List allFiles = new ArrayList();
        List allFilesSize;
        for (Object directoryList1 : directoryList) {
            System.out.println(directoryList1);
            allFilesSize = ioRW.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH + directoryList1);

            for (Object allFilesSize1 : allFilesSize) {
                if (allFilesSize1.toString().contains(".DS_S")) {
                    String dsFilePath = IOProperties.INDIVIDUAL_USER_FILE_PATH + directoryList1 + "/" + allFilesSize1.toString();
                    File dsFile = new File(dsFilePath);
                    dsFile.delete();
                } else {
                    User user = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH,
                            directoryList1.toString(), allFilesSize1.toString(), IOProperties.USER_FILE_EXTENSION);

                    if (user.getUserPost().size() >= 60) {
                        allFiles.add(user);
                    }
                }
            }
        }
        return allFiles;
    }

    /**
     * return the random list of size numberSize
     *
     * @param numberSize
     * @return
     */
    public static List<Integer> returnRandomNumberList(int numberSize) {
        List<Integer> numList = new ArrayList();
        Random rand = new Random();
        int randNum;
        for (int i = 0; i < numberSize; i++) {
            do {
                randNum = rand.nextInt(numberSize);
            } while (numList.contains(randNum));
            numList.add(randNum);
        }
        return numList;
    }

    /**
     * split string into character and returns
     *
     * @param cluster
     * @return
     */
    public List<Integer> returnDigits(String cluster) {
        LinkedList<Integer> digits = new LinkedList<>();
        char[] cArray = cluster.toCharArray();
        for (int i = 0; i < cArray.length; i++) {
            String tempCharacter = String.valueOf(cArray[i]);
            int singleNumber = Integer.valueOf(tempCharacter);
            digits.add(i, singleNumber);
        }
        return digits;
    }

    public static List<User> returnLimitedSortedUser(List<User> userList, int size) {
        Collections.sort(userList, new ReturnSortedUserList());
        List<User> tempUsers;
        tempUsers = userList.subList(0, size);
        return tempUsers;
    }

    public static Float[] getUserTimeProfile(Alias alias) throws ParseException {

        List<String> postTime = alias.getPostTime();
        List<String> postDate = alias.getPostDate();

        int[] intervalOfDay = TimeAnalysis.getIntervalOfDay(postTime);
        int[] hourOfDay = TimeAnalysis.getHourOfDay(postTime);
        int[] monthOfYear = TimeAnalysis.getMonthOfYear(postDate);
        int[] dayOfWeek = TimeAnalysis.getDayOfWeek(postDate);
        int[] typeOfWeekEnd = TimeAnalysis.getTypeOfWeekEnd(postDate);

        int totalSum = postTime.size();

        Float[] normHourOfDay = IOReadWrite.returnNormalizedVector(hourOfDay, totalSum);
        Float[] normIntervalOfDay = IOReadWrite.returnNormalizedVector(intervalOfDay, totalSum);
        Float[] normmonthOfYear = IOReadWrite.returnNormalizedVector(monthOfYear, totalSum);
        Float[] normDayOfWeek = IOReadWrite.returnNormalizedVector(dayOfWeek, totalSum);
        Float[] normTypeOfWeekEnd = IOReadWrite.returnNormalizedVector(typeOfWeekEnd, totalSum);

        Float[] timeFeatureVectorList = IOReadWrite.concatArrays(normHourOfDay,
                normIntervalOfDay, normmonthOfYear, normDayOfWeek, normTypeOfWeekEnd);

        return timeFeatureVectorList;

    }

    public static Float[] createPostFeatureVectorForUser(Alias alias) {
        StylometryAnalysis stylo = new StylometryAnalysis();
        List<String> posts = alias.getPosts();
        int cnt = 0;
        alias.setFeatureVectorPosList(alias.initializeFeatureVectorPostList());
        Float[] styloFeatVector = null;
//        int featSize = alias.getFeatureVectorPosList().get(0).size();
//        System.out.println("UserID: " + alias.getUserID());

        for (String post : posts) {
//            System.out.println("Post -->  " + post);

            String filteredPost = IOReadWrite.filterPost(post);

//            System.out.println("Filtered Post: " + filteredPost);

            List<String> filteredWordInPost = stylo.extractWords(filteredPost);
//            System.out.println("Filtered Word Size: " + filteredWordInPost.size());

            List<String> wordsInPost = stylo.extractWords(post);
            int WordInPostSize = wordsInPost.size();
//            System.out.println("NON Filtered Word Size: " + WordInPostSize);
            int afterFWplaceInFeatureVector = 0;

            ArrayList<Float> functionWord = stylo.countFunctionWords(filteredWordInPost, WordInPostSize);
//            System.out.println("Function Words: " + functionWord);
            afterFWplaceInFeatureVector = functionWord.size();
//            System.out.println("Function Word: " + afterFWplaceInFeatureVector);
            alias.addToFeatureVectorPostList(functionWord, 0, cnt);

            ArrayList<Float> wordLength = stylo.countWordLengths(filteredWordInPost, WordInPostSize);
            alias.addToFeatureVectorPostList(wordLength, afterFWplaceInFeatureVector, cnt);

            int afterWLplaceInFeatureVector = afterFWplaceInFeatureVector + wordLength.size();
//            System.out.println("Word Count: " + wordLength);
            ArrayList<Float> character = stylo.countCharactersAZ(filteredPost, post);
            alias.addToFeatureVectorPostList(character, afterWLplaceInFeatureVector, cnt);

            int placeafterCharInFeatureVector = afterWLplaceInFeatureVector + character.size();
//            System.out.println("Character: " + placeafterCharInFeatureVector);
            ArrayList<Float> specialCharacter = stylo.countSpecialCharacters(filteredPost, post);
            alias.addToFeatureVectorPostList(specialCharacter, placeafterCharInFeatureVector, cnt);

            int placeafterSPCInFeatureVector = placeafterCharInFeatureVector + specialCharacter.size();

//            System.out.println("Emotion Words: " + placeafterSPCInFeatureVector);
            ArrayList<Float> smilies = stylo.countSmiley(wordsInPost, WordInPostSize);
            alias.addToFeatureVectorPostList(smilies, placeafterSPCInFeatureVector, cnt);
//            int size = smilies.size() + placeafterSPCInFeatureVector;
//            System.out.println("FV Size: " + size);

            cnt++;
        }
        ArrayList<ArrayList<Float>> featureVectorList = alias.getFeatureVectorPosList();

        int numberOfPosts = posts.size();
        int nrOfFeatures = featureVectorList.get(0).size();

        List<Float> featureVector = new ArrayList<>(Collections.nCopies(nrOfFeatures, 0.0f));

        // Now we average over all posts to create a single feature vector for each alias
        for (int i = 0; i < nrOfFeatures; i++) {
            float value = 0.0f;
            for (int j = 0; j < numberOfPosts; j++) {
                value += featureVectorList.get(j).get(i);
            }
            value /= numberOfPosts; // normalizing single featureVector count wrt total post
            featureVector.set(i, value);
        }

        styloFeatVector = new Float[featureVector.size()];
        featureVector.toArray(styloFeatVector);

        return styloFeatVector;
    }

    /**
     * return the normalized/percentage of posts
     *
     * @param timeVector
     * @param sum
     * @return
     */
    public static Float[] returnNormalizedVector(int[] timeVector, int sum) {
        Float[] tempTimeVector = new Float[timeVector.length];
        for (int index = 0; index < timeVector.length; index++) {
            float time = timeVector[index];
            float perc = (float) (time / sum);
            int temp = (int) ((perc * 100) + 0.5);

            float itemp = temp;
            tempTimeVector[index] = itemp;
        }
        return tempTimeVector;
    }

    /**
     * concatenate two arrays
     *
     * @param sources
     * @return
     */
    public static Float[] concatArrays(Float[]... sources) {

        int length = 0;
        for (Float[] array : sources) {
            length += array.length;
        }
        Float[] result = new Float[length];
        int destPos = 0;
        for (Float[] array : sources) {
            System.arraycopy(array, 0, result, destPos, array.length);
            destPos += array.length;
        }
        return result;
    }

    /**
     * @Desc There are 6 category for the time {00-04} which is given a number 0
     * {04-08} which is given a number 1 {08-12} which is given a number 2
     * {12-16} which is given a number 3 {16-20} which is given a number 4
     * {20-24} which is given a number 5 where the first hour in the time range
     * is included whereas the second hour in time range is excluded.
     * @param hour
     * @return int
     */
    public static int getTimeCategory(int hour) {
        int category = 6;
        if (hour >= 0 && hour < 4) {
            return 0;
        } else if (hour >= 4 && hour < 8) {
            return 1;
        } else if (hour >= 8 && hour < 12) {
            return 2;
        } else if (hour >= 12 && hour < 16) {
            return 3;
        } else if (hour >= 16 && hour < 20) {
            return 4;
        } else if (hour >= 20 && hour < 24) {
            return 5;
        }
        return category;
    }

    public int getDayOfWeek(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = format1.parse(date);
        c.setTime(dt1);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public int convertDayOfWeek(String date) {

        int dayOfWeek = 0;
        String day = date.substring(0, 3);

        if (null != day) {
            switch (day) {
                case "Sun":
                    dayOfWeek = 1;
                    break;
                case "Mon":
                    dayOfWeek = 2;
                    break;
                case "Tue":
                    dayOfWeek = 3;
                    break;
                case "Wed":
                    dayOfWeek = 4;
                    break;
                case "Thu":
                    dayOfWeek = 5;
                    break;
                case "Fri":
                    dayOfWeek = 6;
                    break;
                case "Sat":
                    dayOfWeek = 7;
                    break;
            }
        }
        return dayOfWeek;
    }

    public int getTypeOfWeek(int day) throws ParseException {

        if (day >= 2 && day <= 6) {
            return 0;
        } else {
            return 1;
        }
    }

    public static int getMonth(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = format1.parse(date);
        c.setTime(dt1);
        int monthOfYear = c.get(Calendar.MONTH);
        return monthOfYear;
    }

    public static String filterPost(String post) {

        /*String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|"
                + "(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern uPattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = uPattern.matcher(post);
//        System.out.println("Main: " + tweet);

        while (m.find()) {
            String urlStr = m.group();
            post = post.replaceAll(Pattern.quote(urlStr), "").trim();
        }

//        System.out.println("Tweet After Filter: " + tweet + "\n");
        /**
         * convert HTML unicode
         */
        post = StringEscapeUtils.unescapeHtml(post);

        post = post.replaceAll("\\<[^>]*>", "");

//        System.out.println("Real Tweet: " + tweet);
        return post;
    }
    
}
