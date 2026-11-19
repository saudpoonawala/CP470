package com.example.androidassignments;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    // Test 1: Check application context

    @Test
    public void useAppContext() {
        Context context = InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext();

        assertEquals(
                "com.example.androidassignments",
                context.getPackageName()
        );
    }

    // Test 2: Check ChatWindow opens

    @Test
    public void chatWindow_opensWithRequiredViews() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();

        Intent intent = new Intent(
                instrumentation.getTargetContext(),
                ChatWindow.class
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Activity activity = instrumentation.startActivitySync(intent);

        try {
            instrumentation.runOnMainSync(() -> {
                assertNotNull(activity.findViewById(R.id.chatView));
                assertNotNull(activity.findViewById(R.id.textInput));
                assertNotNull(activity.findViewById(R.id.sendButton));
            });
        } finally {
            instrumentation.runOnMainSync(activity::finish);
        }
    }

    // Test 3: Check sending a chat message

    @Test
    public void chatWindow_sendingMessageUpdatesListAndClearsInput() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();

        Intent intent = new Intent(
                instrumentation.getTargetContext(),
                ChatWindow.class
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Activity activity = instrumentation.startActivitySync(intent);

        try {
            instrumentation.runOnMainSync(() -> {
                EditText input =
                        activity.findViewById(R.id.textInput);

                Button send =
                        activity.findViewById(R.id.sendButton);

                ListView list =
                        activity.findViewById(R.id.chatView);

                int previousCount = list.getAdapter().getCount();

                input.setText("Hello");
                send.performClick();

                assertEquals(
                        previousCount + 2,
                        list.getAdapter().getCount()
                );

                assertEquals(
                        "Hello",
                        list.getAdapter().getItem(previousCount)
                );

                assertEquals(
                        "Received: Thanks for your message!",
                        list.getAdapter().getItem(previousCount + 1)
                );

                assertEquals("", input.getText().toString());
            });
        } finally {
            instrumentation.runOnMainSync(activity::finish);
        }
    }

    // Test 4: Check LoginActivity

    @Test
    public void loginActivity_opensWithRequiredViews() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();

        Intent intent = new Intent(
                instrumentation.getTargetContext(),
                LoginActivity.class
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Activity activity = instrumentation.startActivitySync(intent);

        try {
            instrumentation.runOnMainSync(() -> {
                assertNotNull(activity.findViewById(R.id.loginName));
                assertNotNull(activity.findViewById(R.id.password));
                assertNotNull(activity.findViewById(R.id.loginButton));
            });
        } finally {
            instrumentation.runOnMainSync(activity::finish);
        }
    }

    // Test 5: Check MainActivity buttons

    @Test
    public void mainActivity_opensWithRequiredButtons() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();

        Intent intent = new Intent(
                instrumentation.getTargetContext(),
                MainActivity.class
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Activity activity = instrumentation.startActivitySync(intent);

        try {
            instrumentation.runOnMainSync(() -> {
                Button listButton =
                        activity.findViewById(R.id.button);

                Button chatButton =
                        activity.findViewById(R.id.startChatButton);

                assertNotNull(listButton);
                assertNotNull(chatButton);

                assertTrue(listButton.isEnabled());
                assertTrue(chatButton.isEnabled());
            });
        } finally {
            instrumentation.runOnMainSync(activity::finish);
        }
    }

    // Test 6: Check ListItemsActivity

    @Test
    public void listItemsActivity_controlsWorkCorrectly() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();

        Intent intent = new Intent(
                instrumentation.getTargetContext(),
                ListItemsActivity.class
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Activity activity = instrumentation.startActivitySync(intent);

        try {
            instrumentation.runOnMainSync(() -> {
                assertNotNull(
                        activity.findViewById(R.id.radioButton)
                );

                assertNotNull(
                        activity.findViewById(R.id.checkBox)
                );

                assertNotNull(
                        activity.findViewById(R.id.imageButton)
                );

                SwitchCompat toggle =
                        activity.findViewById(R.id.switchButton);

                assertNotNull(toggle);

                toggle.setChecked(true);
                assertTrue(toggle.isChecked());

                toggle.setChecked(false);
                assertFalse(toggle.isChecked());
            });
        } finally {
            instrumentation.runOnMainSync(activity::finish);
        }
    }

    // Test 7: Check toolbar and menu items

    @Test
    public void toolbar_hasRequiredViewsAndMenuItems() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();

        Intent intent = new Intent(
                instrumentation.getTargetContext(),
                TestToolbar.class
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Activity activity = instrumentation.startActivitySync(intent);

        try {
            instrumentation.runOnMainSync(() -> {
                Toolbar toolbar =
                        activity.findViewById(R.id.toolbar);

                assertNotNull(toolbar);

                assertNotNull(
                        activity.findViewById(R.id.fab)
                );

                assertNotNull(
                        toolbar.getMenu().findItem(R.id.choice1)
                );

                assertNotNull(
                        toolbar.getMenu().findItem(R.id.choice2)
                );

                assertNotNull(
                        toolbar.getMenu().findItem(R.id.choice3)
                );

                assertNotNull(
                        toolbar.getMenu().findItem(R.id.about)
                );
            });
        } finally {
            instrumentation.runOnMainSync(activity::finish);
        }
    }

    // Test 8: SQLite - Save and read messages

    @Test
    public void database_canSaveAndReadMessage() {
        SQLiteDatabase db = SQLiteDatabase.create(null);

        try {
            ChatDatabaseHelper helper =
                    new ChatDatabaseHelper(
                            InstrumentationRegistry
                                    .getInstrumentation()
                                    .getTargetContext()
                    );

            helper.onCreate(db);

            ContentValues values = new ContentValues();

            values.put(
                    ChatDatabaseHelper.KEY_MESSAGE,
                    "Hello SQLite"
            );

            long id = db.insert(
                    ChatDatabaseHelper.TABLE_NAME,
                    null,
                    values
            );

            assertTrue(id > 0);

            try (Cursor cursor = db.query(
                    ChatDatabaseHelper.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            )) {
                assertEquals(1, cursor.getCount());
                assertEquals(2, cursor.getColumnCount());

                assertTrue(cursor.moveToFirst());

                String message = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                ChatDatabaseHelper.KEY_MESSAGE
                        )
                );

                assertEquals("Hello SQLite", message);
            }

        } finally {
            db.close();
        }
    }

    // Test 9: SQLite - Upgrade deletes old messages

    @Test
    public void database_upgradeDeletesOldMessages() {
        SQLiteDatabase db = SQLiteDatabase.create(null);

        try {
            ChatDatabaseHelper helper =
                    new ChatDatabaseHelper(
                            InstrumentationRegistry
                                    .getInstrumentation()
                                    .getTargetContext()
                    );

            helper.onCreate(db);

            ContentValues values = new ContentValues();

            values.put(
                    ChatDatabaseHelper.KEY_MESSAGE,
                    "Old message"
            );

            db.insert(
                    ChatDatabaseHelper.TABLE_NAME,
                    null,
                    values
            );

            helper.onUpgrade(db, 1, 2);

            try (Cursor cursor = db.query(
                    ChatDatabaseHelper.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            )) {
                assertEquals(0, cursor.getCount());
            }

        } finally {
            db.close();
        }
    }
}
