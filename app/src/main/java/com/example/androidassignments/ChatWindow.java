package com.example.androidassignments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "ChatWindow";

    private ListView chatView;
    private EditText textInput;
    private Button sendButton;
    private ArrayList<String> chatMessages;

    private SQLiteDatabase database;
    private ChatDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_window);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );
                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                }
        );

        chatView = findViewById(R.id.chatView);
        textInput = findViewById(R.id.textInput);
        sendButton = findViewById(R.id.sendButton);

        chatMessages = new ArrayList<>();

        // Open the database.
        databaseHelper = new ChatDatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        // Load previously saved messages.
        Cursor cursor = database.query(
                ChatDatabaseHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ChatDatabaseHelper.KEY_ID + " ASC"
        );

        Log.i(ACTIVITY_NAME,
                "Cursor's column count = " + cursor.getColumnCount());

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME,
                    "Column name: " + cursor.getColumnName(i));
        }

        int messageColumn = cursor.getColumnIndexOrThrow(
                ChatDatabaseHelper.KEY_MESSAGE
        );

        while (cursor.moveToNext()) {
            String message = cursor.getString(messageColumn);
            chatMessages.add(message);

            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + message);
        }

        cursor.close();

        ChatAdapter adapter = new ChatAdapter(this);
        chatView.setAdapter(adapter);

        sendButton.setOnClickListener(v -> {
            String message = textInput.getText().toString().trim();

            if (!isValidMessage(message)) {
                return;
            }

            // Save the user's message.
            saveMessage(message);
            chatMessages.add(message);

            // Save the automatic reply too.
            String reply = "Received: Thanks for your message!";
            saveMessage(reply);
            chatMessages.add(reply);

            adapter.notifyDataSetChanged();
            chatView.smoothScrollToPosition(chatMessages.size() - 1);
            textInput.setText("");
        });
    }

    // Insert a message into the SQLite database.
    private void saveMessage(String message) {
        ContentValues values = new ContentValues();
        values.put(ChatDatabaseHelper.KEY_MESSAGE, message);

        database.insert(
                ChatDatabaseHelper.TABLE_NAME,
                null,
                values
        );
    }

    public static boolean isValidMessage(String message) {
        return message != null && !message.trim().isEmpty();
    }

    @Override
    protected void onDestroy() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }
        super.onDestroy();
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return chatMessages.size();
        }

        @Override
        public String getItem(int position) {
            return chatMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(
                int position,
                View convertView,
                ViewGroup parent
        ) {
            int layoutId = (position % 2 == 0)
                    ? R.layout.chat_row_incoming
                    : R.layout.chat_row_outgoing;

            View row = getLayoutInflater().inflate(
                    layoutId,
                    parent,
                    false
            );

            TextView messageView =
                    row.findViewById(R.id.messageText);

            messageView.setText(getItem(position));

            return row;
        }
    }
}
