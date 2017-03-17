package models;

/**
 * Created by mishu on 7/28/2016.
 */
public class Model_Routine {
    private int id_rtn;

    private String subjectName_rtn;
    private int dayName_rtn;
    private String room_rtn;
    private String time_rtn;
    private int classHour_rtn;

    private int alarmTime_rtn;

    public Model_Routine() {

    }

    //with id and day name only
    public Model_Routine(int id_rtn, int dayName_rtn) {
        this.id_rtn = id_rtn;
        this.dayName_rtn = dayName_rtn;
    }

    // with id_rtn and without alarm time
    public Model_Routine(int id_rtn, String subjectName_rtn, int dayName_rtn, String room_rtn, String time_rtn, int classHour_rtn) {
        this.id_rtn = id_rtn;
        this.subjectName_rtn = subjectName_rtn;
        this.dayName_rtn = dayName_rtn;
        this.room_rtn = room_rtn;
        this.time_rtn = time_rtn;
        this.classHour_rtn = classHour_rtn;
    }

    // with id_rtn and alarm time
    public Model_Routine(int id_rtn, String subjectName_rtn, int dayName_rtn, String room_rtn, String time_rtn, int classHour_rtn, int alarmTime_rtn) {
        this.id_rtn = id_rtn;
        this.subjectName_rtn = subjectName_rtn;
        this.dayName_rtn = dayName_rtn;
        this.room_rtn = room_rtn;
        this.time_rtn = time_rtn;
        this.classHour_rtn = classHour_rtn;
        this.alarmTime_rtn = alarmTime_rtn;
    }

    // without alarm_time and id_rtn
    public Model_Routine(String subjectName_rtn, int dayName_rtn, String room_rtn, String time_rtn, int classHour_rtn) {
        this.subjectName_rtn = subjectName_rtn;
        this.dayName_rtn = dayName_rtn;
        this.room_rtn = room_rtn;
        this.time_rtn = time_rtn;
        this.classHour_rtn = classHour_rtn;
    }

    //with alarm time and without id_rtn
    public Model_Routine(String subjectName_rtn, int dayName_rtn, String room_rtn, String time_rtn, int classHour_rtn, int alarmTime_rtn) {
        this.subjectName_rtn = subjectName_rtn;
        this.dayName_rtn = dayName_rtn;
        this.room_rtn = room_rtn;
        this.time_rtn = time_rtn;
        this.classHour_rtn = classHour_rtn;
        this.alarmTime_rtn = alarmTime_rtn;
    }

    public int getId() {
        return id_rtn;
    }

    public void setId(int id_rtn) {
        this.id_rtn = id_rtn;
    }

    public String getRoutinSubjctName() {
        return subjectName_rtn;
    }

//    public void setRoutinSubjctName(String subjectName_rtn) {
//        this.subjectName_rtn = subjectName_rtn;
//    }

    public int getRoutinDay() {
        return dayName_rtn;
    }

//    public void setRoutinDay(String dayName_rtn) {
//        this.dayName_rtn = dayName_rtn;
//    }

    public String getRoutineRoom() {
        return room_rtn;
    }

//    public void setRoutineRoom(String room_rtn) {
//        this.room_rtn = room_rtn;
//    }

    public String getRoutinTime() {
        return time_rtn;
    }

//    public void setRoutinTime(String time_rtn) {
//        this.time_rtn = time_rtn;
//    }

    public int getRoutinDuration() {
        return classHour_rtn;
    }

//    public void setRoutinDuration(int classHour_rtn) {
//        this.classHour_rtn = classHour_rtn;
//    }

    public int getRoutineAlarmTime() {
        return alarmTime_rtn;
    }

    public void setRoutineAlarmTime(int alarmTime_rtn) {
        this.alarmTime_rtn = alarmTime_rtn;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}