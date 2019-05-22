package ir.teachcode.app.flashlight.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.teachcode.app.flashlight.R;

public class HelpActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.btnOkay)
    Button btnOkay;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_help );
        unbinder = ButterKnife.bind( this );

        btnOkay.setOnClickListener( v -> finish() );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
