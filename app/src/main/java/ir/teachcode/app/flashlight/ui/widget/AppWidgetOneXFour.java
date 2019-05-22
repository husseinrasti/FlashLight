package ir.teachcode.app.flashlight.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ir.teachcode.app.flashlight.R;
import ir.teachcode.app.flashlight.ui.activity.AlarmActivity;
import ir.teachcode.app.flashlight.ui.activity.PoliceActivity;
import ir.teachcode.app.flashlight.ui.activity.ColorScreenActivity;
import ir.teachcode.app.flashlight.ui.activity.StartupActivity;


public class AppWidgetOneXFour extends AppWidgetProvider {

    @Override
    public void onUpdate( Context context , AppWidgetManager appWidgetManager , int[] appWidgetIds ) {
        super.onUpdate( context , appWidgetManager , appWidgetIds );
        for ( int appWidgetId : appWidgetIds ) {
            updateAppWidget( context , appWidgetManager , appWidgetId );
        }
    }

    private void updateAppWidget( Context context , AppWidgetManager appWidgetManager , int appWidgetId ) {
        RemoteViews views = new RemoteViews( context.getPackageName() , R.layout.widget_form );

        Intent intentPoliceLights = new Intent( context , PoliceActivity.class );
        intentPoliceLights.setAction( AppWidgetManager.ACTION_APPWIDGET_UPDATE );
        PendingIntent pendingPoliceLights = PendingIntent.getActivity( context , 0 , intentPoliceLights , 0 );

        Intent intentActivityStartup = new Intent( context , StartupActivity.class );
        intentActivityStartup.setAction( AppWidgetManager.ACTION_APPWIDGET_UPDATE );
        PendingIntent pendingActivityStartup = PendingIntent.getActivity( context , 0 , intentActivityStartup , 0 );

        Intent intentAlarm = new Intent( context , AlarmActivity.class );
        intentAlarm.setAction( AppWidgetManager.ACTION_APPWIDGET_UPDATE );
        PendingIntent pendingAlarm = PendingIntent.getActivity( context , 0 , intentAlarm , 0 );

        Intent intentScreenLight = new Intent( context , ColorScreenActivity.class );
        intentScreenLight.setAction( AppWidgetManager.ACTION_APPWIDGET_UPDATE );
        PendingIntent pendingScreenLight = PendingIntent.getActivity( context , 0 , intentScreenLight , 0 );

        views.setOnClickPendingIntent( R.id.imgPoliceLightWidget , pendingPoliceLights );
        views.setOnClickPendingIntent( R.id.imgAlarmWidget , pendingAlarm );
        views.setOnClickPendingIntent( R.id.imgScreenLight , pendingScreenLight );
        views.setOnClickPendingIntent( R.id.imgFlashLightWidget , pendingActivityStartup );

        appWidgetManager.updateAppWidget( appWidgetId , views );
    }
}