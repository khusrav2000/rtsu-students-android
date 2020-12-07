package tj.rtsu.students.points;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import tj.rtsu.students.Data;
import tj.rtsu.students.R;
import tj.rtsu.students.data.model.Course;
import tj.rtsu.students.data.model.WeekPoint;

public class CoursePoints extends AppCompatActivity {

    Course course;
    public static final String APP_PREFERENCES = "SkipLoginPhone";
    public static final String APP_LANGUAGE = "language";
    SharedPreferences skipLoginPhone;
    int language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_points);

        skipLoginPhone  = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        language = skipLoginPhone.getInt(APP_LANGUAGE, 0);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.course_points_detail);

        setSupportActionBar(myToolbar);
        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setTitle("");
        //actionBar.setHomeAsUpIndicator(R.drawable.back_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        myToolbar.setTitle("");

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //onBackPressed();// возврат на предыдущий activity
                onBackPressed();
            }
        });





        System.out.println(Data.mInterstitialAd.isLoaded());
        if (Data.mInterstitialAd.isLoaded()){
            Data.mInterstitialAd.show();

        } else {
            if (!Data.mInterstitialAd.isLoading()){
                Data.mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        }



        Data.mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed
                Data.mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        int position = getIntent().getIntExtra("CoursePosition", 0);
        course = Data.getCourses().get(position);
        System.out.println(course.toString());

        TextView ratingOnePoint = findViewById(R.id.rating_one_point);
        LinearLayout ratingOneWeekPoints = findViewById(R.id.rating_one_week_points);
        TextView ratingTwoPoint = findViewById(R.id.rating_two_point);
        LinearLayout ratingTwoWeekPoints = findViewById(R.id.rating_two_week_points);
        TextView examePoint = findViewById(R.id.exam_point);
        TextView courseName = findViewById(R.id.course_name);
        TextView totalPoints = findViewById(R.id.total_points_and_mark);

        if (!Data.getProfile().isUchNew()) {
            ratingOnePoint.setVisibility(View.INVISIBLE);
            ratingTwoPoint.setVisibility(View.INVISIBLE);
        }

        String weekText = "Неделя ";
        if (language == 2){
            courseName.setText(course.getSubjectName().getRuText());
            weekText = "Неделя ";
        } else {
            courseName.setText(course.getSubjectName().getRuText());
            weekText = "Неделя ";
        }

        ratingOnePoint.setText(String.valueOf(course.getFirstRatingPoint()));
        Resources r = this.getResources();

        for (WeekPoint weekPoint : course.getFirstWeekPoints()){
            RelativeLayout relativeLayout = new RelativeLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int margin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, margin,0, margin);
            relativeLayout.setLayoutParams(params);


            TextView weekName = new TextView(this);
            RelativeLayout.LayoutParams weekNameParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            weekNameParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    20,
                    r.getDisplayMetrics()
            );
            weekNameParams.setMarginStart(px);

            weekName.setLayoutParams(weekNameParams);
            if (weekPoint.getWeekNumber() == 9 && Data.getProfile().isUchNew()) {
                weekName.setText(weekText + weekPoint.getWeekNumber() + " ( А/Б )");
            } else {
                weekName.setText(weekText + weekPoint.getWeekNumber());
            }
            weekName.setTextColor(getResources().getColor(R.color.white));
            weekName.setTypeface(ResourcesCompat.getFont(this, R.font.roboto_regular));
            weekName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            relativeLayout.addView(weekName);

            TextView weekPointText = new TextView(this);
            RelativeLayout.LayoutParams weekPointParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            weekPointParams.addRule(RelativeLayout.ALIGN_PARENT_END);

            weekPointParams.setMarginEnd(px);
            weekPointText.setLayoutParams(weekPointParams);
            weekPointText.setText(String.valueOf(weekPoint.getPoint()));
            weekPointText.setTextColor(getResources().getColor(R.color.points_color));
            weekPointText.setTypeface(ResourcesCompat.getFont(this, R.font.roboto_regular));
            weekPointText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            relativeLayout.addView(weekPointText);

            ratingOneWeekPoints.addView(relativeLayout);

            View view = new View(this);
            int lineHeight = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    1,
                    r.getDisplayMetrics()
            );
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    lineHeight
            );
            view.setLayoutParams(viewParams);
            view.setBackgroundColor(getResources().getColor(R.color.line_color));
            view.setAlpha((float) 0.3);
            ratingOneWeekPoints.addView(view);

        }

        ////// old uchproc version /////////

        if (!Data.getProfile().isUchNew()) {
            RelativeLayout relativeLayout = new RelativeLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int margin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, margin,0, margin);
            relativeLayout.setLayoutParams(params);


            TextView weekName = new TextView(this);
            RelativeLayout.LayoutParams weekNameParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            weekNameParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    20,
                    r.getDisplayMetrics()
            );
            weekNameParams.setMarginStart(px);

            weekName.setLayoutParams(weekNameParams);
            weekName.setText("Рейтинговый контроль");
            weekName.setTextColor(getResources().getColor(R.color.white));
            weekName.setTypeface(ResourcesCompat.getFont(this, R.font.roboto_regular));
            weekName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            relativeLayout.addView(weekName);

            TextView weekPointText = new TextView(this);
            RelativeLayout.LayoutParams weekPointParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            weekPointParams.addRule(RelativeLayout.ALIGN_PARENT_END);

            weekPointParams.setMarginEnd(px);
            weekPointText.setLayoutParams(weekPointParams);
            weekPointText.setText(String.valueOf(course.getFirstRatingPoint()));
            weekPointText.setTextColor(getResources().getColor(R.color.points_color));
            weekPointText.setTypeface(ResourcesCompat.getFont(this, R.font.roboto_regular));
            weekPointText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            relativeLayout.addView(weekPointText);

            ratingOneWeekPoints.addView(relativeLayout);

            View view = new View(this);
            int lineHeight = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    1,
                    r.getDisplayMetrics()
            );
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    lineHeight
            );
            view.setLayoutParams(viewParams);
            view.setBackgroundColor(getResources().getColor(R.color.line_color));
            view.setAlpha((float) 0.3);
            ratingOneWeekPoints.addView(view);
        }

        //////////////////

        ratingTwoPoint.setText(String.valueOf(course.getSecondRatingPoint()));
        for (WeekPoint weekPoint : course.getSecondWeekPoints()){
            RelativeLayout relativeLayout = new RelativeLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int margin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, margin,0, margin);
            relativeLayout.setLayoutParams(params);


            TextView weekName = new TextView(this);
            RelativeLayout.LayoutParams weekNameParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            weekNameParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    20,
                    r.getDisplayMetrics()
            );
            weekNameParams.setMarginStart(px);

            weekName.setLayoutParams(weekNameParams);
            if (weekPoint.getWeekNumber() == 9 && Data.getProfile().isUchNew()) {
                weekName.setText(weekText + weekPoint.getWeekNumber() + " ( А/Б )");
            } else {
                weekName.setText(weekText + weekPoint.getWeekNumber());
            }
            weekName.setTextColor(getResources().getColor(R.color.white));
            weekName.setTypeface(ResourcesCompat.getFont(this, R.font.roboto_regular));
            weekName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            relativeLayout.addView(weekName);

            TextView weekPointText = new TextView(this);
            RelativeLayout.LayoutParams weekPointParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            weekPointParams.addRule(RelativeLayout.ALIGN_PARENT_END);

            weekPointParams.setMarginEnd(px);
            weekPointText.setLayoutParams(weekPointParams);
            weekPointText.setText(weekPoint.getPoint() + "");
            weekPointText.setTextColor(getResources().getColor(R.color.points_color));
            weekPointText.setTypeface(ResourcesCompat.getFont(this, R.font.roboto_regular));
            weekPointText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            relativeLayout.addView(weekPointText);

            ratingTwoWeekPoints.addView(relativeLayout);

            View view = new View(this);
            int lineHeight = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    1,
                    r.getDisplayMetrics()
            );
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    lineHeight
            );
            view.setLayoutParams(viewParams);
            view.setBackgroundColor(getResources().getColor(R.color.line_color));
            view.setAlpha((float) 0.3);
            ratingTwoWeekPoints.addView(view);

        }

        ////// old uchproc version /////////

        if (!Data.getProfile().isUchNew()) {
            RelativeLayout relativeLayout = new RelativeLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int margin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, margin,0, margin);
            relativeLayout.setLayoutParams(params);


            TextView weekName = new TextView(this);
            RelativeLayout.LayoutParams weekNameParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            weekNameParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    20,
                    r.getDisplayMetrics()
            );
            weekNameParams.setMarginStart(px);

            weekName.setLayoutParams(weekNameParams);
            weekName.setText("Рейтинговый контроль");
            weekName.setTextColor(getResources().getColor(R.color.white));
            weekName.setTypeface(ResourcesCompat.getFont(this, R.font.roboto_regular));
            weekName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            relativeLayout.addView(weekName);

            TextView weekPointText = new TextView(this);
            RelativeLayout.LayoutParams weekPointParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            weekPointParams.addRule(RelativeLayout.ALIGN_PARENT_END);

            weekPointParams.setMarginEnd(px);
            weekPointText.setLayoutParams(weekPointParams);
            weekPointText.setText(String.valueOf(course.getSecondRatingPoint()));
            weekPointText.setTextColor(getResources().getColor(R.color.points_color));
            weekPointText.setTypeface(ResourcesCompat.getFont(this, R.font.roboto_regular));
            weekPointText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            relativeLayout.addView(weekPointText);

            ratingTwoWeekPoints.addView(relativeLayout);

            View view = new View(this);
            int lineHeight = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    1,
                    r.getDisplayMetrics()
            );
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    lineHeight
            );
            view.setLayoutParams(viewParams);
            view.setBackgroundColor(getResources().getColor(R.color.line_color));
            view.setAlpha((float) 0.3);
            ratingTwoWeekPoints.addView(view);
        }

        //////////////////
        RelativeLayout relativeLayout = findViewById(R.id.administration_point_layout);
        TextView adminPoint = findViewById(R.id.administration_point);
        adminPoint.setText(String.valueOf(course.getAdminPoint()));
        if (Data.getProfile().isUchNew()) {
            relativeLayout.setVisibility(View.GONE);
        }

        examePoint.setText(course.getExamPoint() + " / 100");

        totalPoints.setText(course.getTotalPoints() + " / " + course.getMark());
    }

    private void startTestMetod() {
        TextView textView = new TextView(this);
        findViewById(R.id.rating_one_week_points).setVisibility(View.GONE);
    }
}
