package cn.com.magicabc.ui.bean;

import java.io.Serializable;

/**
 * Created by hellohome on 18/3/5.
 */

public class HomeWorkBean implements Serializable{

    /**
     * grade : RTJ
     * lesson : 1-1
     * starts : 0
     * type : ZY
     */

    private String grade;
    private String lesson;
    private int starts;
    private String type;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public int getStarts() {
        return starts;
    }

    public void setStarts(int starts) {
        this.starts = starts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
