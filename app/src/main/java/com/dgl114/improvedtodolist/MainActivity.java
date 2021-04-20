package com.dgl114.improvedtodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import java.io.IOException;

//**************************************************************************************************
//MainActivity.java         Author: Unknown     Modified by: zakacat
//
//This is the controller for the app.
//This activity includes methods
//- For lifecycle actions such as onCreate(), onResume(), and onPause()
//- To process the clicking of the add (+) button
//- To update the TextView with the list
//- To clear the list
//- To inflate the app bar menu and handle the action within it (only one - add)
//- To process a long press touch gesture
//**************************************************************************************************
public class MainActivity extends AppCompatActivity {

    private TextView mItemListTextView;
    private ToDoList mToDoList;
    private GestureDetectorCompat mDetector;

    //**********************************************************************************************
    //at onCreate(), the layout is synced, the textView is synced, a ToDoList object is instantiated,
    //and a GestureDetector object is instantiated.
    //**********************************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mItemListTextView = findViewById(R.id.itemList);

        mToDoList = new ToDoList(this);

        mDetector = new GestureDetectorCompat(this, new DiceGestureListener());

    }

    //**********************************************************************************************
    //onResume() will try to read from the local list file and then display that file. If there is an
    //issue reading the file, then an exception is thrown.
    //**********************************************************************************************
    @Override
    protected void onResume() {
        super.onResume();

        try {
            mToDoList.readFromFile();
            displayList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //**********************************************************************************************
    //onPause() will save the list to the file and will throw an exception if there is an issue.
    //**********************************************************************************************
    @Override
    protected void onPause() {
        super.onPause();

        try {
            mToDoList.saveToFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //**********************************************************************************************
    //addButtonClick() is actually called directly from the AddDialogFragment.java. The string that
    //user put into the dialog is passed into here. If there IS input then the input is added as an
    //item to the ToDoList object and then that list is displayed.
    //**********************************************************************************************
    public void addButtonClick(String input) {
        if (input.length() > 0) {
            mToDoList.addItem(input);
            displayList();
        }
    }

    //**********************************************************************************************
    //displayList() uses StringBuffer to modify text. for each item in the ToDoList object, a number
    //and a period is added to the front, then the string , then an escape sequence(\n) to skip to
    //the next line. The TextView is then updated with this information.
    //**********************************************************************************************
    private void displayList() {
        StringBuffer itemText = new StringBuffer();
        String[] items = mToDoList.getItems();
        for (int i = 0; i < items.length; i++) {
            itemText.append(i + 1).append(". ").append(items[i]).append("\n");
        }
        mItemListTextView.setText(itemText);
    }

    //**********************************************************************************************
    //clearButtonClick() clears the list of the ToDoList object and then calls displayLIst() again.
    //**********************************************************************************************
    public void clearButtonClick() {
        mToDoList.clear();
        displayList();
    }

    //**********************************************************************************************
    //onCreateOptionsMenu inflates a menu, referencing the xml file appbar_menu for the layout.
    //**********************************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //**********************************************************************************************
    //onOptionsItemSelected() handles the add button that is within the appbar menu. If the button
    //is clicked, then a FragmentManager object is created to handle an instance of AddDialogFragment.
    //The fragment is then displayed and returns true. If not then the item in the parameter is
    //sent up to be handled.
    //**********************************************************************************************
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add) {
            //Here is where we will add the info for the dialog fragment that has
            //all the necessary handling to add new things to the list.
            FragmentManager manager = getSupportFragmentManager();
            AddDialogFragment mDialogFragment = new AddDialogFragment();
            mDialogFragment.show(manager, AddDialogFragment.TAG);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    //**********************************************************************************************
    //This is a necessary method override needed for Gesture Detector.
    //onTouchEvent() takes in a motion event and calls a method to react to this.
    //**********************************************************************************************
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //******************************************************************************************
    //This  is an inner class which specifies how motion events are handled with this
    // instance of gesture detector.
    //******************************************************************************************
    private class DiceGestureListener implements GestureDetector.OnGestureListener {


        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        //******************************************************************************************
        //onLongPress() reads a long press from the user and calls clearButtonClick(). The
        //definition of this event is held elsewhere.
        //******************************************************************************************
        @Override
        public void onLongPress(MotionEvent e) {
            clearButtonClick();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}
