package ir.teachcode.app.flashlight.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.teachcode.app.flashlight.App;
import ir.teachcode.app.flashlight.R;

public class PoliceActivity extends AppCompatActivity {

    @BindView(R.id.imgViewRed)
    ImageView imgViewRed;
    @BindView(R.id.imgViewBlue)
    ImageView imgViewBlue;
    @BindView(R.id.imgBackPolice)
    ImageView imgBackPolice;
    @BindView(R.id.imgPlay)
    ImageView imgPlay;
    @BindView(R.id.imgStop)
    ImageView imgStop;

    private boolean isActive = false;
    private boolean isSwap = true;

    private Runnable runnable;

    private MediaPlayer mediaPlayer;

    private Unbinder unbinder;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_police );
        unbinder = ButterKnife.bind( this );

        WindowManager.LayoutParams layoutParam = getWindow().getAttributes();
        layoutParam.screenBrightness = 1;
        getWindow().setAttributes( layoutParam );

        imgPlay.setOnClickListener( v -> {
            mediaPlayer = MediaPlayer.create( this , R.raw.police );
            mediaPlayer.setLooping( true );
            imgPlay.setVisibility( View.INVISIBLE );
            imgStop.setVisibility( View.VISIBLE );
            try {
                mediaPlayer.start();
            } catch ( IllegalArgumentException e ) {
                e.printStackTrace();
            } catch ( SecurityException e ) {
                e.printStackTrace();
            } catch ( IllegalStateException e ) {
                e.printStackTrace();
            }
        } );

        imgStop.setOnClickListener( v -> {
            try {
                imgPlay.setVisibility( View.VISIBLE );
                imgStop.setVisibility( View.INVISIBLE );
                mediaPlayer.release();
                mediaPlayer = null;
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } );

        runnable = () -> {
            try {
                policeLight();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        };

        startPoliceLights();

        imgBackPolice.setOnClickListener( v -> {
            finish();
        } );
    }

    private void policeLight() {
        if ( isActive ) {
            if ( isSwap ) {
                imgViewRed.setBackgroundColor( Color.RED );
                imgViewBlue.setBackgroundColor( Color.BLACK );
                isSwap = false;
                App.HANDLER.postDelayed( runnable , 150 );
            } else {
                imgViewRed.setBackgroundColor( Color.BLACK );
                imgViewBlue.setBackgroundColor( Color.BLUE );
                isSwap = true;
                App.HANDLER.postDelayed( runnable , 150 );
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            runnable = null;
            mediaPlayer.release();
            mediaPlayer.stop();
        } catch ( Exception e ) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void startPoliceLights() {
        isActive = true;
        App.HANDLER.post( runnable );
    }

}
