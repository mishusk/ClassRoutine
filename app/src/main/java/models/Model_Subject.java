package models;

/**
 * Created by mishu on 7/28/2016.
 */
public class Model_Subject {
    private int id;
    private String subjectName;

    public Model_Subject() {
    }

    public Model_Subject(int id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }

    public Model_Subject(String subjectName) {
        this.subjectName = subjectName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
