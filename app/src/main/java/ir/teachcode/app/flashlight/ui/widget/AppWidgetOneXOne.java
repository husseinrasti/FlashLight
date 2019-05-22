package ir.teachcode.app.flashlight.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ir.teachcode.app.flashlight.R;
import ir.teachcode.app.flashlight.ui.activity.StartupActivity;


public class AppWidgetOneXOne extends AppWidgetProvider {


    @Override
    public void onUpdate( Context context , AppWidgetManager appWidgetManager , int[] appWidgetIds ) {
        super.onUpdate( context , appWidgetManager , appWidgetIds );
        for ( int appWidgetId : appWidgetIds ) {
            updateAppWidget( context , appWidgetManager , appWidgetId );
        }
    }

    private void updateAppWidget( Context context , AppWidgetManager appWidgetManager , int appWidgetId ) {

        RemoteViews views = new RemoteViews( context.getPackageName() , R.layout.widget_form_one );

        Intent intentActivityStartup = new Intent( context , StartupActivity.class );
        intentActivityStartup.setAction( AppWidgetManager.ACTION_APPWIDGET_UPDATE );
        PendingIntent pendingActivityStartup = PendingIntent.getActivity( context , 0 , intentActivityStartup , 0 );

        views.setOnClickPendingIntent( R.id.imgFlashLightWidgetOne , pendingActivityStartup );
        appWidgetManager.updateAppWidget( appWidgetId , views );
    }

}
