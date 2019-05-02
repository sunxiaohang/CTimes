package com.sunxiaohang.root.ctimes;

import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicPlayActivity extends AppCompatActivity {
    private PlayRotateView playRotateView;
    private TextView musicNameTextView;
    private RecyclerView lyricsRecycleView;

    private FloatingActionButton playPanelActionButton;

    private LinearLayout playDialogPanel;
    private TextView singleLineLyrics;
    private TextView currentProcessTextView;
    private SeekBar progressSeekBar;
    private TextView durationTextView;
    private ImageButton previousButton;
    private ImageButton playButton;
    private ImageButton nextButton;
    class FloatActionButtonTranslate implements TypeEvaluator<Point> {
        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            int currentX = (int) ((endValue.x-startValue.x)*fraction);
            int currentY = (int) ((endValue.y=startValue.y)*fraction);
            Point point = new Point(currentX,currentY);
            return point;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player_view);
//        Music music = getIntent().getExtras().getParcelable("music");
        bindView();
        playPanelActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radius = playPanelActionButton.getWidth()/2;
                TranslateAnimation translateAnimation = new TranslateAnimation(0,-playDialogPanel.getWidth()/2+(int)(1.5*radius),0,playDialogPanel.getHeight()/2);
                translateAnimation.setDuration(300);
                translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                final ScaleAnimation scaleAnimation = new ScaleAnimation(1f,0f,1f,0f);
                scaleAnimation.setDuration(100);
                final ScaleAnimation panelScaleAnimation = new ScaleAnimation(0f,1f,0f,1f,playDialogPanel.getWidth()/2,playDialogPanel.getHeight()/2);
                panelScaleAnimation.setDuration(300);
                panelScaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                playPanelActionButton.startAnimation(translateAnimation);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        playPanelActionButton.setAnimation(scaleAnimation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        playDialogPanel.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        playDialogPanel.startAnimation(panelScaleAnimation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
//                AnimationSet set = new AnimationSet(false);
//                TranslateAnimation translateAnimation = new TranslateAnimation(centerX,playDialogPanel.getWidth()/2,centerY,playDialogPanel.getWidth()/2);
//                translateAnimation.setDuration(2000);
//                playPanelActionButton.setAnimation(translateAnimation);
//                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,0.0f,1.0f,0.0f);
//                scaleAnimation.setDuration(500);
//                AnimatorSet animatorSet;
//                set.addAnimation();
            }
        });
    }

    private void bindView() {
        playRotateView = findViewById(R.id.music_play_rotate_view);
        musicNameTextView = findViewById(R.id.music_play_name);
        lyricsRecycleView = findViewById(R.id.play_item_lyrics_recycle_view);

        playPanelActionButton = findViewById(R.id.float_action_button);
        playDialogPanel = findViewById(R.id.play_dialog_panel);
        singleLineLyrics = findViewById(R.id.single_line_lyrics);
        currentProcessTextView = findViewById(R.id.play_process_time);
        progressSeekBar = findViewById(R.id.progress_seek_bar);
        durationTextView = findViewById(R.id.duration_text_view);
        previousButton = findViewById(R.id.previous_button);
        playButton = findViewById(R.id.play_button);
        nextButton = findViewById(R.id.next_button);
    }
}
