package com.minggu3.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

public class TestWidget extends AppWidgetProvider {
    private static final String sharedProfile = "com.minggu3.widget";
    private static final String COUNT_KEY = "count";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences preferences = context.getSharedPreferences(sharedProfile, 0);
        int count = preferences.getInt(COUNT_KEY + appWidgetId, 0);
        count++;

        String dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());

        Intent intentUpdate = new Intent(context, TestWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (context, appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.test_widget);
        views.setTextViewText(R.id.widgetID, String.valueOf(appWidgetId));
        views.setTextViewText(R.id.widgetUpdate, dateString);
        views.setTextViewText(R.id.widgetCount, String.valueOf(count));
        views.setOnClickPendingIntent(R.id.button_update, pendingIntent);


        SharedPreferences.Editor prefEditor = preferences.edit();
        prefEditor.putInt(COUNT_KEY + appWidgetId, count);
        prefEditor.apply();
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}