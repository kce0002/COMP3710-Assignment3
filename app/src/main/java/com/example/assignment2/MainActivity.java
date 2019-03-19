package com.example.assignment2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;


// Kyle Ehlers
// COMP 3710 - 001
// 3/8/19
// Assignment 3

public class MainActivity extends AppCompatActivity {

    Button addNoteButton;
    EditText editText;
    ScrollView scrollView;
    LinearLayout scrollLayout;

    ArrayList<String> notesList;
    ArrayList<String> searchList;
    int historySize;
    boolean search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fields:
        historySize = 0;
        notesList = new ArrayList<>();
        searchList = new ArrayList<>();
        search = false;

        String savedNote = "";

        // UI Elements:
        addNoteButton = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        scrollView = findViewById(R.id.scrollView);
        scrollLayout = findViewById(R.id.scrollLayout);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsFile", 0);
        historySize = prefs.getInt("historySizeKey", historySize);
        for (int i = 0; i < historySize; i++) {
            String note = prefs.getString("note" + i, savedNote);
            //View newNote = null;
            //newNote = (LinearLayout) View.inflate(MainActivity.this, R.layout.scroll_items, null);
            View newNote;
            newNote = View.inflate(MainActivity.this, R.layout.scroll_items, null);
            EditText et = newNote.findViewById(R.id.editNote);
            et.setText(note);
            Button delete = newNote.findViewById(R.id.deleteButton);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollLayout.removeView((View)v.getParent());
                }
            });
            scrollLayout.addView(newNote);
            //notesList.add(note);
        }

        /*for (String s : notesList) {
            View newNote = null;
            newNote = (LinearLayout) View.inflate(MainActivity.this, R.layout.scroll_items, null);
            EditText et = newNote.findViewById(R.id.editNote);
            et.setText(s);
            Button db = newNote.findViewById(R.id.deleteButton);
            db.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollLayout.removeView((View)v.getParent());
                }
            });
            scrollLayout.addView(newNote);
        }*/

    }

    @Override
    protected void onStop() {
        SharedPreferences prefs = this.getSharedPreferences("myPrefsFile", 0);
        SharedPreferences.Editor editor = prefs.edit();

        if (search) {
            populateView();
            search = false;
        }

        populateNotesList();

        editor.putInt("historySizeKey", notesList.size());

        for (int i = 0; i < notesList.size(); i++) {
            editor.putString("note"+ i, notesList.get(i));
        }

        editor.apply();
        //editor.commit();

        super.onStop();
    }

    public void addNote(View v) {
        String note = editText.getText().toString().trim();
        notesList.add(note);

        if (!note.equals("")) {
            View newNote;
            newNote = View.inflate(MainActivity.this, R.layout.scroll_items, null);
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

    public void search(View v) {
        if (!search) {
            populateNotesList();
        }

        search = true;

        String keyword = editText.getText().toString().trim();

        searchList.clear();
        scrollLayout.removeAllViews();

        for (String s : notesList) {
            if (s.contains(keyword)) {
                searchList.add(s);
            }
        }

        for (String s : searchList) {
            View newNote;
            newNote = View.inflate(MainActivity.this, R.layout.scroll_items, null);
            EditText et = newNote.findViewById(R.id.editNote);
            et.setText(s);
            Button db = newNote.findViewById(R.id.deleteButton);
            db.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Don't delete while searching a non-empty keyword", Toast.LENGTH_LONG).show();
                }
            });
            scrollLayout.addView(newNote);
        }
    }

    public void clearSearch(View v) {
        populateView();
        search = false;
    }

    private void populateView() {
        scrollLayout.removeAllViews();

        for (int i = 0; i < notesList.size(); i++) {
            View newNote;
            newNote = View.inflate(MainActivity.this, R.layout.scroll_items, null);
            EditText et = newNote.findViewById(R.id.editNote);
            et.setText(notesList.get(i));
            Button db = newNote.findViewById(R.id.deleteButton);
            db.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(MainActivity.this, "Don't delete while searching a non-empty keyword", Toast.LENGTH_LONG).show();
                    scrollLayout.removeView((View)v.getParent());
                }
            });
            scrollLayout.addView(newNote);
        }
    }

    private void populateNotesList() {
        notesList.clear();

        for (int i = 0; i < scrollLayout.getChildCount(); i++) {
            LinearLayout l = (LinearLayout) scrollLayout.getChildAt(i);
            View v = l.getChildAt(0);
            EditText e = (EditText) v;
            notesList.add(e.getText().toString());
        }
    }
}
