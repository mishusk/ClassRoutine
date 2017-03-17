package models;

/**
 * Created by mishu on 3/12/2017.
 */

public class Model_Exam {
    private int id;
    private String subject_name;
    private String date;
    private String time;
    private String duration;

    public Model_Exam() {
    }

    public Model_Exam(int id, String subject_name, String date, String time, String duration) {
        this.id = id;
        this.subject_name = subject_name;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public Model_Exam(String subject_name, String date, String time, String duration) {
        this.subject_name = subject_name;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
