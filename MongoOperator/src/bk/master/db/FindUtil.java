package bk.master.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bk.master.input.ExportUtil;
import bk.master.input.InputUtil;
import bk.master.input.TimeTranslatorUtil;
import bk.master.input.model.Location;
import bk.master.input.model.Move;

public class FindUtil {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dayFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public static void getRuningRoute(String inputFile, String deviceId) {
        List<Move> dateList = InputUtil.loadCsvDate(inputFile);
        for (int i = 0; i < dateList.size(); i++) {
            Move mv = dateList.get(i);
            long duration = TimeTranslatorUtil.substractDate(mv.getOrginalDestTime(), mv.getOrginalDepartTime());
            DBUtil.findLocationDeviceByTime(TimeTranslatorUtil.convertDateToISODate(mv.getDepartTime()),
                    TimeTranslatorUtil.convertDateToISODate(mv.getDestTime()), deviceId,
                    "location_"+i+"_"+TimeTranslatorUtil.covertToReadableFormat(duration)+".json",
                    "route_"+i+"_"+TimeTranslatorUtil.covertToReadableFormat(duration)+".txt");
        }
    }

    public static void getFinishRoute(int startDay, int endDay) {
        List<Location> bxASLngLat = InputUtil.loadInputLocation("benxeAnSuong.txt");
        List<Location> bxCCLngLat = InputUtil.loadInputLocation("benxeCuChi.txt");
        for (int i=startDay;i<=endDay;i++) {
            int year = 2012;
            int month = 10;
            int day = i;
            int startHour = 4;
            int endHour = 23;
            Calendar cal1 = Calendar.getInstance();
            cal1.setTimeInMillis(0);
            cal1.set(year, month, day, startHour, 0, 0);
            Date date1 = cal1.getTime();
            Calendar cal2 = Calendar.getInstance();
            cal2.setTimeInMillis(0);
            cal2.set(year, month, day, endHour, 0, 0);
            Date date2 = cal2.getTime();
            try {
                String queryStartDate = formatter.format(date1);
                String queryEndDate = formatter.format(date2);
                String queryDate = dayFormatter.format(date1);
                boolean existInAS = DBUtil.findTimeDeviceWithinLocation(bxASLngLat, TimeTranslatorUtil.convertDateToISODate(queryStartDate),
                        TimeTranslatorUtil.convertDateToISODate(queryEndDate),
                        "bxAS_"+queryDate+".json", "dateAS_"+queryDate+".txt");
                boolean existInCC = DBUtil.findTimeDeviceWithinLocation(bxCCLngLat, TimeTranslatorUtil.convertDateToISODate(queryStartDate),
                        TimeTranslatorUtil.convertDateToISODate(queryEndDate),
                        "bxCC_"+queryDate+".json", "dateCC_"+queryDate+".txt");
                if (existInAS && existInCC) {
                    List<Move> result = TimeTranslatorUtil.mergeSchedule("dateAS_"+queryDate+".txt","dateCC_"+queryDate+".txt",
                            "An Suong", "Cu Chi");
                    ExportUtil.exportToScheduleFile(result, "schedule_"+queryDate+".csv");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
