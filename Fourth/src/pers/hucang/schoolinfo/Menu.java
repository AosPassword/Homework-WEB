package pers.hucang.schoolinfo;

import java.util.*;

/**
 * @author MashiroC
 * @Date 2017/11/22
 * @Content 查询菜单类
 */
public class Menu {

    /**
     * 主菜单
     */
    public Menu() {
        System.out.println("----信息加载完成----");
        Scanner check = new Scanner(System.in);
        String choice;

        while (true) {
            System.out.println("Please make a choice");
            System.out.println("1---信息菜单");
            System.out.println("2---查找学生");
            System.out.println("0---退出");

            choice = check.next();
            if (!isNumeric(choice))
                continue;
            if (Objects.equals(choice, "2")) {
                checkStudent();
            }
            if (Objects.equals(choice, "1")) {
                checkInfo();
            }
            if (Objects.equals(choice, "0")) {
                System.exit(-1);
            }

        }
    }

    /**
     * 一层一层查询
     * 中的
     * 信息展示菜单
     */
    private static void checkInfo() {
        //key--储存菜单中的选项
        //value--储存菜单要展示的信息
        Map<Integer, String> menu = new TreeMap<>();
        int count;//读取菜单时用来给菜单的编号赋值 && 菜单的最大长度
        int menuNum = 0;//菜单深度
        String collage = null;//选定的学院
        String major = null;//选定的专业
        String cclass = null;//选定的班级
        String student = null;//选定的学生

        String choice;//你的选择
        Scanner scan = new Scanner(System.in);

        /*
        菜单分五层 0层是学院Set 1层是专业Set 2层是班级Set 3层是班级里的学生Set 4层是选定的学生的信息
        每次根据菜单深度返回一个Set 然后把Set作为value 从1到菜单中选项总数为key
        根据当前菜单深度不同 来给不同的变量赋值
         */
        out:
        while (true) {
            if (menuNum == 4) {
                System.out.println(Data.getData().get(collage).get(major).get(cclass).get(student));
                System.out.println("是否继续查询？(Y/y)");
                String last;
                last = scan.next();
                while (true) {
                    if ("Y".equals(last) || "y".equals(last)) {
                        menuNum--;
                        continue out;
                    } else {
                        return;
                    }
                }
            }
            //读取当前菜单并显示
            count = 1;
            for (String str : getSet(menuNum, collage, major, cclass)) {
                menu.put(count, str);
                count++;
            }
            menu.put(0, "返回上一级");
            for (Integer num : menu.keySet()) {
                System.out.println(num + "---" + menu.get(num));
            }

            //选择
            choice = scan.next();
            if (!isNumeric(choice) || Integer.parseInt(choice) > count - 1) {//输入的不为数字或大于菜单中选项最大
                System.out.println("请输入正确的数字！");
            } else if (Integer.parseInt(choice) == 0) {//输入零返回上一级
                if (menuNum == 0) {
                    return;
                } else {
                    menuNum--;
                }
            } else {
                //根据当前菜单深度设置内容
                switch (menuNum) {
                    case 0:
                        collage = menu.get(Integer.parseInt(choice));
                        break;
                    case 1:
                        major = menu.get(Integer.parseInt(choice));
                        break;
                    case 2:
                        cclass = menu.get(Integer.parseInt(choice));
                        break;
                    case 3:
                        student = menu.get(Integer.parseInt(choice));
                        break;
                }
                menuNum++;
            }
            menu.clear();
        }
    }

    /**
     * 一层一层查询
     * 中的
     * 判断要展示哪些信息
     *
     * @param menuNum 菜单深度
     * @param major   专业
     * @param cclass  班级
     * @return 要展示的信息
     */
    private static Set<String> getSet(int menuNum, String collage, String major, String cclass) {
        switch (menuNum) {    //   <学院，<专业,<班级<学号，学生>>>>
            case 0:
                return Data.getData().keySet();
            case 1:
                return Data.getData().get(collage).keySet();
            case 2:
                return Data.getData().get(collage).get(major).keySet();
            case 3:
                return Data.getData().get(collage).get(major).get(cclass).keySet();
        }
        return Data.getData().keySet();
    }

    /**
     * 按学号查询学生
     * 中的
     * 输入学号和输出信息
     */
    private static void checkStudent() {
        System.out.println("请输入你查找的学生的学号");
        Scanner str = new Scanner(System.in);
        String id = str.next();
        if (!isNumeric(id)) {
            System.out.println("请不要输入字母");
        } else {
            Student stu = lookupStudent(id);
            if ("无".equals(stu.getName()))
                System.out.println("查无此人");
            else {
                System.out.println(stu);
                System.out.println("是否继续查询？(Y/y)");
                String isContinue = str.next();
                if ("Y".equals(isContinue) || "y".equals(isContinue))
                    checkStudent();
            }
        }
    }

    /**
     * 按照学号查询学生
     * 中的
     * 查询
     *
     * @param id 学号
     * @return 查到的学生 如果为null 则查询不到
     */
    private static Student lookupStudent(String id) {
        //   <学院，<专业,<班级<学号，学生>>>>
        for (String collage:Data.getData().keySet()) {

        for (String major : Data.getData().get(collage).keySet()
                ) {
            for (String cclass : Data.getData().get(collage).get(major).keySet()
                    ) {
                Set<String> studentsName = Data.getData().get(collage).get(major).get(cclass).keySet();
                for (String stu : studentsName
                        ) {
                    if (Objects.equals(id, Data.getData().get(collage).get(major).get(cclass).get(stu).getStuid()))
                        return Data.getData().get(collage).get(major).get(cclass).get(stu);
                }
            }
        }
        }
        return new Student();
    }

    /**
     * 判断是否为数字
     *
     * @param str 要判断的数字
     * @return 是否为数字
     */
    private static boolean isNumeric(String str) {
        return str.matches("[0-9]+");
    }

}
