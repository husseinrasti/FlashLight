package ir.teachcode.app.flashlight.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.teachcode.app.flashlight.R;
import ir.teachcode.app.flashlight.ui.dialog.NoticeDialogFragment;
import ir.teachcode.app.flashlight.utils.Config;
import ir.teachcode.app.flashlight.utils.FlashlightProvider;
import ir.teachcode.app.flashlight.utils.SharedPref;
import ir.teachcode.app.flashlight.utils.Utils;


public class StartupActivity extends AppCompatActivity implements OnClickListener {

    private static final int REQUEST_PERMISSIONS = 1;

    @BindView(R.id.imgFlashLed)
    ImageView imgFlashLed;
    @BindView(R.id.imgLayoutAlarm)
    ImageView imgLayoutAlarm;
    @BindView(R.id.imgLayoutPolice)
    ImageView imgLayoutPolice;
    @BindView(R.id.imgLayoutScreen)
    ImageView imgLayoutScreen;
    @BindView(R.id.navigateBack)
    ImageView navigate;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private Unbinder unbinder;
    private FlashlightProvider flashlightProvider;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_startup );
        unbinder = ButterKnife.bind( this );

        flashlightProvider = FlashlightProvider.getInstance( getApplicationContext() );

        initializeUI();
        initNavigation();

        if ( !SharedPref.getShowDialog() ) {
            NoticeDialogFragment dialogFragment = NoticeDialogFragment.newInstance();
            dialogFragment.show( getSupportFragmentManager() , "NoticeDialog" );
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode , String[] permissions , int[] grantResults ) {
        super.onRequestPermissionsResult( requestCode , permissions , grantResults );
        switch ( requestCode ) {
            case REQUEST_PERMISSIONS: {
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    SharedPref.setGrantPermission();
                    FlashlightProvider.getCamera();
                } else {
                    Snackbar.make( findViewById( android.R.id.content ) , R.string.runtime_permissions_txt ,
                            Snackbar.LENGTH_LONG ).setAction( R.string.str_btn_snackbar_enable ,
                            v -> ActivityCompat.requestPermissions( this , permissions , requestCode ) ).show();
                }
                break;
            }
        }
    }

    private void initializeUI() {
        navigate.setVisibility( View.INVISIBLE );
        menu.setOnClickListener( this );
        imgFlashLed.setOnClickListener( this );
        imgLayoutPolice.setOnClickListener( this );
        imgLayoutAlarm.setOnClickListener( this );
        imgLayoutScreen.setOnClickListener( this );
        flashlightProvider.setImgFlashLed( imgFlashLed );
    }

    @Override
    public void onClick( View v ) {
        int i = v.getId();
        if ( i == R.id.menu ) {
            drawerLayout.openDrawer( GravityCompat.START );
        } else if ( i == R.id.imgFlashLed ) {
            if ( !SharedPref.getGrantPermission() ) {
                ActivityCompat.requestPermissions( this , new String[] { Manifest.permission.CAMERA } , REQUEST_PERMISSIONS );
                return;
            } else {
                flashlightProvider.checkFlashLight();
            }
        } else if ( i == R.id.imgLayoutAlarm ) {
            startActivity( new Intent( StartupActivity.this , AlarmActivity.class ) );
        } else if ( i == R.id.imgLayoutPolice ) {
            startActivity( new Intent( StartupActivity.this , PoliceActivity.class ) );
        } else if ( i == R.id.imgLayoutScreen ) {
            startActivity( new Intent( StartupActivity.this , ColorScreenActivity.class ) );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FlashlightProvider.getCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // on pause turn off the flash
        flashlightProvider.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // on stop release the camera
        flashlightProvider.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initNavigation() {
        navigationView.setNavigationItemSelectedListener( item -> {
            int id = item.getItemId();
            drawerLayout.closeDrawers();
            switch ( id ) {
                case R.id.navigation_item_help: {
                    Intent intent = new Intent( StartupActivity.this , HelpActivity.class );
                    startActivity( intent );
                    break;
                }
                case R.id.navigation_item_about: {
                    Intent intent = new Intent( StartupActivity.this , AboutActivity.class );
                    startActivity( intent );
                    break;
                }
                case R.id.navigation_item_share: {
                    String data = getResources().getString( R.string.share_text ) + "\n"
                            + "http://cafebazaar.ir/app/?id=" + Utils.PACKAGE_NAME + "&ref=share";
                    Intent intent = new Intent( Intent.ACTION_SEND );
                    intent.setType( "text/plain" );
                    intent.putExtra( Intent.EXTRA_SUBJECT , data );
                    intent.putExtra( Intent.EXTRA_TITLE , getResources().getString( R.string.app_name ) );
                    intent.putExtra( Intent.EXTRA_TEXT , data );
                    startActivity( Intent.createChooser( intent , getResources().getString( R.string.share_using ) ) );
                    break;
                }
                case R.id.navigation_item_rate: {
                    if ( Utils.isPackageExisted( this , "com.farsitel.bazaar" ) ) {
                        Intent intent = new Intent( Intent.ACTION_EDIT );
                        intent.setData( Uri.parse( "bazaar://details?id=" + Utils.PACKAGE_NAME ) );
                        intent.setPackage( "com.farsitel.bazaar" );
                        startActivity( intent );
                    } else {
                        Toast.makeText( getApplicationContext() , getString( R.string.str_toast_install_market ) , Toast.LENGTH_LONG ).show();
                    }
                    break;
                }
                case R.id.navigation_item_app: {
                    if ( Utils.isPackageExisted( this , "com.farsitel.bazaar" ) ) {
                        Intent intent = new Intent( Intent.ACTION_VIEW );
                        intent.setData( Uri.parse( "bazaar://collection?slug=by_author&aid=" + Config.DEVELOPER_ID ) );
                        intent.setPackage( "com.farsitel.bazaar" );
                        startActivity( intent );
                    } else {
                        Toast.makeText( getApplicationContext() , getString( R.string.str_toast_install_market ) , Toast.LENGTH_LONG ).show();
                    }
                    break;
                }
            }
            return true;
        } );
    }
}
