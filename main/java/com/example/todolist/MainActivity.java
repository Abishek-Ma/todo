package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TaskDbHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private List<String> taskTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskDbHelper(this);
        taskTitles = new ArrayList<>();

        EditText editTextTask = findViewById(R.id.editTextTask);
        Button buttonAddTask = findViewById(R.id.buttonAddTask);
        ListView listViewTasks = findViewById(R.id.listViewTasks);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskTitles);
        listViewTasks.setAdapter(adapter);

        loadTasks();

        buttonAddTask.setOnClickListener(v -> {
            String task = editTextTask.getText().toString();
            if (!task.isEmpty()) {
                dbHelper.insertTask(task);
                taskTitles.add(task);
                adapter.notifyDataSetChanged();
                editTextTask.setText("");
            }
        });

        listViewTasks.setOnItemClickListener((parent, view, position, id) -> {
            dbHelper.deleteTask(position + 1); // Delete by position (adjust for 0-index)
            taskTitles.remove(position);
            adapter.notifyDataSetChanged();
        });
    }

    private void loadTasks() {
        List<Task> tasks = dbHelper.getAllTasks();
        for (Task task : tasks) {
            taskTitles.add(task.getName());
        }
        adapter.notifyDataSetChanged();
    }
}
