package tj.rtsu.students.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeekPoint {
    @SerializedName("WeekNumber")
    @Expose
    int weekNumber;

    @SerializedName("Point")
    @Expose
    double point;

    @SerializedName("MaxPoint")
    @Expose
    double maxPoint;

    @SerializedName("IsСurrentWeek")
    @Expose
    boolean isCurrentWeek;

    public WeekPoint(int weekNumber, double point, double maxPoint, boolean isCurrentWeek) {
        this.weekNumber = weekNumber;
        this.point = point;
        this.maxPoint = maxPoint;
        this.isCurrentWeek = isCurrentWeek;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public double getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(double maxPoint) {
        this.maxPoint = maxPoint;
    }

    public boolean isCurrentWeek() {
        return isCurrentWeek;
    }

    public void setCurrentWeek(boolean currentWeek) {
        isCurrentWeek = currentWeek;
    }

    @Override
    public String toString() {
        return "WeekPoint{" +
                "weekNumber=" + weekNumber +
                ", point=" + point +
                ", maxPoint=" + maxPoint +
                ", isCurrentWeek=" + isCurrentWeek +
                '}';
    }
}
