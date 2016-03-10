package cc.haoduoyu.demoapp.rxjava;

import java.util.ArrayList;

/**
 * Created by XP on 2016/3/10.
 */
public class Student {

    public int id;//学号
    public String name;//姓名

    public ArrayList<Course> courses;//学生选修的课程

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }
}
