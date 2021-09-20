package com.krose.io;

import com.krose.Input;
import com.krose.Output;

public class NewAccountMenuIO implements IMenu {
    private static final String MENU_TITLE = "Create New Account";
    private static final String[] MENU_ITEMS = new String[] {
            "Checking",
            "Savings"
    };

    private final MenuIO menuIO;

    public NewAccountMenuIO(Input input, Output output) {
        this.menuIO = new MenuIO(input, output, MENU_TITLE, MENU_ITEMS);
    }

    @Override
    public int getMenuChoice() {
        return menuIO.getMenuChoice();
    }
}
