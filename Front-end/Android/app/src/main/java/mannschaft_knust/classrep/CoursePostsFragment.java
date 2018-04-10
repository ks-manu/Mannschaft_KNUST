package mannschaft_knust.classrep;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CoursePostsFragment extends ListFragment {

    RecyclerView coursePostsRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_course_posts, container, false);

        coursePostsRecyclerView = v.findViewById(R.id.course_post_recycler);
        final CoursePostsAdapter coursePostsAdapter = new CoursePostsAdapter(v.getContext());
        coursePostsRecyclerView.setAdapter(coursePostsAdapter);

        CoursePostsViewModel coursePostsViewModel =
                ViewModelProviders.of(this).get(CoursePostsViewModel.class);
        coursePostsViewModel.getCoursePosts().observe(this, new Observer<List<CoursePost>>() {
            @Override
            public void onChanged(@Nullable List<CoursePost> coursePosts) {
                coursePostsAdapter.updateData(coursePosts);
            }
        });

        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_course_posts, menu);
    }
}
