package org.redrock.kebiao.util;

import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redrock.kebiao.been.Data;
import org.redrock.kebiao.been.Ics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class KebiaoUtil {
    public static String jsonToics(JSONObject jsonObject) {
        final String begin = "BEGIN:VCALENDAR\n" +
                "CALSCALE:GREGORIAN\n" +
                "VERSION:2.0\n" +
                "METHOD:PUBLISH\n" +
                "X-WR-CALNAME:RedRock Hucang\n" +
                "X-WR-TIMEZONE:Asia/Shanghai\n" +
                "X-APPLE-CALENDAR-COLOR:#9933CC\n";
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        StringBuilder sb = new StringBuilder();
        sb.append(begin);


        //从得到的jsonArray中取出每一门课的信息
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            Gson gson = new Gson();
            Data data = gson.fromJson(json.toString(), Data.class);

            //组装成每门课每一节的信息
            for (int j = 0; j < data.getWeek().length; j++) {

                //10月9号第五周周一的时间戳
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(new Date(1507478400000L));

                //day为在周几的课，j为从第五周为第0周的第几周
                //循环增加day天得到是周几的课
                //循环增加j周得到是第几周的课
                int day = getDayNum(data.getDay());
                for (int k = 0; k < day; k++) {
                    gc.add(5, 1);
                }
                for (int k = 0; k < j; k++) {
                    gc.add(3, 1);
                }

                //拼接DTstart和DTend
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                StringBuilder start = new StringBuilder();
                StringBuilder end = new StringBuilder();
                start.append(df.format(gc.getTime()).toString()).append("T");
                end.append(start.toString());
                String lesson[] = getTime(data.getLesson());
                start.append(lesson[0]);
                end.append(lesson[1]);
                Ics ics = new Ics();
                ics.setUid(start.toString());
                ics.setDtStart(start.toString());
                ics.setDtEnd(end.toString());
                ics.setSummary(data.getCourse());
                ics.setLocation("@" + data.getClassroom());
                ics.setDescription(data.getTeacher() + "," + data.getRawWeek() + "," + data.getType());
                sb.append(ics.toString());
            }
        }
        sb.append("END:VCALENDAR\n");
        return sb.toString();
    }

    public static int getDayNum(String day) {
        int num = -1;
        if ("星期一".equals(day)) {
            num = 0;
        } else if ("星期二".equals(day)) {
            num = 1;
        } else if ("星期三".equals(day)) {
            num = 2;
        } else if ("星期四".equals(day)) {
            num = 3;
        } else if ("星期五".equals(day)) {
            num = 4;
        } else if ("星期六".equals(day)) {
            num = 5;
        } else if ("星期天".equals(day)) {
            num = 6;
        }
        return num;
    }

    public static String[] getTime(String time) {
        String[] lesson = new String[2];
        if ("一二节".equals(time)) {
            lesson[0] = "080000";
            lesson[1] = "094000";
        } else if ("三四节".equals(time)) {
            lesson[0] = "101500";
            lesson[1] = "115500";
        } else if ("五六节".equals(time)) {
            lesson[0] = "140000";
            lesson[1] = "154000";
        } else if ("七八节".equals(time)) {
            lesson[0] = "161500";
            lesson[1] = "175500";
        } else if ("九十节".equals(time)) {
            lesson[0] = "190000";
            lesson[1] = "204000";
        }
        return lesson;
    }
}
