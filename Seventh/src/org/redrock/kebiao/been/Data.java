package org.redrock.kebiao.been;

import com.google.gson.annotations.SerializedName;

@lombok.Data
public class Data {
    @SerializedName("hash_day")
    private int hashDay;
    @SerializedName("hash_lesson")
    private int hashLesson;
    @SerializedName("begin_lesson")
    private int beginLesson;
    private String day;
    private String lesson;
    private String course;
    private String teacher;
    private String classroom;
    private String rawWeek;
    private String weekModel;
    private int weekBegin;
    private int weekEnd;
    private String type;
    private int period;
    @SerializedName("_id")
    private String id;
    private int[] week;
}
