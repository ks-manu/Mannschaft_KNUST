package mannschaft_knust.classrep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.TextView;

import java.util.Date;
import java.util.List;

class CoursePostsAdapter extends RecyclerView.Adapter<CoursePostsAdapter.ViewHolder> {
=======
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class CoursePostsAdapter extends RecyclerView.Adapter<CoursePostsAdapter.ViewHolder>
implements Filterable{
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView senderNameView;
        private TextView timeSentView;
        private TextView notSentIndicator;
        private TextView messageView;
        private TextView attachmentIndicator;
<<<<<<< HEAD
        private Button voteButton;
=======
        private ImageButton voteButton;
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
        private TextView totalVotes;

        private ViewHolder(ViewGroup coursePostItemView){
            super(coursePostItemView);
            senderNameView = coursePostItemView.findViewById(R.id.sender_name);
            timeSentView = coursePostItemView.findViewById(R.id.time_sent);
            notSentIndicator = coursePostItemView.findViewById(R.id.not_sent_indicator);
            messageView = coursePostItemView.findViewById(R.id.meessage_view);
            attachmentIndicator = coursePostItemView.findViewById(R.id.attachment_indicator);
            voteButton = coursePostItemView.findViewById(R.id.vote_button);
            totalVotes = coursePostItemView.findViewById(R.id.total_vote);
        }
    }

    private List<CoursePost> coursePosts;
<<<<<<< HEAD
    private Context mContext;

    public CoursePostsAdapter(Context mContext){
        this.mContext = mContext;
=======
    private List<CoursePost> courseFilteredPosts = new ArrayList<>();
    private List<CoursePost> filteredCoursePosts;
    private String currentCourse;
    private Context recyclerContext;

    public CoursePostsAdapter(Context recyclerContext, String currentCourse){
        this.recyclerContext = recyclerContext;
        this.currentCourse = currentCourse;
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
    }

    public void updateData(List<CoursePost> coursePosts){
        this.coursePosts = coursePosts;
<<<<<<< HEAD
=======
        //clear before re inserting data
        courseFilteredPosts.clear();
        for(int i =0;i<coursePosts.size();i++) {
            if (coursePosts.get(i).postID.toLowerCase().contains(currentCourse.toLowerCase()))
                courseFilteredPosts.add(coursePosts.get(i));
        }
        filteredCoursePosts =  courseFilteredPosts;
        notifyDataSetChanged();
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
    }

    @Override
    @NonNull
    public CoursePostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {
        // create a new view
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_post_item, parent, false);

        return new CoursePostsAdapter.ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final CoursePostsAdapter.ViewHolder holder, int position) {
<<<<<<< HEAD
        CoursePost currentPost = coursePosts.get(position);
=======
        CoursePost currentPost = filteredCoursePosts.get(position);
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
        long elapsedTime;
        String stringBuilder;

        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //write sender name
        holder.senderNameView.setText(currentPost.sentBy);

        //write message
        holder.messageView.setText(currentPost.message);

        //write time sent or show not sent
        if (currentPost.timeSent != null){
            holder.notSentIndicator.setVisibility(View.GONE);
            elapsedTime = System.currentTimeMillis() - currentPost.timeSent.getTime();

            //timestamp format "yyyy-mm-dd hh:mm:ss.fffffffff"
            if((elapsedTime/3.154e+10)>1)//more than a year
                holder.timeSentView.setText(currentPost.timeSent.toString().substring(0,9));
            else if((elapsedTime/2.628e+9)>1)//more than a month
                holder.timeSentView.setText(currentPost.timeSent.toString().substring(5,9));
            else if((elapsedTime/6.048e+8)>1)//more than a week
                holder.timeSentView.setText(currentPost.timeSent.toString().substring(8,15));
            else if((elapsedTime/2.592e+8)>1){//more than 3 days
                stringBuilder = ((Date)currentPost.timeSent).toString().substring(0,2)
                        + currentPost.timeSent.toString().substring(8,15);
                holder.timeSentView.setText(stringBuilder);
            }
            else if((elapsedTime/8.64e+7)>1){//more than a day
                stringBuilder = (int)(elapsedTime/8.64e+7) + "day(s) ago";
                holder.timeSentView.setText( stringBuilder);
            }
<<<<<<< HEAD
            else if((elapsedTime/60000)>1){
=======
            else if((elapsedTime/3.6e+6)>1){//more than an hour
                holder.timeSentView.setText(currentPost.timeSent.toString().substring(11,15));
            }
            else if((elapsedTime/60000)>1){//more than a minute
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
                stringBuilder = (int)(elapsedTime/60000)+"minute(s) ago";
                holder.timeSentView.setText( stringBuilder);
            }
        }
        else
            holder.timeSentView.setVisibility(View.GONE);

        //indicate attachment
        if (!currentPost.hasAttachment){
            holder.attachmentIndicator.setVisibility(View.GONE);
        }

<<<<<<< HEAD
        //show or disable vote indicators
        if(coursePosts.get(position).voteable){
            if(!(currentPost.userVote == CoursePost.UserVote.UNDECIDED
                    || currentPost.voteStatus) )
                holder.voteButton.setVisibility(View.GONE);
            else{
                holder.voteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //creating a popup menu
                        PopupMenu popup = new PopupMenu(mContext, holder.voteButton);
                        //inflating menu from xml resource
                        popup.inflate(R.menu.menu_vote);
                        //adding click listener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.vote_up:
                                        //handle menu1 click
                                        break;
                                    case R.id.vote_down:
                                        //handle menu2 click
                                        break;
                                }
                                return false;
                            }
                        });
                        //displaying the popup
                        popup.show();
                    }
                });
            }

=======

        //show or disable vote indicators
        String userType = recyclerContext
                .getSharedPreferences("mannschaft_knust.classrep.USER_PREF", Context.MODE_PRIVATE)
                .getString("user type","");
        if(filteredCoursePosts.get(position).voteable){
            if (userType.equals("Student")){
                if(!(currentPost.userVote == CoursePost.UserVote.UNDECIDED
                        || currentPost.voteStatus) )
                    holder.voteButton.setVisibility(View.GONE);
                else{
                    holder.voteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //creating a popup menu
                            PopupMenu popup = new PopupMenu(recyclerContext, holder.voteButton);
                            //inflating menu from xml resource
                            popup.inflate(R.menu.menu_vote);
                            //adding click listener
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.vote_up:
                                            //handle menu1 click
                                            break;
                                        case R.id.vote_down:
                                            //handle menu2 click
                                            break;
                                    }
                                    return false;
                                }
                            });
                            //displaying the popup
                            popup.show();
                        }
                    });
                }
            }
            else holder.voteButton.setVisibility(View.GONE);
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
        }
        else {
            holder.voteButton.setVisibility(View.GONE);
            holder.totalVotes.setVisibility(View.GONE);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
<<<<<<< HEAD
        if( coursePosts != null)
            return coursePosts.size();
=======
        if( filteredCoursePosts != null)
            return filteredCoursePosts.size();
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
        else
            return 0;
    }

<<<<<<< HEAD
=======
    @Override
    public Filter getFilter(){
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence charSequence){
                String query = charSequence.toString();

                List<CoursePost> filtered = new ArrayList<>();

                if(query.isEmpty())
                    filtered = courseFilteredPosts;
                else
                    for(int i =0;i<courseFilteredPosts.size();i++){
                        if (courseFilteredPosts.get(i).message.toLowerCase().contains(query.toLowerCase()))
                            filtered.add(courseFilteredPosts.get(i));
                    }
                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results){
                filteredCoursePosts = (List<CoursePost>) results.values;
                notifyDataSetChanged();
            }
        };
    }

>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
}
