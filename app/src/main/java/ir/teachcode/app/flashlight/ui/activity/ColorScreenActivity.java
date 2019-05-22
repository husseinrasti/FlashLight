package ir.teachcode.app.flashlight.ui.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.teachcode.app.flashlight.ui.dialog.ColorPickerDialog;
import ir.teachcode.app.flashlight.R;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ColorScreenActivity extends AppCompatActivity {

    private ColorPickerDialog colorPickerDialog;

    @BindView(R.id.layoutScreen)
    ConstraintLayout layoutScreen;
    @BindView(R.id.seekBarLight)
    SeekBar seekBarLight;
    @BindView(R.id.imgColor)
    ImageView imgColor;
    @BindView(R.id.imgBackScreen)
    ImageView imgBackScreen;

    private Unbinder unbinder;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_color_screen );
        unbinder = ButterKnife.bind( this );

        final WindowManager.LayoutParams layoutParam = getWindow().getAttributes();

        colorPickerDialog = new ColorPickerDialog( this , 0xffffff , color -> layoutScreen.setBackgroundColor( color ) );

        imgColor.setOnClickListener( v -> colorPickerDialog.show() );
        imgBackScreen.setOnClickListener( v -> finish() );
        seekBarLight.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {


            @Override
            public void onStopTrackingTouch( SeekBar seekBar ) {

            }


            @Override
            public void onStartTrackingTouch( SeekBar seekBar ) {

            }


            @Override
            public void onProgressChanged( SeekBar seekBar , int progress , boolean fromUser ) {
                float brightness = progress / 100.0f;
                layoutParam.screenBrightness = brightness;
                getWindow().setAttributes( layoutParam );
            }
        } );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}