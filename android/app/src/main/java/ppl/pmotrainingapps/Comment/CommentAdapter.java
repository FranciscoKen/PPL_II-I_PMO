package ppl.pmotrainingapps.Comment;

/**
 * Created by David on 3/21/2018.
 */



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import ppl.pmotrainingapps.Main.MainActivity;
import ppl.pmotrainingapps.Pengumuman.Pengumuman;
import ppl.pmotrainingapps.R;
import ppl.pmotrainingapps.Pengumuman.Content;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<Comment> commentList;

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView username_view, comment_view;

        public CommentViewHolder(View view) {
            super(view);
            username_view = itemView.findViewById(R.id.user_name);
            comment_view = itemView.findViewById(R.id.comment_text);
        }

        void setSource(String username, String comment) {
            username_view.setText(username);
            comment_view.setText(comment);
        }

    }

    public CommentAdapter(Context mContext, List<Comment> commentList) {
        this.mContext = mContext;
        this.commentList = commentList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_comment, parent, false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CommentViewHolder holder, int position) {
        final Comment comment = commentList.get(position);
        holder.setSource(comment.userName,comment.comment);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


}
