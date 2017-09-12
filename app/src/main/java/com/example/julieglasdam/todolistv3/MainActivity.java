package com.example.julieglasdam.todolistv3;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    private List<String> tasks = new ArrayList<>();
    private ListView listView;
    private int countTasks = 0;


    // Saves the tasks, the user inputs, and stores it permanently
    private void saveData(String data) {
        SharedPreferences sharedPreferences = getSharedPreferences("tasks", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("task"+countTasks, data);
        editor.commit();
        tasks.add(data);
        countTasks++;
        Log.d("saveData", data);
    }

    // Get data and save it in a list
    private void readFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("tasks", Context.MODE_PRIVATE);
        Map<String,?> keys = sharedPreferences.getAll();
          for(Map.Entry<String,?> entry : keys.entrySet()){
               Log.d("map values",entry.getKey() + ": " +
               entry.getValue().toString());
               tasks.add(entry.getValue().toString());
               countTasks++;
          }


    }


    /* Get data from file, use an array adaptor to convert the strings, to something
    * that can be displayed. Create a listview and add the adaptor to it*/
    private void createList() {
        // Get items from file and store in a list
        readFromSharedPreferences();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,            // Reference super class
                R.layout.item,   // The xml file for the one item
                tasks);          // The list of strings to be displayed


        // Create listview by finding the id from the xml file, and use the adaptor
        listView = getListView();
        listView.setAdapter(arrayAdapter);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createList();

        // Input field and button
        final EditText editText = (EditText) findViewById(R.id.inputTask);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new Button.OnClickListener(){ // Event listener
            public void onClick(View v) { // Callback method
                if (!editText.getText().toString().matches("")) { // Check if input field is empty
                    saveData(editText.getText().toString()); // Add data to shared preferences and list
                    ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged(); // Update list after adding item
                }
            }
        });


    }
}
