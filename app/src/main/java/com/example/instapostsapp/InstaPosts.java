package com.example.instapostsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InstaPosts extends AppCompatActivity {

    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private String currentTable;
    private ImageView home, target, flash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insta_posts);

        dbHelper = new DBHelper(this);

        home = findViewById(R.id.home);
        target = findViewById(R.id.target);
        flash = findViewById(R.id.flash);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        currentTable = getCurrentTable();
        postList = getPosts();
        postAdapter = new PostAdapter(this, postList, dbHelper, currentTable);
        recyclerView.setAdapter(postAdapter);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstaPosts.this, home.class);
                startActivity(intent);
            }
        });

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to a meaningful activity for 'flash' click
                Intent intent = new Intent(InstaPosts.this, InstaPosts.class);
                startActivity(intent);
            }
        });

        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to a meaningful activity for 'target' click
                Intent intent = new Intent(InstaPosts.this, challenge.class);
                startActivity(intent);
            }
        });
    }

    private List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(currentTable, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                int likes = cursor.getInt(cursor.getColumnIndexOrThrow("likes"));
                int comments = cursor.getInt(cursor.getColumnIndexOrThrow("comments"));
                int isLikedInt = cursor.getInt(cursor.getColumnIndexOrThrow("isLiked"));  // Read the isLiked column
                boolean isLiked = isLikedInt == 1;  // Convert to boolean

                posts.add(new Post(id, image, likes, comments, isLiked));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return posts;
    }

    public void showCommentDialog(final int postId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Comment");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Submit", (dialog, which) -> {
            String comment = input.getText().toString();
            if (!comment.isEmpty()) {
                dbHelper.addComment(postId, comment);
                dbHelper.incrementComments(currentTable, postId);
                postList.clear();
                postList.addAll(getPosts());  // Refresh the list
                postAdapter.notifyDataSetChanged();
                Toast.makeText(InstaPosts.this, "Comment added", Toast.LENGTH_SHORT).show();
                Toast.makeText(InstaPosts.this, "+2 Points added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InstaPosts.this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private String getCurrentTable() {
        String[] tables = {DBHelper.TABLE_CHALLENGE_1, DBHelper.TABLE_CHALLENGE_2, DBHelper.TABLE_CHALLENGE_3};
        return tables[(int) (Math.random() * tables.length)];
    }
}
