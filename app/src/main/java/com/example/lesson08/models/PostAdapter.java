package com.example.lesson08.models;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.lesson08.PostFragment;

import java.util.List;

public class PostAdapter extends FragmentStateAdapter {
    private List<Post> posts;
    private View.OnClickListener clickListener;

    public PostAdapter(@NonNull FragmentActivity fragmentActivity, List<Post> posts, View.OnClickListener clickListener) {
        super(fragmentActivity);
        this.posts = posts;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new PostFragment(posts.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public Post getPostAt(int position) {
        return posts.get(position);
    }
}