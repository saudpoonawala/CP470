package com.example.androidassignments;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        Context appContext =
                InstrumentationRegistry.getInstrumentation()
                        .getTargetContext();

        assertEquals(
                "com.example.androidassignments",
                appContext.getPackageName()
        );
    }

    @Test
    public void chatWindow_opensWithRequiredViews() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();

        Intent intent = new Intent(
                instrumentation.getTargetContext(),
                ChatWindow.class
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Activity activity =
                instrumentation.startActivitySync(intent);

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

    @Test
    public void chatWindow_sendingMessageUpdatesListAndClearsInput() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();

        Intent intent = new Intent(
                instrumentation.getTargetContext(),
                ChatWindow.class
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Activity activity =
                instrumentation.startActivitySync(intent);

        try {
            instrumentation.runOnMainSync(() -> {
                EditText input =
                        activity.findViewById(R.id.textInput);
                Button send =
                        activity.findViewById(R.id.sendButton);
                ListView list =
                        activity.findViewById(R.id.chatView);

                input.setText("Hello");
                send.performClick();

                assertEquals(2, list.getAdapter().getCount());
                assertEquals("Hello",
                        list.getAdapter().getItem(0));
                assertEquals(
                        "Received: Thanks for your message!",
                        list.getAdapter().getItem(1)
                );
                assertEquals("", input.getText().toString());
            });
        } finally {
            instrumentation.runOnMainSync(activity::finish);
        }
    }

    @Test
    public void loginActivity_opensWithRequiredViews() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();

        Intent intent = new Intent(
                instrumentation.getTargetContext(),
                LoginActivity.class
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Activity activity =
                instrumentation.startActivitySync(intent);

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

    @Test
    public void mainActivity_opensWithRequiredButtons() {
        Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();

        Intent intent = new Intent(
                instrumentation.getTargetContext(),
                MainActivity.class
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Activity activity =
                instrumentation.startActivitySync(intent);

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
                assertNotNull(activity.findViewById(R.id.radioButton));
                assertNotNull(activity.findViewById(R.id.checkBox));
                assertNotNull(activity.findViewById(R.id.imageButton));

                androidx.appcompat.widget.SwitchCompat toggle =
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


}
