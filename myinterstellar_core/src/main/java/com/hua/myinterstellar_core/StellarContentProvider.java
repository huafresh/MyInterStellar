package com.hua.myinterstellar_core;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-02-29 20:31
 */

@SuppressWarnings("NullableProblems")
public class StellarContentProvider extends ContentProvider {

    public static final Uri URI = Uri.parse("content://com.hua.myinterstellar.dispatcher.provider");

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return StellarMatrix.generateCursor(Dispatcher.get().asBinder());
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
