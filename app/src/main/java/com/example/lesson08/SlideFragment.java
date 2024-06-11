package com.example.lesson08;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SlideFragment extends Fragment {
    private TextView tvHeader;
    private TextView tvParagraph;
    private Slide slide;

    public SlideFragment(int resource, Slide slide) {
        super(resource);
        this.slide = slide;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.tvHeader);
        tvParagraph = view.findViewById(R.id.tvParagraph);

        tvHeader.setText(slide.getHeader());
        tvParagraph.setText(slide.getParagraph());
        view.setBackgroundColor(slide.getColor());

        tvHeader.setOnClickListener(v -> {
            Toast.makeText(getContext(), slide.getHeader(), Toast.LENGTH_SHORT).show();
        });
    }
}
