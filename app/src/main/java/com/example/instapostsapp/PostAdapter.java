package com.example.instapostsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> posts;
    private DBHelper dbHelper;
    private String currentTable;

    public PostAdapter(Context context, List<Post> posts, DBHelper dbHelper, String currentTable) {
        this.context = context;
        this.posts = posts;
        this.dbHelper = dbHelper;
        this.currentTable = currentTable;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textLikes, textComments;
        private ImageView btnLike, btnComment;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewPost);
            textLikes = itemView.findViewById(R.id.textLikesPost);
            textComments = itemView.findViewById(R.id.textComments);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnComment = itemView.findViewById(R.id.btnComment);
        }

        public void bind(final Post post) {
            Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(post.getImage()));
            imageView.setImageBitmap(bitmap);
            textLikes.setText(String.valueOf(post.getLikes()));
            textComments.setText(String.valueOf(post.getComments()));

            if (post.isLiked()) {
                btnLike.setImageResource(R.drawable.ic_heart_filled);

            } else {
                btnLike.setImageResource(R.drawable.ic_heart_empty);
            }

            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (post.isLiked()) {
                        dbHelper.decrementLikes(currentTable, post.getId());
                        post.setLikes(post.getLikes() - 1);
                        post.setLiked(false);
                        btnLike.setImageResource(R.drawable.ic_heart_empty);

                    } else {
                        dbHelper.incrementLikes(currentTable, post.getId());
                        post.setLikes(post.getLikes() + 1);
                        post.setLiked(true);
                        btnLike.setImageResource(R.drawable.ic_heart_filled);
                        Toast.makeText(context, "+1 Points added", Toast.LENGTH_SHORT).show();
                    }
                    textLikes.setText(String.valueOf(post.getLikes()));
                }
            });

            btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((InstaPosts) context).showCommentDialog(post.getId());
                }
            });
        }
    }
}
