package pers.hucang.schoolinfo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author MashiroC
 * @Date 2017/11/22
 * @Content 数据存储类
 */
public class Data extends Observable {

    //   <学院，<专业,<班级<姓名，学生>>>>
    private static Map<String, Map<String, Map<String, Map<String, Student>>>> data = new HashMap<>();
    private static int loadNum = 0;

    /**
     * @throws IOException
     * @throws InterruptedException
     */
    public Data() throws IOException, InterruptedException, ClassNotFoundException, SQLException {
        Date date1 = new Date();
        System.out.println(date1);
        int flag = new DatabaseTools().initializationDatabase();

        System.out.println("----加载信息----");

        ArrayList<String> collages = tools.getCollageList();

        //这里用的是java自带的观察者类
        Completion ob = new Completion();
        this.addObserver(ob);

        //用多线程来爬取 一个学院一个线程 加快速度
        if (flag != 0) {

            ExecutorService executorService = Executors.newFixedThreadPool(5);
            Load load = new Load(this);
            for (String collage : collages) {
                executorService.execute(new Load(collage));
            }
        }else{
            new Menu();
        }
    }


    public void notifyThread() {
        loadNum++;
        super.setChanged();
        super.notifyObservers(loadNum);
    }


    public static Map<String, Map<String, Map<String, Map<String, Student>>>> getData() {
        return data;
    }

}

/**
 * @Content 信息爬取类
 */
class Load implements Runnable {
    private String collage;
    private static Data data;
    private static Map<String, List<String>> majorToClass;//存储专业到班级的Map
    private static Map<String, ArrayList> collageToMajor;

    public Load(Data data) {
        Load.data = data;
        try {
            majorToClass = tools.getMajorToClassList();
            collageToMajor = tools.getCollageToMajorMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Load(String collage) {
        this.collage = collage;
    }

    public void run() {

        List<String> majors = collageToMajor.get(collage);

        for (String major : majors) {

            Map<String, Map<String, Student>> theClass = new TreeMap<>();
            List<String> classes = majorToClass.get(major);//获得该线程要爬取的专业内的班级List

            for (String cclass : classes) {//遍历该专业的班级

                Set<String> students = null;//获得该班的所有学生信息

                try {
                    students = tools.getClassList(cclass).keySet();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (students != null) {

                    for (String s : students) {//遍历该班级的学生

                        ArrayList<String> info = null;

                        try {
                            info = tools.getClassList(cclass).get(s);//获得该学生的信息
                            new DatabaseTools().add(new Student(s, info));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
        data.notifyThread();

    }
}

/**
 * @Content 监听者类
 */
class Completion implements Observer {

    public void update(Observable obj, Object arg) {
        if ((int) arg == 16) {
            System.out.println(new Date());
            new Menu();
        }
    }

}
