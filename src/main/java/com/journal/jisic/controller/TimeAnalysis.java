package com.journal.jisic.controller;

import com.journal.jisic.IOHandler.IOReadWrite;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author amendrashrestha
 */
public class TimeAnalysis {

    public static int[] getIntervalOfDay(List<String> postTime) {
        int[] intervalOfDay = new int[6];

        for (String time : postTime) {
            String[] splitTime = time.split(":");
            int hour = Integer.parseInt(splitTime[0]);
            int timeCategory = IOReadWrite.getTimeCategory(hour);
            if (timeCategory < 6) {
                intervalOfDay[timeCategory] = intervalOfDay[timeCategory] + 1;
            }
        }
        return intervalOfDay;
    }

    public static int[] getHourOfDay(List<String> postTime) {
        int[] hourOfDay = new int[24];

        for (String time : postTime) {
            String[] splitTime = time.split(":");
            int hour = Integer.parseInt(splitTime[0]);
            hourOfDay[hour] = hourOfDay[hour] + 1;
        }
        return hourOfDay;
    }
    
    public static int[] getMonthOfYear(List<String> postDate) throws ParseException {
        int[] monthOfYear = new int[12];

        for (String posts : postDate) {
            int MonthOfYear = IOReadWrite.getMonth(posts);
            monthOfYear[MonthOfYear] = monthOfYear[MonthOfYear] + 1;
        }
        return monthOfYear;
    }
    
    public static int[] getDayOfWeek(List<String> postDate) throws ParseException {
        int[] dayOfWeek = new int[7];
        IOReadWrite io = new IOReadWrite();

        for (String date : postDate) {
            int DayOfWeek = io.getDayOfWeek(date) - 1;
            dayOfWeek[DayOfWeek] = dayOfWeek[DayOfWeek] + 1;
        }
        return dayOfWeek;
    }

    public static int[] getTypeOfWeekEnd(List<String> postDate) throws ParseException {
        int[] typeOfWeekEnd = new int[2];
        IOReadWrite io = new IOReadWrite();
        
        for(String date : postDate){
           int dayOfWeek = io.getDayOfWeek(date);
            int typeOfWeek = io.getTypeOfWeek(dayOfWeek);
            typeOfWeekEnd[typeOfWeek] = typeOfWeekEnd[typeOfWeek] + 1;
        }
        return typeOfWeekEnd;
    }
}
