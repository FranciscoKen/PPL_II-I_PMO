package ppl.pmotrainingapps.Video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;

import ppl.pmotrainingapps.Materi.ContentMateri;
import ppl.pmotrainingapps.R;

public class FullScreenMediaController extends MediaController {

    private ImageButton fullScreen;
    private String isFullScreen;

    public FullScreenMediaController(Context context){
        super(context);
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);

        //Image Button for fullscreen
        fullScreen = new ImageButton(super.getContext());

        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.RIGHT;

        params.rightMargin = 20;
        addView(fullScreen,params);

        isFullScreen =  ((Activity)getContext()).getIntent().getStringExtra("fullScreenInd");

        if("y".equals(isFullScreen)){
            fullScreen.setImageResource(R.drawable.ic_fullscreen_exit);
            fullScreen.setBackgroundResource(0);
        } else {
            fullScreen.setImageResource(R.drawable.ic_fullscreen);
            fullScreen.setBackgroundResource(0);
        }

        fullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),VideoActivityExample.class);

                if("y".equals(isFullScreen)){
                    intent.putExtra("fullScreenInd","");
                } else {
                    intent.putExtra("fullScreenInd","y");
                }

                ((Activity)getContext()).startActivity(intent);
            }
        });
    }
}
