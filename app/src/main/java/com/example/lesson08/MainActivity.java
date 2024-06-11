package com.example.lesson08;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lesson08.models.Db;
import com.example.lesson08.models.Post;
import com.example.lesson08.models.PostAdapter;
import com.example.lesson08.models.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private PostAdapter adapter;
    private TextView tvSlideNumber;
    private Button btnFirstSlide;
    private Button btnLastSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        tvSlideNumber = findViewById(R.id.tvSlideNumber);
        btnLastSlide = findViewById(R.id.btnLastSlide);
        btnFirstSlide = findViewById(R.id.btnFirstSlide);

        btnLastSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastSlidePosition = adapter.getItemCount() - 1;
                viewPager.setCurrentItem(lastSlidePosition);
            }
        });

        btnFirstSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int firstSlidePosition = 0;
                viewPager.setCurrentItem(firstSlidePosition);
            }
        });


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                String slideNumberText = "Slide " + (position + 1) + "/" + adapter.getItemCount();
                tvSlideNumber.setText(slideNumberText);
                saveSlideNumber(position);
            }
        });


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewPager.getCurrentItem();
                com.example.lesson08.PostFragment postFragment = (com.example.lesson08.PostFragment) adapter.createFragment(position);
                Post post = postFragment.getPost();
                Db.getUser(post.getUserId(), new Db.UserFetchListener() {
                    @Override
                    public void onUserFetched(User user) {
                        showUserDialog(user);
                    }

                    @Override
                    public void onError(Throwable t) {
                        // Handle the error
                    }
                });
            }
        };

        Db.getAllPosts(new Db.PostFetchListener() {
            @Override
            public void onPostsFetched(List<Post> posts) {
                adapter = new PostAdapter(MainActivity.this, posts, clickListener);
                viewPager.setAdapter(adapter);
                viewPager.setPageTransformer(new ZoomOutPageTransformer());
                viewPager.setCurrentItem(getSavedSlideNumber());
            }

            @Override
            public void onError(Throwable t) {
                Log.d("MainActivity", "onError: " + t.getMessage());
            }
        });
    }

    public void showUserDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("User Details");
        builder.setMessage("Username: " + user.getUsername() + "\nPhone: " + user.getPhone() + "\nCity: " + user.getAddress().getCity());
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void saveSlideNumber(int slideNumber) {
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("slideNumber", slideNumber);
        editor.apply();
    }

    private int getSavedSlideNumber() {
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        int result = sharedPreferences.getInt("slideNumber", 0);
        Log.d("MainActivity", "getSavedSlideNumber: " + result);
        return result;
    }
}