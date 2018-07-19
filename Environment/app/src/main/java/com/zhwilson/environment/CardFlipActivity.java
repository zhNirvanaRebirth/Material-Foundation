package com.zhwilson.environment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class CardFlipActivity extends AppCompatActivity {
    private boolean showbackFragment = false;
    private TextView flip;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip);
        flip = findViewById(R.id.flip);
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new CardFlipFragmentOne()).commit();
        }
    }

    private void flipCard() {
        if (showbackFragment) {
            getSupportFragmentManager().popBackStack();
            showbackFragment = false;
            return;
        }
        showbackFragment = true;

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out, R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                .replace(R.id.container, new CardFlipFragmentTwo()).addToBackStack(null).commit();
    }

    public static class CardFlipFragmentOne extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_one, container, false);
        }
    }

    public static class CardFlipFragmentTwo extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_two, container, false);
        }
    }
}
