package com.example.androidassignments;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {

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
}
