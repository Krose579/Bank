package com.krose.bank;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class Database {
    private static ObjectMapper mapper;
    private static ObjectWriter writer;
    private static File saveDirectory;
    private static File accountsFile;

    static {
        mapper = new ObjectMapper();
        writer = mapper.writer(new DefaultPrettyPrinter());
        saveDirectory = new File(System.getProperty("user.home"), ".krose");
        accountsFile = new File (saveDirectory, "accounts.json");
    }

    public static void save (List<Account> accounts) throws IOException {
        if(saveDirectory.mkdirs()) System.out.println("Created new directory.");
        if(accountsFile.createNewFile()) System.out.println("Created new file.");
        writer.writeValue(accountsFile, accounts);
    }

    public static List<Account> load () throws IOException {
        if (!accountsFile.exists()) return new ArrayList<>();
        return new ArrayList<>(List.of(mapper.readValue(accountsFile,  Account[].class)));
    }
}