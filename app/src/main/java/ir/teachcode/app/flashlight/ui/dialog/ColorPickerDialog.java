package ir.teachcode.app.flashlight.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import ir.teachcode.app.flashlight.utils.ColorPicker;


public class ColorPickerDialog extends AlertDialog {


    private final ColorPicker colorPickerView;
    private final OnColorSelectedListener onColorSelectedListener;

    private final float[] colorHSV = new float[] { 0f , 0f , 1f };


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }


    public ColorPickerDialog( Context context , int initialColor , OnColorSelectedListener onColorSelectedListener ) {
        super( context );

        this.onColorSelectedListener = onColorSelectedListener;

        RelativeLayout relativeLayout = new RelativeLayout( context );
        LayoutParams layoutParams = new LayoutParams( LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT );
        layoutParams.addRule( RelativeLayout.CENTER_IN_PARENT );

        colorPickerView = new ColorPicker( context );
        colorPickerView.setColor( initialColor );

        relativeLayout.addView( colorPickerView , layoutParams );

        setButton( BUTTON_POSITIVE , "اعمال کن" , onClickListener );
        setButton( BUTTON_NEGATIVE , "بی خیال" , onClickListener );
        setButton( BUTTON_NEUTRAL , "سفید" , onClickListener );

        setView( relativeLayout );

    }


    private final OnClickListener onClickListener = new OnClickListener() {


        @Override
        public void onClick( DialogInterface dialog , int which ) {
            switch ( which ) {
                case BUTTON_POSITIVE: {
                    int selectedColor = colorPickerView.getColor();
                    onColorSelectedListener.onColorSelected( selectedColor );
                    break;
                }
                case BUTTON_NEGATIVE: {
                    dialog.dismiss();
                    break;
                }
                case BUTTON_NEUTRAL: {
                    int selectedColor = Color.HSVToColor( colorHSV );
                    onColorSelectedListener.onColorSelected( selectedColor );
                    break;
                }
            }
        }
    };



    public interface OnColorSelectedListener {


        public void onColorSelected( int color );
    }

}
