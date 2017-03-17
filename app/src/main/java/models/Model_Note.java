package models;

/**
 * Created by mishu on 3/12/2017.
 */

public class Model_Note {
    private int id;
    private String noteText;

    public Model_Note() {
    }

    public Model_Note(int id, String noteText){
        this.id = id;
        this.noteText = noteText;
    }
    public Model_Note(String noteText) {
        this.noteText = noteText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return noteText;
    }

    public void setText(String noteText) {
        this.noteText = noteText;
    }

    @Override
    public String toString() {
        return getNote();
    }
}
