package tj.rtsu.students.points;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import tj.rtsu.students.Data;
import tj.rtsu.students.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */

public class LessonFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    Dialog semesters;
    RecyclerView semestersRecyclerView;
    Dialog language;

    public static final String APP_PREFERENCES = "SkipLoginPhone";
    public static final String APP_LANGUAGE = "language";
    SharedPreferences skipLoginPhone;
    int lang;
    ImageView selectLanguage;

    ProgressBar progressLoadCourses;
    TextView notCoursesInformation;

    public LessonFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public LessonFragment newInstance(int columnCount) {
        LessonFragment fragment = new LessonFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_list, container, false);



        skipLoginPhone  = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        lang = skipLoginPhone.getInt(APP_LANGUAGE, 0);
        selectLanguage = view.findViewById(R.id.select_language);
        progressLoadCourses = view.findViewById(R.id.load_courses);
        notCoursesInformation = view.findViewById(R.id.not_courses_information);

        if (lang == 1){
            selectLanguage.setImageDrawable(getActivity().getDrawable(R.drawable.ic_tajikistan));
        } else if (lang == 2){
            selectLanguage.setImageDrawable(getActivity().getDrawable(R.drawable.ic_russia));
        } else {
            selectLanguage.setImageDrawable(getActivity().getDrawable(R.drawable.ic_russia));
        }
        selectLanguage.setVisibility(View.GONE);
        view.findViewById(R.id.button_select_semester).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semesters.show();
            }
        });

        selectLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language.show();
            }
        });

        // Set the adapter


        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        semesters = new Dialog(getContext());
        semesters.setContentView(R.layout.fragment_semesters_list);
        semesters.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.select_semester_background));
        semestersRecyclerView = semesters.findViewById(R.id.semester_list);
        semestersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        semesters.findViewById(R.id.select_semester_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semesters.dismiss();
            }
        });

        language = new Dialog(getContext());
        language.setContentView(R.layout.fragment_language);
        language.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.select_semester_background));

        language.findViewById(R.id.tajik_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage(1);
                selectLanguage.setImageDrawable(getActivity().getDrawable(R.drawable.ic_tajikistan));
                language.dismiss();
            }
        });

        language.findViewById(R.id.russian_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage(2);
                selectLanguage.setImageDrawable(getActivity().getDrawable(R.drawable.ic_russia));
                language.dismiss();
            }
        });

        language.findViewById(R.id.select_language_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language.dismiss();
            }
        });

        setSemestersAdapter();
        setCourseAdapter();

        return view;
    }

    private void setLanguage(int lang) {
        SharedPreferences.Editor editor = skipLoginPhone.edit();
        editor.putInt(APP_LANGUAGE, lang);
        this.lang = lang;
        editor.apply();
        if (lang == 1){
            Resources res = this.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(new Locale("ru".toLowerCase())); // API 17+ only.
            // Use conf.locale = new Locale(...) if targeting lower versions
            res.updateConfiguration(conf, dm);
        } else if (lang == 2){
            Resources res = this.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(new Locale("ru".toLowerCase())); // API 17+ only.
            // Use conf.locale = new Locale(...) if targeting lower versions
            res.updateConfiguration(conf, dm);
        }
        mListener.refreshActivity();

    }

    public void setSemestersAdapter() {
        if (Data.getSemesters() != null) {
            //System.out.println(Data.getSemesters().toString());
        }
        semestersRecyclerView.setAdapter(new MySemestersRecyclerViewAdapter(getActivity(), Data.getSemesters(), mListener));
    }

    public void setCourseAdapter() {
        progressLoadCourses.setVisibility(View.GONE);
        if (Data.getCourses() != null && Data.getCourses().size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new MylessonRecyclerViewAdapter(lang, Data.getCourses(), mListener));
            notCoursesInformation.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            notCoursesInformation.setVisibility(View.VISIBLE);
        }
    }

    public void dismissSelectSemester(){
        semesters.dismiss();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(int position);
        void onSelectSemester(int semesterId, int position);
        void refreshActivity();
    }

}
