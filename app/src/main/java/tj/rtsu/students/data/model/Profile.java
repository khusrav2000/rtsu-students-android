package tj.rtsu.students.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("RecordBookNumber")
    @Expose
    String recordbookNumber;

    @SerializedName("FullName")
    @Expose
    Language fullName;

    @SerializedName("Faculty")
    @Expose
    Language faculty;

    @SerializedName("Specialty")
    @Expose
    Language specialty;

    @SerializedName("CodeSpecialty")
    @Expose
    String codeSpecialty;

    @SerializedName("TrainingForm")
    @Expose
    String trainingForm;

    @SerializedName("TrainingLevel")
    @Expose
    String trainingLevel;

    @SerializedName("Course")
    @Expose
    int course;

    @SerializedName("Group")
    @Expose
    String group;

    @SerializedName("YearUniversityEntrance")
    @Expose
    String yearUniversityEntrance;

    @SerializedName("TrainingPeriod")
    @Expose
    int trainingPeriod;

    @SerializedName("IsUchNew")
    @Expose
    boolean isUchNew;

    public Profile(String recordbookNumber, Language fullName, Language faculty, Language specialty, String codeSpecialty, String trainingForm, String trainingLevel, int course, String group, String yearUniversityEntrance, int trainingPeriod, boolean isUchNew) {
        this.recordbookNumber = recordbookNumber;
        this.fullName = fullName;
        this.faculty = faculty;
        this.specialty = specialty;
        this.codeSpecialty = codeSpecialty;
        this.trainingForm = trainingForm;
        this.trainingLevel = trainingLevel;
        this.course = course;
        this.group = group;
        this.yearUniversityEntrance = yearUniversityEntrance;
        this.trainingPeriod = trainingPeriod;
        this.isUchNew = isUchNew;
    }

    public String getRecordbookNumber() {
        return recordbookNumber;
    }

    public void setRecordbookNumber(String recordbookNumber) {
        this.recordbookNumber = recordbookNumber;
    }

    public Language getFullName() {
        return fullName;
    }

    public void setFullName(Language fullName) {
        this.fullName = fullName;
    }

    public Language getFaculty() {
        return faculty;
    }

    public void setFaculty(Language faculty) {
        this.faculty = faculty;
    }

    public Language getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Language specialty) {
        this.specialty = specialty;
    }

    public String getCodeSpecialty() {
        return codeSpecialty;
    }

    public void setCodeSpecialty(String codeSpecialty) {
        this.codeSpecialty = codeSpecialty;
    }

    public String getTrainingForm() {
        return trainingForm;
    }

    public void setTrainingForm(String trainingForm) {
        this.trainingForm = trainingForm;
    }

    public String getTrainingLevel() {
        return trainingLevel;
    }

    public void setTrainingLevel(String trainingLevel) {
        this.trainingLevel = trainingLevel;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getYearUniversityEntrance() {
        return yearUniversityEntrance;
    }

    public void setYearUniversityEntrance(String yearUniversityEntrance) {
        this.yearUniversityEntrance = yearUniversityEntrance;
    }

    public int getTrainingPeriod() {
        return trainingPeriod;
    }

    public void setTrainingPeriod(int trainingPeriod) {
        this.trainingPeriod = trainingPeriod;
    }

    public boolean isUchNew() {
        return isUchNew;
    }

    public void setUchNew(boolean uchNew) {
        isUchNew = uchNew;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "recordbookNumber='" + recordbookNumber + '\'' +
                ", fullName=" + fullName +
                ", faculty=" + faculty +
                ", specialty=" + specialty +
                ", codeSpecialty='" + codeSpecialty + '\'' +
                ", trainingForm='" + trainingForm + '\'' +
                ", trainingLevel='" + trainingLevel + '\'' +
                ", course=" + course +
                ", group='" + group + '\'' +
                ", yearUniversityEntrance='" + yearUniversityEntrance + '\'' +
                ", trainingPeriod=" + trainingPeriod +
                ", isUchNew=" + isUchNew +
                '}';
    }
}
