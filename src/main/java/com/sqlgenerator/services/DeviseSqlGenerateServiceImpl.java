package com.sqlgenerator.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.*;

public class DeviseSqlGenerateServiceImpl extends BaseSqlGeneratorService {

    @Override
    protected void generateSQLQueries(String excel, String sheetName) throws Exception {
        super.generateSQLQueries(excel,  sheetName);

        file_out_name = file_out_name.replace("$", "Devise");
        List<String> sqls = new ArrayList<>();
        sqls.add("TRUNCATE ${database_schema}.DEVISE ;");
        String holder =
                "INSERT INTO ${database_schema}.DEVISE (id, code_banque_associe, code_pays_associe, createdbyuser, createdon, updatedbyuser" +
                ", updatedon, version, code_alpha, code_iso, code_langue, code_local, devise_compensable," +
                " libelle, nombre_decimal, nbr_decimal_change, type, code_alpha2) " +
                "VALUES (nextval('${database_schema}.hibernate_sequence'), '${code_banque}', 'GN', 'bq1', '2021-01-13 17:42:21.113000', 'ncadmin'" +
                ", '2021-04-09 11:12:21.524000', 4, '$code_alpha', '$code_iso', '$langue', '$code_local', null, " +
                "'$libelle', 2, null, 'ALL', null);";

        for (Row row : sheet) {

            Cell codeIso = row.getCell(props.get("codeIndex"));
            Cell codeAlpha = row.getCell(props.get("codeAlpha"));
            Cell country = row.getCell(props.get("country"));
            boolean skipFirstLine = row.getRowNum() !=0 ;
            if (skipFirstLine) {

                sqls.add(
                        holder.replace("$code_alpha",  getCellValue(codeAlpha) )
                                .replace("$libelle",   getCellValue(codeAlpha) )
                                .replace("$code_iso",  getStringCodeOnNCharacters(codeIso ,"3" ) )
                                .replace("$code_local",getCellValue(country ) )
                                .replace("$langue", "fr")
                );

                sqls.add(
                        holder.replace("$code_alpha",   getCellValue(codeAlpha) )
                                .replace("$libelle",    getCellValue(codeAlpha) )
                                .replace("$code_iso",   getStringCodeOnNCharacters(codeIso ,"3" ) )
                                .replace("$code_local",getCellValue(country ) )
                                .replace("$langue", "en")
                );

            }
        }
        queries = sqls;
    }
    public void readProperties (){
        System.out.print("Please index of code iso ( start count from 0 )           : \t");
        String codeIndex = scanner.next();


        System.out.print("Please index of Alpha ( start count from 0 )          : \t");
        String Alpha = scanner.next();

        System.out.print("Please index of country ( start count from 0 )        : \t");
        String country = scanner.next();


        props.put("codeAlpha", Integer.valueOf(Alpha));
        props.put("country", Integer.valueOf(country));
        props.put("codeIndex", Integer.valueOf(codeIndex));



    }

}
