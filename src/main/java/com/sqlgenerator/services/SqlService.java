package com.sqlgenerator.services;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SqlService {
    static Scanner scanner = new Scanner(System.in);

    public static void sqlGenerator() throws Exception {


        Map<String, Integer> map = new HashMap<>();

        System.out.println("##################### Welcome to sql generator ##################");
        System.out.print("\nplease give me the absolut path for technical file    : \t");

        String filePath = scanner.next();
        System.out.print("\n\n" +
                "Generate SQL queries for \n\n" +
                "[BRANCHES   -> 1]  \n" +
                "[CURRENCIES -> 2]  \n" +
                "[BANQUE INT -> 3]  \n\n" +
                "[BANQUE DOM -> 4]  \n\n" +
                "Enter a value: \t");
        String tableType = scanner.next();
        System.out.print("please provide with the exact sheet name              : \t");
        String sheetName = scanner.next();


        BaseSqlGeneratorService baseSqlGeneratorService;
        if ("1".equals(tableType)) {
            baseSqlGeneratorService = instantiateService(BranchSqlGeneratorServiceImpl.class);
        } else if ("2".equals(tableType)) {
            baseSqlGeneratorService = instantiateService(DeviseSqlGenerateServiceImpl.class);
        } else if ("3".equals(tableType)) {
            baseSqlGeneratorService = instantiateService(BanqueInternationalSqlGeneratorService.class);
        }else if ("4".equals(tableType)) {
            baseSqlGeneratorService = instantiateService(BanqueDomesticSqlGeneratorService.class);
        } else {
            System.out.print("Invalid table type provided.");
            return;
        }


        assert baseSqlGeneratorService != null;
        baseSqlGeneratorService.
                generate("", filePath, sheetName, map);

    }


    private static BaseSqlGeneratorService instantiateService(Class<? extends BaseSqlGeneratorService> serviceClass) {
        try {
            // Instantiate the service object
            Constructor<? extends BaseSqlGeneratorService> constructor = serviceClass.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            System.out.print("Failed to instantiate the service class: " + serviceClass.getName());
        }

        return null;
    }

    public static void clearTerminal() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Clear command for Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Clear command for Linux and macOS
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


