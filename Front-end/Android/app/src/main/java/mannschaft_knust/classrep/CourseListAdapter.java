package mannschaft_knust.classrep;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder>
        implements Filterable{

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
    private List<Course> filteredCourseList;

    void updateData(List<Course> courseList){
        filteredCourseList = courseList;
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
        Course currentCourse = filteredCourseList.get(position);
        holder.courseName.setText(currentCourse.courseAndCode);
        holder.courseDetail.setText(currentCourse.programmeAndYear);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(filteredCourseList != null)
            return filteredCourseList.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter(){
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence charSequence){
                String query = charSequence.toString();

                List<Course> filtered = new ArrayList<>();

                if(query.isEmpty())
                    filtered = courseList;
                else
                    for(int i =0;i<courseList.size();i++){
                    if (courseList.get(i).courseAndCode.toLowerCase().contains(
                            query.toLowerCase()))
                        filtered.add(courseList.get(i));
                    }
                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results){
                filteredCourseList = (List<Course>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
