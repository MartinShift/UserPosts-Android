package com.example.lesson08;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lesson08.models.Post;

public class PostFragment extends Fragment {
    private TextView tvHeader;
    private TextView tvParagraph;
    private Post post;
    private View.OnClickListener clickListener;

    public PostFragment(Post post, View.OnClickListener clickListener) {
        super(R.layout.fragment_post);
        this.post = post;
        this.clickListener = clickListener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.tvHeader);
        tvParagraph = view.findViewById(R.id.tvParagraph);

        tvHeader.setText(post.getTitle());
        tvParagraph.setText(post.getBody());

        view.setOnClickListener(clickListener);
    }

    public Post getPost() {
        return post;
    }
}