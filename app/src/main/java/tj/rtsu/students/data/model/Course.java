package tj.rtsu.students.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Course {

    @SerializedName("SubjectID")
    @Expose
    int subjectId;

    @SerializedName("SubjectName")
    @Expose
    Language subjectName;

    @SerializedName("TeacherName")
    @Expose
    Language teacherName;

    @SerializedName("FirstRatingWeeks")
    @Expose
    List<WeekPoint> firstWeekPoints;

    @SerializedName("FirstRatingPoint")
    @Expose
    double firstRatingPoint;

    @SerializedName("SecondRatingWeeks")
    @Expose
    List<WeekPoint> secondWeekPoints;

    @SerializedName("SecondRatingPoint")
    @Expose
    double secondRatingPoint;

    @SerializedName("ExamPoint")
    @Expose
    double examPoint;

    @SerializedName("TotalPoint")
    @Expose
    double totalPoints;

    @SerializedName("Mark")
    @Expose
    String mark;

    @SerializedName("AdminPoint")
    @Expose
    double adminPoint;

    public Course(int subjectId, Language sunjectName, Language teacherName, List<WeekPoint> firstWeekPoints, double firstRatingPoint, List<WeekPoint> secondWeekPoints, double secondRatingPoint, double examPoint, double totalPoints, String mark, double adminPoint) {
        this.subjectId = subjectId;
        this.subjectName = sunjectName;
        this.teacherName = teacherName;
        this.firstWeekPoints = firstWeekPoints;
        this.firstRatingPoint = firstRatingPoint;
        this.secondWeekPoints = secondWeekPoints;
        this.secondRatingPoint = secondRatingPoint;
        this.examPoint = examPoint;
        this.totalPoints = totalPoints;
        this.mark = mark;
        this.adminPoint = adminPoint;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public Language getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(Language subjectName) {
        this.subjectName = subjectName;
    }

    public Language getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(Language teacherName) {
        this.teacherName = teacherName;
    }

    public List<WeekPoint> getFirstWeekPoints() {
        return firstWeekPoints;
    }

    public void setFirstWeekPoints(List<WeekPoint> firstWeekPoints) {
        this.firstWeekPoints = firstWeekPoints;
    }

    public double getFirstRatingPoint() {
        return firstRatingPoint;
    }

    public void setFirstRatingPoint(double firstRatingPoint) {
        this.firstRatingPoint = firstRatingPoint;
    }

    public List<WeekPoint> getSecondWeekPoints() {
        return secondWeekPoints;
    }

    public void setSecondWeekPoints(List<WeekPoint> secondWeekPoints) {
        this.secondWeekPoints = secondWeekPoints;
    }

    public double getSecondRatingPoint() {
        return secondRatingPoint;
    }

    public void setSecondRatingPoint(double secondRatingPoint) {
        this.secondRatingPoint = secondRatingPoint;
    }

    public double getExamPoint() {
        return examPoint;
    }

    public void setExamPoint(double examPoint) {
        this.examPoint = examPoint;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public double getAdminPoint() {
        return adminPoint;
    }

    public void setAdminPoint(double adminPoint) {
        this.adminPoint = adminPoint;
    }

    @Override
    public String toString() {
        return "Course{" +
                "subjectId=" + subjectId +
                ", subjectName=" + subjectName +
                ", teacherName=" + teacherName +
                ", firstWeekPoints=" + firstWeekPoints +
                ", firstRatingPoint=" + firstRatingPoint +
                ", secondWeekPoints=" + secondWeekPoints +
                ", secondRatingPoint=" + secondRatingPoint +
                ", examPoint=" + examPoint +
                ", totalPoints=" + totalPoints +
                ", mark='" + mark + '\'' +
                ", adminPoint=" + adminPoint +
                '}';
    }
}