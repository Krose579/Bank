package com.krose.io;

import com.krose.Input;
import com.krose.Output;

public class MenuIO implements IMenu {
    private Input input;
    private Output output;
    private String title;
    private String[] menuItems;

    public MenuIO(Input input, Output output, String title, String[] menuItems) {
        this.input = input;
        this.output = output;
        this.title = title;
        this.menuItems = menuItems;
    }

    public int getMenuChoice() {
        displayMenu();
        return getChoiceInput();
    }

    private void displayMenu() {
        output.writeDivider();
        output.write(title, Output.Alignment.CENTER);
        output.writeDivider();
        for(int i = 0; i < menuItems.length; i++) {
            output.write(String.format("%d:\t%s", i + 1, menuItems[i]));
        }
        output.writeDivider();
    }

    private int getChoiceInput() {
        int choice = 0;
        while (true) {
            output.write("Choose a Menu Option:");
            choice = input.getInteger();
            if (choice > 0 && choice <= menuItems.length) break;
            else output.write("Invalid choice.");
        }
        return choice;
    }
}
