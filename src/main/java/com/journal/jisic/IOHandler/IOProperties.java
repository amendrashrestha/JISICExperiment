package com.journal.jisic.IOHandler;
/**
 *
 * @author ITE
 */
public class IOProperties {

    public static final String YEAR_DATA_FILE_PATH_07 = System.getProperty("user.home") + "/Downloads/BoardDataSet/2007/UsersPostTime/";
    public static final String YEAR_DATA_FILE_PATH_06 = System.getProperty("user.home") + "/Downloads/BoardDataSet/2006/UsersPostTime/";
    public static final String XML_DATA_FILE_PATH = System.getProperty("user.home") + "/Downloads/BoardDataSet/2007/posts";
    public static final String All_ACTIVITY_BASE_PATH = System.getProperty("user.home") + "/Downloads/Test/ActivityData/"; 
//    public static final String All_ACTIVITY_BASE_PATH = "C:\\Users\\ITE\\Downloads\\TestData\\ActivityData\\";
    /**
     * This is the base path for all the created data throughout the experiment.
     * This path has to exist before running the program
     */
    //For creating first activity peak you need to pass the folder name too
//    public static final String INDIVIDUAL_USER_FILE_PATH = "C:\\Users\\ITE\\Downloads\\2008\\UsersPost\\150K\\";
    //For other activity thing
//    public static final String INDIVIDUAL_USER_FILE_PATH = "C:\\Users\\ITE\\Downloads\\2003\\UsersPost\\";
//    public static final String INDIVIDUAL_USER_FILE_PATH = "C:\\Users\\ITE\\Downloads\\2003\\UsersPostWithContent\\";
    public static final String INDIVIDUAL_USER_FILE_PATH = System.getProperty("user.home") + "/Downloads/BoardDataSet/2007/UsersPostWithContent/";
//    public static final String INDIVIDUAL_USER_FILE_PATH = System.getProperty("user.home") + "/Downloads/WekaTestData/UsersPostTime/";
 
    /**
     * Following are the text file and text seperator for reading the data from
     * XML and writing it to text file
     */
    public static final String USER_FILE_EXTENSION = ".txt";
    public static final String DATA_SEPERATOR = "This seperates post";
    
    public static final String SWE_FUNCTION_WORDS_PATH = System.getProperty("user.home") + "/functionWords_Swedish.txt";
    public static final String FUNCTION_WORDS_PATH = System.getProperty("user.home") + "/functionWord/function_words.txt";
}