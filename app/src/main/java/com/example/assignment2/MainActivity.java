package com.example.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;


// Kyle Ehlers
// COMP 3710 - 001
// 2/26/19
// Assignment 2

public class MainActivity extends AppCompatActivity {

    Button addNoteButton;
    EditText editText;
    ScrollView scrollView;
    LinearLayout scrollLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNoteButton = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        scrollView = findViewById(R.id.scrollView);
        scrollLayout = findViewById(R.id.scrollLayout);
    }

    public void addNote(View v) {
        String note = editText.getText().toString().trim();
        if (!note.equals("")) {
            View newNote = null;
            newNote = (LinearLayout) View.inflate(MainActivity.this, R.layout.scroll_items, null);
            EditText et = newNote.findViewById(R.id.editNote);
            et.setText(note);
            Button db = newNote.findViewById(R.id.deleteButton);
            db.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollLayout.removeView((View)v.getParent());
                }
            });
            scrollLayout.addView(newNote);
        }

    }
}
