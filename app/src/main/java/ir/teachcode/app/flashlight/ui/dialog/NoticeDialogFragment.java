package ir.teachcode.app.flashlight.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.teachcode.app.flashlight.R;
import ir.teachcode.app.flashlight.utils.SharedPref;

public class NoticeDialogFragment extends DialogFragment {

    @BindView(R.id.btnOk)
    Button btnOk;
    @BindView(R.id.chkShowDialog)
    CheckBox chkShowDialog;

    private Unbinder unbinder;

    public static NoticeDialogFragment newInstance() {
        return new NoticeDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.dialog_form , container , false );
    }

    @Override
    public void onViewCreated( @NonNull View view , @Nullable Bundle savedInstanceState ) {
        super.onViewCreated( view , savedInstanceState );
        unbinder = ButterKnife.bind( this , view );

        btnOk.setOnClickListener( v -> dismiss() );

        chkShowDialog.setOnCheckedChangeListener( ( buttonView , isChecked ) -> {
            if ( isChecked ) {
                SharedPref.setShowDialog(true);
            }else {
                SharedPref.setShowDialog(false);
            }
        } );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
