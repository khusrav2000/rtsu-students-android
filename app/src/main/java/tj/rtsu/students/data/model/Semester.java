package tj.rtsu.students.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Semester {

    @SerializedName("AcademicYear")
    @Expose
    String semesterName;

    @SerializedName("ID")
    @Expose
    int semesterId;

    boolean isActive;

    public Semester(String semesterName, int semesterId, boolean isActive) {
        this.semesterName = semesterName;
        this.semesterId = semesterId;
        this.isActive = isActive;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Semester{" +
                "semesterName='" + semesterName + '\'' +
                ", semesterId=" + semesterId +
                ", isActive=" + isActive +
                '}';
    }
}
