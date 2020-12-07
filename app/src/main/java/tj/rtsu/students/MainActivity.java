package tj.rtsu.students;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import tj.rtsu.students.data.ConnectivityHelper;
import tj.rtsu.students.data.NetworkClient;
import tj.rtsu.students.data.apis.Studetns;
import tj.rtsu.students.data.model.Course;
import tj.rtsu.students.data.model.Semester;
import tj.rtsu.students.points.CoursePoints;
import tj.rtsu.students.points.LessonFragment;

public class MainActivity extends AppCompatActivity implements LessonFragment.OnListFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener{

    FrameLayout mainFragment;
    LessonFragment lessonFragment;
    ProfileFragment profileFragment = ProfileFragment.newInstance();

    public static final String APP_PREFERENCES = "SkipLoginPhone";
    public static final String APP_TOKEN = "getToken";
    public static final String APP_LANGUAGE = "language";
    SharedPreferences skipLoginPhone;
    String token = "";
    int language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        skipLoginPhone  = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        token    = skipLoginPhone.getString(APP_TOKEN, "");
        language = skipLoginPhone.getInt(APP_LANGUAGE, 0);

        if (language == 2){
            Resources res = this.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(new Locale("ru".toLowerCase())); // API 17+ only.
            // Use conf.locale = new Locale(...) if targeting lower versions
            res.updateConfiguration(conf, dm);
        } else {
            Resources res = this.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(new Locale("ru".toLowerCase())); // API 17+ only.
            // Use conf.locale = new Locale(...) if targeting lower versions
            res.updateConfiguration(conf, dm);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFragment = findViewById(R.id.main_fragment);

        downloadAndSetCoursesData();

        lessonFragment = new LessonFragment();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (menuItem.getItemId() == R.id.navigation_points){
                    transaction.replace(R.id.main_fragment, lessonFragment);
                    transaction.commit();
                }
                if (menuItem.getItemId() == R.id.navigation_profile){
                    transaction.replace(R.id.main_fragment, profileFragment);
                    transaction.commit();
                }
                return true;
            }
        });

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment, lessonFragment);
        ft.commit();


        /*Profile profile = new Profile("2018010294","Ашурзода Хусрав Абдусамад", "механикаю математика",
                "Математикаи амали", "31030302", "Рузона", "бакалавр",
                2, "ТАС", "25/12/2019", 4);
        Data.setProfile(profile);*/


    }


    private void downloadAndSetCoursesData() {

        //Data.setCours(coursesList);

    }


    @Override
    public void onListFragmentInteraction(int position) {
        System.out.println("CLICK");
        if (!ConnectivityHelper.isConnectedToNetwork(this)){
            Toast.makeText(getApplicationContext(), getText(R.string.internet_not_available), Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, CoursePoints.class);
            intent.putExtra("CoursePosition", position);
            startActivity(intent);
        }
    }

    @Override
    public void onSelectSemester(int semesterId, int position) {
        findViewById(R.id.not_courses_information).setVisibility(View.GONE);
        findViewById(R.id.load_courses).setVisibility(View.VISIBLE);
        findViewById(R.id.list).setVisibility(View.GONE);

        downloadAndSetCoursesData();
        List<Semester> semestersList = Data.getSemesters();
        for (Semester semesters : semestersList){
            semesters.setActive(false);
        }
        semestersList.get(position).setActive(true);
        Data.setSemesters(semestersList);

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Studetns studetns = retrofit.create(Studetns.class);
        Call call = studetns.getCoursesBySemester(token, semesterId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    List<Course> courseList = (List<Course>) response.body();
                    Data.setCourses(courseList);
                    updateCoursesAdapter();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        lessonFragment.setSemestersAdapter();
        lessonFragment.dismissSelectSemester();
    }

    @Override
    public void refreshActivity() {
        recreate();
    }

    private void updateCoursesAdapter() {
        lessonFragment.setCourseAdapter();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void finishActivity() {
        finish();
    }
}
