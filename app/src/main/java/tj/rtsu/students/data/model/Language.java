package tj.rtsu.students.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Language {

    @SerializedName("TJ")
    @Expose
    String TjText;

    @SerializedName("RU")
    @Expose
    String RuText;

    public Language(String tjText, String ruText) {
        TjText = tjText;
        RuText = ruText;
    }

    public String getTjText() {
        return TjText;
    }

    public void setTjText(String tjText) {
        TjText = tjText;
    }

    public String getRuText() {
        return RuText;
    }

    public void setRuText(String ruText) {
        RuText = ruText;
    }

    @Override
    public String toString() {
        return "Language{" +
                "TjText='" + TjText + '\'' +
                ", RuText='" + RuText + '\'' +
                '}';
    }
}
