package ir.teachcode.app.flashlight.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.teachcode.app.flashlight.R;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigateBack)
    ImageView navigate;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.email)
    TextView email;

    private Unbinder unbinder;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about );
        unbinder = ButterKnife.bind( this );

        initializeUI();
    }

    private void initializeUI() {
        email.setText( Html.fromHtml( "<a href=\"mailto:teachcode.ir@gmail.com\">ارسال نظر و پیشنهاد</a>" ) );
        email.setMovementMethod( LinkMovementMethod.getInstance() );

        menu.setVisibility( View.INVISIBLE );

        navigate.setOnClickListener( v -> finish() );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
