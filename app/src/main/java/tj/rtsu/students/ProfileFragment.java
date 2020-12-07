package tj.rtsu.students;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import tj.rtsu.students.data.model.Profile;
import tj.rtsu.students.login.LoginActivity;


public class ProfileFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    public static final String APP_PREFERENCES = "SkipLoginPhone";
    public static final String APP_LANGUAGE = "language";
    public static final String APP_TOKEN = "getToken";
    SharedPreferences skipLoginPhone;
    int lang;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        skipLoginPhone  = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        lang = skipLoginPhone.getInt(APP_LANGUAGE, 0);

        Profile profile = Data.getProfile();
        TextView fullName = view.findViewById(R.id.full_name);
        TextView recordBookNumber = view.findViewById(R.id.record_book_number);
        TextView faculty = view.findViewById(R.id.faculty);
        TextView specialty = view.findViewById(R.id.specialty_and_code);
        TextView trainingFrom = view.findViewById(R.id.training_form);
        TextView trainingLevel = view.findViewById(R.id.training_level);
        TextView courseAndTrainingPeriod = view.findViewById(R.id.course_and_training_period);
        TextView group = view.findViewById(R.id.group);
        TextView yearEntrance = view.findViewById(R.id.year_entrance);
        TextView logout = view.findViewById(R.id.logout);
        if (lang == 2){
            fullName.setText(profile.getFullName().getRuText());
            faculty.setText(profile.getFaculty().getRuText());
            specialty.setText(profile.getSpecialty().getRuText() + " (" + profile.getCodeSpecialty() + ")");
        } else {
            fullName.setText(profile.getFullName().getRuText());
            faculty.setText(profile.getFaculty().getRuText());
            specialty.setText(profile.getSpecialty().getRuText() + " (" + profile.getCodeSpecialty() + ")");
        }

        recordBookNumber.setText(profile.getRecordbookNumber());
        trainingFrom.setText(profile.getTrainingForm());
        trainingLevel.setText(profile.getTrainingLevel());
        courseAndTrainingPeriod.setText(profile.getCourse() + " / " + profile.getTrainingPeriod());
        group.setText(profile.getGroup());
        yearEntrance.setText(profile.getYearUniversityEntrance());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin();
            }
        });

        return view;
    }

    private void startLogin() {
        SharedPreferences.Editor editor = skipLoginPhone.edit();
        editor.putString(APP_TOKEN, "");
        editor.apply();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        mListener.finishActivity();
        //onDetach();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void finishActivity();
    }
}
