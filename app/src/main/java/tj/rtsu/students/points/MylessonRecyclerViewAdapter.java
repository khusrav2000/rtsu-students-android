package tj.rtsu.students.points;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tj.rtsu.students.R;
import tj.rtsu.students.data.model.Course;
import tj.rtsu.students.points.LessonFragment.OnListFragmentInteractionListener;


public class MylessonRecyclerViewAdapter extends RecyclerView.Adapter<MylessonRecyclerViewAdapter.ViewHolder> {

    private final List<Course> mValues;
    private final OnListFragmentInteractionListener mListener;


    int lang = 0;

    public MylessonRecyclerViewAdapter(int lang,List<Course> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.lang = lang;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lesson, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        if (lang == 2) {
            holder.courseName.setText(mValues.get(position).getSubjectName().getRuText());
            holder.teacherName.setText(mValues.get(position).getTeacherName().getRuText());
        } else {
            holder.courseName.setText(mValues.get(position).getSubjectName().getRuText());
            holder.teacherName.setText(mValues.get(position).getTeacherName().getRuText());
        }


        holder.pointsProgress.setProgress(Integer.valueOf((int) holder.mItem.getTotalPoints()));

        holder.totalPoints.setText(String.valueOf(holder.mItem.getTotalPoints()));




        holder.mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    holder.courseLayout.setAlpha((float) 0.4);
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    holder.courseLayout.setAlpha((float) 1);
                    mListener.onListFragmentInteraction(position);
                } else {
                    holder.courseLayout.setAlpha((float) 1);
                }



                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView courseName;
        public final TextView teacherName;
        public Course mItem;
        private ProgressBar pointsProgress;
        private TextView totalPoints;
        private RelativeLayout courseLayout;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            courseName = view.findViewById(R.id.lesson_name);
            teacherName = view.findViewById(R.id.teacher_name);
            pointsProgress = view.findViewById(R.id.progress_points);
            totalPoints = view.findViewById(R.id.total_points);
            courseLayout = view.findViewById(R.id.main_layout_course);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + courseName.getText() + "'";
        }
    }

}
