package com.dgl114.improvedtodolist;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//**************************************************************************************************
//ToDoList.java         Author: Unknown         Modified by: zakacat
//
//This class contains the model logic for Zakacat's To Do list app.
//It includes methods
//- To add an item to the list
//- To return the items in the list as a String array
//- To clear the list
//- To save the list to the local file (public)
//- To read the list that is currently in the file
//- To write the saved list (private)
//**************************************************************************************************
public class ToDoList {
    //**********************************************************************************************
    //Here we have a string which is FILENAME and names it something appropriate for a text file.
    //**********************************************************************************************
    private static final String FILENAME = "todolist.txt";
    private final Context CONTEXT;

    private List<String> mTaskList;

    //**********************************************************************************************
    //The constructor passes in the context from wherever it was called from. It then instantiates
    // an ArrayList of the String type.
    //**********************************************************************************************
    public ToDoList(Context context) {
        CONTEXT = context;
        mTaskList = new ArrayList<>();
    }

    //**********************************************************************************************
    //addItem() passes in a String and then adds it the the list. An exception is thrown if the item
    //being passed in is not the correct String type.
    //**********************************************************************************************
    public void addItem(String item) throws IllegalArgumentException {
        mTaskList.add(item);
    }

    //**********************************************************************************************
    //getItems() returns a local String array that has used ArrayList method to toArray() to transfer
    //the list of strings. This method then returns the list of strings and a String array.
    //**********************************************************************************************
    public String[] getItems() {
        String[] items = new String[mTaskList.size()];
        return mTaskList.toArray(items);
    }

    //**********************************************************************************************
    //clear() calls the ArrayList method clear() to clear the list.
    //**********************************************************************************************
    public void clear() {
        mTaskList.clear();
    }

    //**********************************************************************************************
    //saveToFile() creates a FileOutputStream at the file location in private mode which restricts
    //access to only this app. It then calls writeListToStream() while passing in the new
    //output stream. Throws an exception if the file cannot be created.
    //**********************************************************************************************
    public void saveToFile() throws IOException {
        FileOutputStream outputStream = CONTEXT.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        writeListToStream(outputStream);
    }

    //**********************************************************************************************
    //writeListToStream() takes the outputStream and assigns it to a PrintWriter object. That object
    //then prints the strings (one per line) to the text document via the outputStream. The writer
    //is then closed (I assume to prevent resource leaks).
    //**********************************************************************************************
    private void writeListToStream(FileOutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        for (String item : mTaskList) {
            writer.println(item);
        }
        writer.close();
    }

    //**********************************************************************************************
    //readFromFile() creates a BufferedReader object, opens an input stream for the same list file,
    //clears the mTaskList attribute. It then reads the lines from the text and adds them to the
    //mTaskList attribute. If the file is not found, it throws an exceptions. After that,
    //if the reader is still open, it is also closed.
    //**********************************************************************************************
    public void readFromFile() throws IOException {

        BufferedReader reader = null;

        try {
            FileInputStream inputStream = CONTEXT.openFileInput(FILENAME);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            mTaskList.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                mTaskList.add(line);
            }
        } catch (FileNotFoundException ignored) {

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
