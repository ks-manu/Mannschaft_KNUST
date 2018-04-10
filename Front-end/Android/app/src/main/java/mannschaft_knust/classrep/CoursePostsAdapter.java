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
import android.widget.TextView;

import java.util.Date;
import java.util.List;

class CoursePostsAdapter extends RecyclerView.Adapter<CoursePostsAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView senderNameView;
        private TextView timeSentView;
        private TextView notSentIndicator;
        private TextView messageView;
        private TextView attachmentIndicator;
        private Button voteButton;
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
    private Context mContext;

    public CoursePostsAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void updateData(List<CoursePost> coursePosts){
        this.coursePosts = coursePosts;
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
        CoursePost currentPost = coursePosts.get(position);
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
            else if((elapsedTime/60000)>1){
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

        }
        else {
            holder.voteButton.setVisibility(View.GONE);
            holder.totalVotes.setVisibility(View.GONE);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if( coursePosts != null)
            return coursePosts.size();
        else
            return 0;
    }

}
