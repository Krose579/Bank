package com.krose.io;

import com.krose.Input;
import com.krose.Output;

public class MainMenuIO implements IMenu {
    private static final String MENU_TITLE = "Main Menu";
    private static final String[] MENU_ITEMS = new String[] {
            "Create New Account",
            "Deposit",
            "Withdraw",
            "Apply Interest",
            "Exit"
    };

    private final MenuIO menuIO;

    public MainMenuIO(Input input, Output output) {
        this.menuIO = new MenuIO(input, output, MENU_TITLE, MENU_ITEMS);
    }

    @Override
    public int getMenuChoice() {
        return menuIO.getMenuChoice();
    }
}
