# Soding Assessment App

This is my submission for Android developer vacancy assessment at Soding

## Getting Started

Install the Apk on Android device

### Prerequisites

Minimum SDK is Android 15

## Running the tests

TaskInstrumentedTest.java are automated tests for DB interactions
MainActivityInstrumentedTest.java are automated tests for UI interactions

DB must be empty before running the tests

## App walkthrough

The App consists of a single activity (MainActivity), which shows the user a 
[RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html) containing all tasks in the DB. DB name is Tasks_DB.db (for live)
and Tasks_Test_DB.db (for testing).

A [FloatingActionButton](https://developer.android.com/reference/android/support/design/widget/FloatingActionButton.html) is used to add a new Task to
the DB. It displays a dialog for user to enter Name and description of the task
(both mandatory) and when user clicks the save button, the task is added to the 
DB and displayed in the RecyclerView. Each task is displayed in the RecyclerView
with first line representing the Name, second line representing the description,
third line shows when task was created and last line representing last update 
timestamp. Each Task has an Edit and Delete [ImageButton](https://developer.android.com/reference/android/widget/ImageButton.html). The Edit opens up
dialog with this task's data to edit and saved when Save button is pressed. Delete
button removes the task from DB.