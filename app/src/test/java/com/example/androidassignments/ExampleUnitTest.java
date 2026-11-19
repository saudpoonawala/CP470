package com.example.androidassignments;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    // Assignment 2 - Message validation tests

    @Test
    public void validMessage_isAccepted() {
        assertTrue(ChatWindow.isValidMessage("Hello"));
    }

    @Test
    public void emptyMessage_isRejected() {
        assertFalse(ChatWindow.isValidMessage(""));
    }

    @Test
    public void spacesOnlyMessage_isRejected() {
        assertFalse(ChatWindow.isValidMessage("   "));
    }

    @Test
    public void nullMessage_isRejected() {
        assertFalse(ChatWindow.isValidMessage(null));
    }

    @Test
    public void messageWithSurroundingSpaces_isAccepted() {
        assertTrue(ChatWindow.isValidMessage("  Hello  "));
    }

    // Assignment 2 Part 4 - Toolbar tests

    @Test
    public void toolbar_defaultMessageIsCorrect() {
        ToolbarMessageState state = new ToolbarMessageState();

        assertEquals("You selected item 1", state.getMessage());
    }

    @Test
    public void toolbar_messageCanBeUpdated() {
        ToolbarMessageState state = new ToolbarMessageState();

        state.setMessage("Hello CP470");

        assertEquals("Hello CP470", state.getMessage());
    }

    @Test
    public void toolbar_latestMessageIsDisplayed() {
        ToolbarMessageState state = new ToolbarMessageState();

        state.setMessage("First message");
        state.setMessage("Updated message");

        assertEquals("Updated message", state.getMessage());
    }

    // Assignment 3 - Additional tests

    @Test
    public void tabsOnlyMessage_isRejected() {
        assertFalse(ChatWindow.isValidMessage("\t\t"));
    }

    @Test
    public void databaseName_isCorrect() {
        assertEquals("Messages.db",
                ChatDatabaseHelper.DATABASE_NAME);
    }

    @Test
    public void databaseTableName_isCorrect() {
        assertEquals("MESSAGES",
                ChatDatabaseHelper.TABLE_NAME);
    }

    @Test
    public void databaseColumns_areCorrect() {
        assertEquals("_id", ChatDatabaseHelper.KEY_ID);
        assertEquals("MESSAGE", ChatDatabaseHelper.KEY_MESSAGE);
    }
}
