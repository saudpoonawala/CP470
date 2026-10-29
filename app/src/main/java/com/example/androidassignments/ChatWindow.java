package com.example.androidassignments;

import android.os.Bundle;
import android.content.Context;
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

    private ListView chatView;
    private EditText textInput;
    private Button sendButton;
    private ArrayList<String> chatMessages;

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

        ChatAdapter adapter = new ChatAdapter(this);
        chatView.setAdapter(adapter);

        sendButton.setOnClickListener(v -> {
            String message = textInput.getText().toString().trim();

            if (!isValidMessage(message)) {
                return;
            }

            chatMessages.add(message);
            chatMessages.add("Received: Thanks for your message!");

            adapter.notifyDataSetChanged();
            chatView.smoothScrollToPosition(chatMessages.size() - 1);
            textInput.setText("");
        });
    }

    public static boolean isValidMessage(String message) {
        return message != null && !message.trim().isEmpty();
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
