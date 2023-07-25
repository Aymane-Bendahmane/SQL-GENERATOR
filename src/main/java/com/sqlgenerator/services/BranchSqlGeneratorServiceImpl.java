package com.sqlgenerator.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class BranchSqlGeneratorServiceImpl extends BaseSqlGeneratorService {

    @Override
    protected void generateSQLQueries(String excel, String sheetName) throws Exception {
        super.generateSQLQueries(excel, sheetName);
        file_out_name = file_out_name.replace("$", "Agence");
        List<String> sqls = new ArrayList<>();
        sqls.add("Delete from ${database_schema}.table_codification where classe = 'Agence' ;");
        String holder = "INSERT INTO ${database_schema}.table_codification (id, version, code, code_langue, libelle, class, code_nature_remise,\n" +
                "                                                   adresse1, adresse2,\n" +
                "                                                   adresse3, code_postal, pays, ville, description, libelle_court,\n" +
                "                                                   nature_credit,\n" +
                "                                                   codeswift, code_famille, code_devise_alpha, nombre_decimal,\n" +
                "                                                   devise_id, lib,\n" +
                "                                                   code_banque_associe, code_pays_associe, createdbyuser, createdon,\n" +
                "                                                   updatedbyuser,\n" +
                "                                                   updatedon, autorise, classe, plafond, visible, code_segment_associe,\n" +
                "                                                   is_default,\n" +
                "                                                   status_code, rma)\n" +
                "values (nextval('${database_schema}.hibernate_sequence'), 1, '$codeAgence', '$langue', '$nameAgence',\n" +
                "        'TableCodification', null, null,\n" +
                "        null, null, null,\n" +
                "        '$country', null, null,\n" +
                "        null, null, null, null, null, null, null, null, '${code_banque}', '${code_pays}', null, null, null, null, null, 'Agence',\n" +
                "        null,\n" +
                "        null, null, null, null, null);";

        for (Row row : sheet) {
            Cell codeCell = row.getCell(props.get("codeIndex"));
            Cell libelleCell = row.getCell(props.get("libelleindex"));
            Cell countryCell = row.getCell(props.get("country"));
            boolean skipFirstLine = row.getRowNum() != 0;
            if (skipFirstLine && codeCell != null && libelleCell != null) {
                sqls.add(
                        holder.replace("$nameAgence", getCellValue(libelleCell))
                                .replace("$codeAgence", getStringCodeOnNCharacters(codeCell,"5"))
                                .replace("$country", getCellValue(countryCell))
                                .replace("$langue", "fr")
                );
                sqls.add(
                        holder.replace("$nameAgence", getCellValue(libelleCell))
                                .replace("$codeAgence", getStringCodeOnNCharacters(codeCell,"5"))
                                .replace("$country", getCellValue(countryCell))
                                .replace("$langue", "en")
                );
            }
        }

        queries = sqls;

    }





    public void readProperties (){

        System.out.print("Please index of code ( start count from 0 )           : \t");
        String codeIndex = scanner.next();

        System.out.print("Please index of libelle ( start count from 0 )        : \t");
        String libelle = scanner.next();


        System.out.print("Please index of country ( start count from 0 )        : \t");
        String country = scanner.next();


        props.put("country", Integer.valueOf(country));
        props.put("codeIndex", Integer.valueOf(codeIndex));
        props.put("libelleindex", Integer.valueOf(libelle));


    }
}
