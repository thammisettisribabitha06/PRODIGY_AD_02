package com.example.todolistapp;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText taskInput;
    Button addButton;
    ListView taskList;

    ArrayList<String> tasks;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskInput = findViewById(R.id.taskInput);
        addButton = findViewById(R.id.addButton);
        taskList = findViewById(R.id.taskList);

        tasks = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, R.layout.task_item, R.id.taskText, tasks) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                Button deleteButton = view.findViewById(R.id.deleteButton);

                deleteButton.setOnClickListener(v -> {
                    tasks.remove(position);
                    notifyDataSetChanged();
                });


                TextView taskText = view.findViewById(R.id.taskText);

                taskText.setOnClickListener(v -> {

                    EditText editText = new EditText(MainActivity.this);
                    editText.setText(tasks.get(position));

                    new android.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("Edit Task")
                            .setView(editText)
                            .setPositiveButton("Save", (dialog, which) -> {

                                tasks.set(position, editText.getText().toString());
                                notifyDataSetChanged();

                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                });

                return view;
            }
        };

        taskList.setAdapter(adapter);

        addButton.setOnClickListener(v -> {

            String task = taskInput.getText().toString();

            if (!task.isEmpty()) {
                tasks.add(task);
                adapter.notifyDataSetChanged();
                taskInput.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Enter a task", Toast.LENGTH_SHORT).show();
            }
        });
    }
}