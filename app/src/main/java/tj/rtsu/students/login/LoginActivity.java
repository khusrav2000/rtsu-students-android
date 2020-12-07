package tj.rtsu.students.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import tj.rtsu.students.Data;
import tj.rtsu.students.MainActivity;
import tj.rtsu.students.R;
import tj.rtsu.students.data.ConnectivityHelper;
import tj.rtsu.students.data.NetworkClient;
import tj.rtsu.students.data.apis.Studetns;
import tj.rtsu.students.data.model.Course;
import tj.rtsu.students.data.model.Message;
import tj.rtsu.students.data.model.Profile;
import tj.rtsu.students.data.model.Semester;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    ProgressBar loadingProgressBar;
    public static final String APP_PREFERENCES = "SkipLoginPhone";
    public static final String APP_TOKEN = "getToken";
    public static final String APP_LANGUAGE = "language";
    SharedPreferences skipLoginPhone;
    String token = "";
    int language;
    TextView tryAgain;

    @Override
    public void onCreate(Bundle savedInstanceState) {

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
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);
        tryAgain = findViewById(R.id.try_again);




        if (token != ""){
            findViewById(R.id.login_layout).setVisibility(View.GONE);
            checkLogin();
        }

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkLogin();
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Здесь все не впорядке.))
                checkLogin();
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
                tryAgain.setVisibility(View.GONE);
            }
        });
    }

    private void checkLogin() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        String login = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Studetns studetns = retrofit.create(Studetns.class);
        System.out.println("TOKE = " + token);
        Call call = studetns.login(token, login, password);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    Message message = (Message) response.body();
                    startLogin(message.getMessage());
                } else {
                    loadingProgressBar.setVisibility(View.GONE);
                    showLoginFailed(R.string.login_failed);
                    findViewById(R.id.login_layout).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                loadingProgressBar.setVisibility(View.GONE);
                if (!ConnectivityHelper.isConnectedToNetwork(getCtx())){
                    Toast.makeText(getApplicationContext(), getText(R.string.internet_not_available), Toast.LENGTH_LONG).show();
                } else {
                    showLoginFailed(R.string.server_not_work);
                }

                tryAgain.setVisibility(View.VISIBLE);

            }
        });

    }

    private void startLogin(String token1) {
        SharedPreferences.Editor editor = skipLoginPhone.edit();
        editor.putString(APP_TOKEN, token1);
        editor.apply();
        token = token1;
        (new LoadData()).execute();
    }

    private void startMainActivity() {
        Data.mInterstitialAd = new InterstitialAd(this);
        //Data.mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); // TEST
        Data.mInterstitialAd.setAdUnitId("ca-app-pub-5583392303902725/1922103524");
        Data.mInterstitialAd.loadAd(new AdRequest.Builder().build());

        loadingProgressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    private void updateUiWithUser() {
        String welcome = getString(R.string.welcome) + Data.getProfile().getFullName().getTjText();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private class LoadData extends AsyncTask<String, Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... param) {
            Retrofit retrofit = NetworkClient.getRetrofitClient();
            Studetns studetns = retrofit.create(Studetns.class);
            Call call = studetns.getStudentProfile(token);
            try {
                Response response = call.execute();
                if (response.isSuccessful()){
                    System.out.println("Profile =========");
                    System.out.println(((Profile) response.body()).toString());
                    Data.setProfile((Profile) response.body());
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            call = studetns.getSemesters(token);
            try{
                Response response = call.execute();
                if (response.isSuccessful()){
                    List<Semester> semestersList = (List<Semester>) response.body();
                    for (Semester semesters : semestersList){
                        semesters.setActive(false);
                    }
                    semestersList.get(0).setActive(true);
                    Data.setSemesters(semestersList);
                } else {
                    return false;
                }
            } catch (IOException e){
                e.printStackTrace();
                return false;
            }

            call = studetns.getCoursesBySemester(token, Data.getSemesters().get(0).getSemesterId());
            try{
                Response response = call.execute();
                if (response.isSuccessful()){
                    List<Course> courseList = (List<Course>) response.body();
                    Data.setCourses(courseList);
                } else {
                    return false;
                }
            } catch (IOException e){
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                updateUiWithUser();
                startMainActivity();
            } else {
                loadingProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), getText(R.string.error_load_data), Toast.LENGTH_LONG).show();
                tryAgain.setVisibility(View.VISIBLE);
            }
        }
    }

    public Context getCtx(){
        return  this;
    }

}
