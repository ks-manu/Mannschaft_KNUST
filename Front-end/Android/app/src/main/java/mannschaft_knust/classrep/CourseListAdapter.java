package mannschaft_knust.classrep;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView courseName;
        TextView courseDetail;

        private ViewHolder(View courseListItemView){
            super(courseListItemView);
            courseName = courseListItemView.findViewById(R.id.course_name);
            courseDetail = courseListItemView.findViewById(R.id.course_detail);
        }
    }

    private List<Course> courseList;

    void updateData(List<Course> courseList){
        this.courseList = courseList;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public CourseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_list_item, parent, false);

        return new CourseListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Course currentCourse = courseList.get(position);
        holder.courseName.setText(currentCourse.courseAndCode);
        holder.courseDetail.setText(currentCourse.programmeAndYear);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(courseList != null)
            return courseList.size();
        else
            return 0;
    }

}
