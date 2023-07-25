package com.sqlgenerator.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BanqueInternationalSqlGeneratorService extends BaseSqlGeneratorService {


    @Override
    protected void generateSQLQueries(String excel, String sheetName) throws Exception {
        super.generateSQLQueries(excel, sheetName);

        file_out_name = file_out_name.replace("$", "banque_International");
        List<String> sqls = new ArrayList<>();
        sqls.add("TRUNCATE ${database_schema}.banque_international ;");
        String holder = "INSERT INTO ${database_schema}.banque_international (id, code_banque_associe, code_pays_associe, createdbyuser, createdon, updatedbyuser, updatedon, version, code_banque, code_agence_banque, label, devise_banque, country, swift_id, addres1, addres2, addres3, is_remboursable, cle_rma) VALUES\n" +
                "(nextval('${database_schema}.hibernate_sequence'), '${code_banque}', '${code_pays}', null, null, null,'2021-12-21 16:28:57.774293', null, '$codeBanque', null,'$libelle', null, '$country', '$swift_id', '$add1', '$add2', '$add3', false, null);";

        for (Row row : sheet) {

            Cell codeBanque = row.getCell(props.get("codeBanque"));
            Cell libelle = row.getCell(props.get("libelle"));
            Cell country = row.getCell(props.get("country"));
            Cell swift_id = row.getCell(props.get("swift_id"));
            Cell add1 = row.getCell(props.get("add1"));
            Cell add2 = row.getCell(props.get("add2"));
            Cell add3 = row.getCell(props.get("add3"));

            boolean skipFirstLine = row.getRowNum() != 0;

            if
            (
                    skipFirstLine && codeBanque != null && country != null && libelle != null && swift_id != null
            ) {

                sqls.add(
                        holder
                                .replace("$codeBanque", getStringCodeOnNCharacters(codeBanque,"5"))

                                .replace("$libelle", getCellValue(libelle))

                                .replace("$country", getCellValue(country))

                                .replace("$swift_id", getCellValue(swift_id))

                                .replace("$add1", getCellValue(add1))

                                .replace("$add2", getCellValue(add2))

                                .replace("$add3", getCellValue(add3))
                );


            }
        }
        queries = sqls;

    }


    public void readProperties() {
        Map<String, Integer> map = new HashMap<>();

        System.out.print("Please index of swift_id ( start count from 0 )                : \t");
        map.put("swift_id", Integer.valueOf(scanner.next()));

        System.out.print("Please index of codeBanque ( start count from 0 )                : \t");
        map.put("codeBanque", Integer.valueOf(scanner.next()));

        System.out.print("Please index of country ( start count from 0 )        : \t");
        map.put("country", Integer.valueOf(scanner.next()));

        System.out.print("Please index of add1 ( start count from 0 )           : \t");
        map.put("add1", Integer.valueOf(scanner.next()));

        System.out.print("Please index of add2 ( start count from 0 )           : \t");
        map.put("add2", Integer.valueOf(scanner.next()));

        System.out.print("Please index of code ( start count from 0 )           : \t");
        map.put("add3", Integer.valueOf(scanner.next()));

        System.out.print("Please index of libelle ( start count from 0 )        : \t");
        map.put("libelle", Integer.valueOf(scanner.next()));

        this.props = map;

    }
}
