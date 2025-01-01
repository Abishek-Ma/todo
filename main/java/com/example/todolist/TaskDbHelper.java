package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TaskDb.db";
    private static final int DATABASE_VERSION = 1;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " (" +
                        TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY," +
                        TaskContract.TaskEntry.COLUMN_NAME_TITLE + " TEXT," +
                        TaskContract.TaskEntry.COLUMN_NAME_COMPLETED + " INTEGER)";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }

    public void insertTask(String title) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE, title);
        values.put(TaskContract.TaskEntry.COLUMN_NAME_COMPLETED, 0);
        db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
    }

    public List<Task> getAllTasks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        List<Task> tasks = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_TITLE));
            boolean completed = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_COMPLETED)) > 0;
            tasks.add(new Task(id, title, completed));
        }
        cursor.close();
        return tasks;
    }

    public void deleteTask(long id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = TaskContract.TaskEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(TaskContract.TaskEntry.TABLE_NAME, selection, selectionArgs);
    }
}
