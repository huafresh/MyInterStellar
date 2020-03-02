package com.hua.myinterstellar_core;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-01 09:03
 */

class StellarMatrix extends MatrixCursor {
    private static final String[] COLUMN_NAME = {"dispatcher_binder"};
    private Bundle extras = new Bundle();
    private static final String KEY_DISPATCHER_BINDER = "get_dispatcher_binder";


    private StellarMatrix(String[] columnNames, IBinder binder) {
        super(columnNames);
        extras.putBinder(KEY_DISPATCHER_BINDER, binder);
    }

    @Override
    public Bundle getExtras() {
        return extras;
    }

    static Cursor generateCursor(IBinder binder) {
        return new StellarMatrix(COLUMN_NAME, binder);
    }

    static @Nullable
    IBinder resolveBinder(@Nullable Cursor cursor) {
        if (cursor != null && cursor.getExtras() != null) {
            return cursor.getExtras().getBinder(KEY_DISPATCHER_BINDER);
        }
        return null;
    }
}
