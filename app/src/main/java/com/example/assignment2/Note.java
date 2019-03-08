package com.example.assignment2;

public class Note {
    private String note;
    private int index;

    public Note(String _note, int _index) {
        note = _note;
        index = _index;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String _note) {
        note = _note;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int _index) {
        index = _index;
    }
}
