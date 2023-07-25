package com.sqlgenerator.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

public abstract class BaseSqlGeneratorService {
    Sheet sheet;

    String file_out_name = "$_&.sql";

    List<String> queries;

    static Scanner scanner = new Scanner(System.in);

    @Value("${app.sql.output.path}")
    String outputPath;

    Map<String, Integer> props = new HashMap<>();
    private static final String OUTPUT_PATH_FILE = "D:\\SideProject\\untitled\\src\\main\\resources\\";

    protected void generateSQLQueries(String excel, String sheetName) throws Exception {
        sheet = getSheet(excel, sheetName);
        readProperties();
        recap(excel, sheetName);
    }

    protected Sheet getSheet(String fileName, String sheetName) throws IOException {

        Sheet sheet = null;
        Workbook workbook;
        try (FileInputStream file = new FileInputStream(fileName)) {

            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);

        } catch (Exception e) {
            e.printStackTrace();
            throw  e ;
        }
        return sheet;
    }

    public void sqlToTXT(String fileName, List<String> tobeFound) {
        try (PrintStream out = new PrintStream(Files.newOutputStream(Paths.get(outputPath + fileName)))) {
            System.out.println("in progress");
            tobeFound.forEach(out::println);
            System.out.println("Done");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generate(String bankCode, String excel, String sheetName, Map<String, Integer> properties) throws IOException {
        try {
            generateSQLQueries(excel, sheetName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        file_out_name = file_out_name.replace("&", String.valueOf(Instant.now().toEpochMilli()));
        sqlToTXT(file_out_name, queries);
    }

    public void readProperties() {
    }

    private void recap(String filePath, String sheetName) throws Exception {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("| Recap:");
        System.out.println("| Technical File Path   : " + filePath);
        System.out.println("| Sheet Name            : " + sheetName);
        props.forEach((key, val) -> System.out.println("| " + key + "          : " + val));
        System.out.println("-----------------------------------------------------------------");
        System.out.print("\nProcced Y/N : ");
        String forward = scanner.next();

        if (!forward.toUpperCase().equals("Y"))
            throw new Exception("Operation aborted");
        scanner.close();

    }


    public String getStringCodeOnNCharacters(Cell codeCell, String n) {
        String code = getCellValue(codeCell);
        if (code.length() != 5) {

            //Remove decimals
            code = code.replaceAll("\\..*", "");

            // Add leading zeros
            code = String.format("%0" + n + "d", Integer.parseInt(code));
        }

        return code;
    }


    public String getCellValue(Cell codeCell) {
        String code = "";
        if (codeCell == null) return code;
        CellType cellType = codeCell.getCellType();
        if (Objects.requireNonNull(cellType) == CellType.NUMERIC) {
            code = String.valueOf(codeCell.getNumericCellValue()).replaceAll("\\..*", "");
        } else if (cellType == CellType.STRING) {
            code = codeCell.getStringCellValue();
        }
        if (code.contains("null"))
            code = "";
        return code.replace("'", "''")
                .replace(" ", "").trim();
    }
}
