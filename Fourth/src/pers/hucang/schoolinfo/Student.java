package pers.hucang.schoolinfo;

import java.util.*;

/**
 * @author MashiroC
 * @Date 2017/11/22
 * @Content 学生类
 */
public class Student {
    private String name;
    private String stuid;
    private String sex;
    private String clazz;
    private String majorId;
    private String major;
    private String collage;
    private String enterYear;
    private String atSchool;

    Student() {
        this.name = "无";
    }

    Student(String name, ArrayList<String> information) {
        this.name = name;
        this.stuid = information.get(1);
        this.sex = information.get(2);
        this.clazz = information.get(3);
        this.majorId = information.get(4);
        this.major = information.get(5);
        this.collage = information.get(6);
        this.enterYear = information.get(7);
        this.atSchool = information.get(8);
    }

    public String getName() {
        return this.name;
    }

    public String getAtSchool() {
        return atSchool;
    }

    public String getClazz() {
        return clazz;
    }

    public String getCollage() {
        return collage;
    }

    public String getEnterYear() {
        return enterYear;
    }

    public String getMajor() {
        return major;
    }

    public String getMajorId() {
        return majorId;
    }

    public String getSex() {
        return sex;
    }

    public String getStuid() {
        return stuid;
    }

    @Override
    public String toString() {
        return "姓名：" + this.name + "\n" +
                "学号：" + this.stuid + "\n" +
                "性别：" + this.sex + "\n" +
                "班级：" + this.clazz + "\n" +
                "专业号：" + this.majorId + "\n" +
                "专业：" + this.major + "\n" +
                "学院：" + this.collage + "\n" +
                "入校日期：" + this.enterYear + "\n" +
                "在校状态：" + this.atSchool + "\n";
    }
}
