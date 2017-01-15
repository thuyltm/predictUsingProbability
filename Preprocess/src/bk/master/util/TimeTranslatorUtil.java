package bk.master.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bk.master.input.model.Move;

public class TimeTranslatorUtil {
    static private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static private SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public static String convertDateToISODate(String dateInString) {
        Date date;
        try {
            date = formatter.parse(dateInString);
            return isoFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String convertISODateToDate(String ISODate) {
        try {
            Date result = isoFormatter.parse(ISODate);
            return formatter.format(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Date addDate(Date date, int hours, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hours);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }
    public static List<String> getListMiddleTime(String startDateInString, String endDateInString, int hours, int minutes) {
        List<String> result = new ArrayList<String>();
        try {
            Date startDate = formatter.parse(startDateInString);
            result.add(isoFormatter.format(startDate));
            Date endDate = formatter.parse(endDateInString);
            Date midDate = addDate(startDate, hours, minutes);
            while (midDate.after(startDate) && midDate.before(endDate)) {
                result.add(isoFormatter.format(midDate));
                midDate = addDate(midDate, hours, minutes);
            }
            result.add(isoFormatter.format(endDate));
        } catch (Exception e) {
           e.printStackTrace();
        }
        return result;
    }
    public static List<Date> convertISODateToDate(List<String> ISODateList) {
        List<Date> result = new ArrayList<Date>();
        try {
            for (String ISODate: ISODateList) {
                result.add(isoFormatter.parse(ISODate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static List<String> convertDateToISODate(List<Date> dateList) {
        List<String> result = new ArrayList<String>();
        try {
            for (Date date: dateList) {
                result.add(isoFormatter.format(date.getTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Date> filterDiffHour(List<String> ISODateList) {
        List<Date> dateList = convertISODateToDate(ISODateList);
        List<Date> result = new ArrayList<Date>();
        int length = dateList.size();
        int i = 0;
        Date pDate = dateList.get(i);
        result.add(pDate);
        Calendar pCalendar = Calendar.getInstance();
        pCalendar.setTime(pDate);
        int pHour = pCalendar.get(Calendar.HOUR_OF_DAY);
        int j = i+1;
        Date aDate;
        Calendar aCalendar = Calendar.getInstance();
        int aHour;
        while (j<length) {
            aDate = dateList.get(j);
            aCalendar.setTime(aDate);
            aHour = aCalendar.get(Calendar.HOUR_OF_DAY);
            if (aHour!=pHour) {
                result.add(dateList.get(j-1));
                result.add(aDate);
                i=j;
                pDate = dateList.get(i);
                pCalendar.setTime(pDate);
                pHour = pCalendar.get(Calendar.HOUR_OF_DAY);
                j=i+1;
            } else {
                j++;
            }
            if (j==length) {
                result.add(aDate);
            }
        }
        return result;
    }
    public static List<Move> mergeSchedule(String scheduleFile1, String scheduleFile2,
            String placeName1, String placeName2) {
        List<Date> dateList1 = InputUtil.loadInputDate(scheduleFile1);
        List<Date> dateList2 = InputUtil.loadInputDate(scheduleFile2);
        Date date1 = dateList1.get(0);
        Date date2 = dateList2.get(0);
        if (date1.before(date2)) {
            return mergeSchedule(dateList1, dateList2, placeName1, placeName2);
        } else {
            return mergeSchedule(dateList2, dateList1, placeName2, placeName1);
        }
    }
    public static List<Move> mergeSchedule(List<Date> startPlaceTime, List<Date> endPlaceTime,
               String startName, String endName) {
        List<Move> moveList = new ArrayList<Move>();
        int i = 0;
        int j = 0;
        int maxNumber1 = startPlaceTime.size();
        int maxNumber2 = endPlaceTime.size();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Date date1 = null;
        Date date2 = null;
        while (i!=maxNumber1 && j!=maxNumber2) {
            date1 = startPlaceTime.get(i);
            calendar1.setTime(date1);
            date2 = endPlaceTime.get(j);
            calendar2.setTime(date2);
            if (date1.before(date2)) {
               Date date3 = null;
               if (i<maxNumber1-1) {
                   date3 = startPlaceTime.get(i+1);
               }
               if ((i==maxNumber1-1 && date3==null) || (date3!= null && date3.after(date2))) {
                   moveList.add(new Move(startName, endName, date1, date2));
               }
               i++;
            } else {
               Date date3 = null;
               if (j<maxNumber2-1) {
                   date3 = endPlaceTime.get(j+1);
               }
               if ((j==maxNumber2-1 && date3==null) || (date3!= null && date3.after(date1))) {
                   moveList.add(new Move(endName, startName, date2, date1));
               }
               j++;
            }
        }
        return moveList;
    }
    public static String covertToReadableFormat(long millis) {
        try {
           long hours = TimeUnit.MILLISECONDS.toHours(millis);
           long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
           long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
           if (hours > 0L) {
               return String.format("%02d:%02d:%02d", hours, minutes, seconds);
           }
           if (minutes > 0L) {
               return String.format("%02d:%02d", minutes, seconds);
           }
           if (seconds > 0L) {
               return String.format("%02d", seconds);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Long substractDate(Date date1, Date date2) {
        if (date2.after(date1)) {
            return Math.abs(date2.getTime()-date1.getTime());
        } else {
            return Math.abs(date1.getTime()-date2.getTime());
        }
    }
    public static long convertToMillionSeconds(String time) {
        String[] data = time.split(":");
        return (Integer.valueOf(data[0])*60+Integer.valueOf(data[1]))*1000;
    }
}
