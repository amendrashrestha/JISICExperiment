package com.journal.jisic.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ITE
 */
public class User {

    private int id;
    private List<Posts> userPost;
    private String type;
    private List<String> postTime;
    private List<String> postDate;
    /*
     * This variable contains the total number of posts, posted in an individual time frame,
     * The information about the avaiable time frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedTimeVector;
    /*
     * This variable contains the total number of posts, posted in an individual day,
     * The information about the avaiable day frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedDayVector;
    /*
     * This variable contains the total number of posts, posted in an individual month,
     * The information about the avaiable day frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedMonthVector;

    /*
     * This variable contains the total number of posts, posted in an individual month,
     * The information about the avaiable day frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedDayOfMonthVector;

    /*
     * This variable contains the total number of posts posted in an each hour of a day,
     * The information about the avaiable day frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedHourOfDayVector;

    /*
     * This variable contains the total number of posts posted in an each hour of a day,
     * The information about the avaiable day frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedTypeOfWeekVector;

    /**
     */
    public User() {
        this.type = UserType.UNDEFINED;

        this.classifiedTimeVector = new int[6];
        this.classifiedDayVector = new int[7];
        this.classifiedMonthVector = new int[12];
        this.classifiedHourOfDayVector = new int[24];
        this.classifiedDayOfMonthVector = new int[31];
        this.classifiedTypeOfWeekVector = new int[2];

    }

    /*public User(int[] classifiedTimeVector, int[] classifiedDayVector, int[] classifiedMonthVector, 
     int[] classifiedHourOfDayVector, int[] classifiedDayOfMonthVector, int[] classifiedTypeOfWeekVector){
        
     }*/
    public List<Posts> getUserPost() {
        return userPost;
    }

    /**
     * @param userPost the userPost to set
     */
    public void setUserPost(List<Posts> userPost) {
        this.userPost = userPost;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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

    /**
     * @return the classifiedTimeVector
     */
    public int[] getClassifiedTimeVector() {
        return classifiedTimeVector;
    }

    /**
     * @param classifiedTimeVector the classifiedTimeVector to set
     */
    public void setClassifiedTimeVector(int[] classifiedTimeVector) {
        this.classifiedTimeVector = classifiedTimeVector;
    }

    /**
     * @return the classifiedDayVector
     */
    public int[] getClassifiedDayVector() {
        return classifiedDayVector;
    }

    /**
     * @param classifiedDayVector the classifiedDayVector to set
     */
    public void setClassifiedDayVector(int[] classifiedDayVector) {
        this.classifiedDayVector = classifiedDayVector;
    }

    /**
     * @return the classifiedMonthVector
     */
    public int[] getClassifiedMonthVector() {
        return classifiedMonthVector;
    }

    /**
     * @param classifiedMonthVector the classifiedMonthVector to set
     */
    public void setClassifiedMonthVector(int[] classifiedMonthVector) {
        this.classifiedMonthVector = classifiedMonthVector;
    }

    /**
     * @return the classifiedDayOfMonthVector
     */
    public int[] getClassifiedDayOfMonthVector() {
        return classifiedDayOfMonthVector;
    }

    /**
     * @param classifiedDayOfMonthVector the classifiedDayOfMonthVector to set
     */
    public void setClassifiedDayOfMonthVector(int[] classifiedDayOfMonthVector) {
        this.classifiedDayOfMonthVector = classifiedDayOfMonthVector;
    }

    /**
     * @return the classifiedHourOfDayVector
     */
    public int[] getClassifiedHourOfDayVector() {
        return classifiedHourOfDayVector;
    }

    /**
     * @param classifiedHourOfDayVector the classifiedHourOfDayVector to set
     */
    public void setClassifiedHourOfDayVector(int[] classifiedHourOfDayVector) {
        this.classifiedHourOfDayVector = classifiedHourOfDayVector;
    }

    /**
     * @return the classifiedTypeOfWeekVector
     */
    public int[] getClassifiedTypeOfWeekVector() {
        return classifiedTypeOfWeekVector;
    }

    /**
     * @param classifiedTypeOfWeekVector the classifiedTypeOfWeekVector to set
     */
    public void setClassifiedTypeOfWeekVector(int[] classifiedTypeOfWeekVector) {
        this.classifiedTypeOfWeekVector = classifiedTypeOfWeekVector;
    }

    /**
     * @return the postTime
     */
    public List<String> getPostTime() {
        return postTime;
    }

    /**
     * @param postTime the postTime to set
     */
    public void setPostTime(List<String> postTime) {
        this.postTime = postTime;
    }

    /**
     * @return the postDate
     */
    public List<String> getPostDate() {
        return postDate;
    }

    /**
     * @param postDate the postDate to set
     */
    public void setPostDate(List<String> postDate) {
        this.postDate = postDate;
    }

    /**
     * @param user
     * @return user
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedTimeVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified time range which has been described in getTimeCategory(int
     * hours) in this class.
     */
    public User setCategorizedTypeOfWeekToUser(User user) throws ParseException {
        int[] userTimeVector = user.getClassifiedTypeOfWeekVector();
        for (Posts posts : user.getUserPost()) {
            String date = posts.getDate();
            int dayOfWeek = getDayOfWeek(date);
            int typeOfWeek = getTypeOfWeek(dayOfWeek);

            userTimeVector[typeOfWeek] = userTimeVector[typeOfWeek] + 1;
        }
        user.setClassifiedTypeOfWeekVector(userTimeVector);

        return user;
    }

    /**
     * @param user
     * @return user
     * @Desc This function populates the "classifiedTimeVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified time range which has been described in getTimeCategory(int
     * hours) in this class.
     */
    public User setCategorizedHourOfDayToUser(User user) {
        int[] userTimeVector = user.getClassifiedHourOfDayVector();
        for (Posts posts : user.getUserPost()) {
            Timestamp ts = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()).concat(posts.getTime()));
            int timeCategory = ts.getHours();
            userTimeVector[timeCategory] = userTimeVector[timeCategory] + 1;
        }
        user.setClassifiedHourOfDayVector(userTimeVector);

        return user;
    }

    /**
     * @param userList
     * @Desc This function populates the "classifiedTimeVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified time range which has been described in getTimeCategory(int
     * hours) in this class.
     * @return List<User>
     */
    public List<User> setCategorizedTimeToUser(List<User> userList) {
        ClusterCommons cc = new ClusterCommons();
        for (User user : userList) {
            int[] userTimeVector = user.getClassifiedTimeVector();
            for (Posts posts : user.getUserPost()) {
                Timestamp ts = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()).concat(posts.getTime()));
                int timeCategory = cc.getTimeCategory(ts.getHours());
                if (timeCategory < 6) {
                    userTimeVector[timeCategory] = userTimeVector[timeCategory] + 1;
                }
            }
            user.setClassifiedTimeVector(userTimeVector);
        }
        return userList;
    }

    /**
     * @param user
     * @Desc This function populates the "classifiedTimeVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified time range which has been described in getTimeCategory(int
     * hours) in this class.
     * @return List<User>
     */
    public User setCategorizedTimeToUser(User user) {
        ClusterCommons cc = new ClusterCommons();
        int[] userTimeVector = user.getClassifiedTimeVector();
        for (Posts posts : user.getUserPost()) {
            Timestamp ts = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()).concat(posts.getTime()));
            int timeCategory = cc.getTimeCategory(ts.getHours());
            if (timeCategory < 6) {
                userTimeVector[timeCategory] = userTimeVector[timeCategory] + 1;
            }
        }
        user.setClassifiedTimeVector(userTimeVector);

        return user;
    }

    /**
     * @param userList
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public List<User> setCategorizedDayToUser(List<User> userList) throws ParseException {
        for (User user : userList) {
            int[] userDayVector = user.getClassifiedDayVector();
            for (Posts posts : user.getUserPost()) {
                String date = posts.getDate();
                int DayOfWeek = getDayOfWeek(date) - 1;
//                System.out.println("Day Of Week " + DayOfWeek);
                userDayVector[DayOfWeek] = userDayVector[DayOfWeek] + 1;
            }
            user.setClassifiedDayVector(userDayVector);
        }
        return userList;
    }

    /**
     * @param user
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public User setCategorizedDayToUser(User user) throws ParseException {
        int[] userDayVector = user.getClassifiedDayVector();
        for (Posts posts : user.getUserPost()) {
            String date = posts.getDate();
            int DayOfWeek = getDayOfWeek(date) - 1;
//                System.out.println("Day Of Week " + DayOfWeek);
            userDayVector[DayOfWeek] = userDayVector[DayOfWeek] + 1;
        }
        user.setClassifiedDayVector(userDayVector);

        return user;
    }

    /**
     * @param userList
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public List<User> setCategorizedMonthToUser(List<User> userList) throws ParseException {
        for (User user : userList) {
            int[] userMonthVector = user.getClassifiedMonthVector();
            for (Posts posts : user.getUserPost()) {
                String date = posts.getDate();
                int MonthOfYear = getMonthOfYear(date);
//                System.out.println("Day Of Week " + DayOfWeek);
                userMonthVector[MonthOfYear] = userMonthVector[MonthOfYear] + 1;
            }
            user.setClassifiedMonthVector(userMonthVector);
        }
        return userList;
    }


    /**
     * @param user
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public User setCategorizedMonthToUser(User user) throws ParseException {
        int[] userMonthVector = user.getClassifiedMonthVector();
        for (Posts posts : user.getUserPost()) {
            String date = posts.getDate();
            int MonthOfYear = getMonthOfYear(date);
//                System.out.println("Day Of Week " + DayOfWeek);
            userMonthVector[MonthOfYear] = userMonthVector[MonthOfYear] + 1;
        }
        user.setClassifiedMonthVector(userMonthVector);

        return user;
    }

    /**
     * @param userList
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public List<User> setCategorizedDayOfMonthToUser(List<User> userList) throws ParseException {
        for (User user : userList) {
            int[] userDayOfMonthVector = user.getClassifiedDayOfMonthVector();
            for (Posts posts : user.getUserPost()) {
                String date = posts.getDate();
                int DayOfMonth = getDayOfMonth(date) - 1;
//                System.out.println("Day Of Week " + DayOfMonth);
                userDayOfMonthVector[DayOfMonth] = userDayOfMonthVector[DayOfMonth] + 1;
            }
            user.setClassifiedDayOfMonthVector(userDayOfMonthVector);
        }
        return userList;
    }

    /**
     * @param user
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public User setCategorizedDayOfMonthToUser(User user) throws ParseException {
        int[] userDayOfMonthVector = user.getClassifiedDayOfMonthVector();
        for (Posts posts : user.getUserPost()) {
            String date = posts.getDate();
            int DayOfMonth = getDayOfMonth(date) - 1;
            userDayOfMonthVector[DayOfMonth] = userDayOfMonthVector[DayOfMonth] + 1;
        }
        user.setClassifiedDayOfMonthVector(userDayOfMonthVector);

        return user;
    }

    private int getDayOfWeek(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = format1.parse(date);
        c.setTime(dt1);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public int getMonthOfYear(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = format1.parse(date);
        c.setTime(dt1);
        int monthOfYear = c.get(Calendar.MONTH);
        return monthOfYear;
    }

    private int getDayOfMonth(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = format1.parse(date);
        c.setTime(dt1);
        int monthOfYear = c.get(Calendar.DAY_OF_MONTH);
        return monthOfYear;
    }

    private int getTypeOfWeek(int day) throws ParseException {

        if (day >= 2 && day <= 6) {
            return 0;
        } else {
            return 1;
        }
    }
}
