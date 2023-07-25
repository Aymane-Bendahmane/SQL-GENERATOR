package com.sqlgenerator.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BanqueDomesticSqlGeneratorService extends BaseSqlGeneratorService{

    @Override
    protected void generateSQLQueries(String excel, String sheetName) throws Exception {
        super.generateSQLQueries(excel, sheetName);

        file_out_name = file_out_name.replace("$", "BANQUE_CONFRERE");
        List<String> sqls = new ArrayList<>();
        sqls.add("TRUNCATE ${database_schema}.banque_confrere ;");
        sqls.add("INSERT INTO ${database_schema}.banque_confrere (id, version, code_banque_associe, code_pays_associe, createdbyuser,\n" +
                "                                                createdon,\n" +
                "                                                updatedbyuser, updatedon, code_bic, statut, reference, name_bank,\n" +
                "                                                adress_bank,\n" +
                "                                                code_bank, branch_name, branch_code, wallet_institution, routing_code)\n" +
                "VALUES ");
        String holder = "(nextval('${database_schema}.hibernate_sequence'), 0, '${code_banque}', '${code_pays}', null, null, null,'2021-12-21 16:28:45.913171', '$bic', null, null,'$name_bank', null, '$code_bank', '$name_branch', '$code_branch', false, 'false'),";

        for (Row row : sheet) {

            Cell name_bank      = row.getCell(props.get("name_bank"));
            Cell code_bank      = row.getCell(props.get("code_bank"));
            Cell name_branch    = row.getCell(props.get("name_branch"));
            Cell code_branch    = row.getCell(props.get("code_branch"));
            Cell bic            = row.getCell(props.get("bic"));


            boolean skipFirstLine = row.getRowNum() != 0;

            if
            (
                    skipFirstLine
                            &&
                    (
                            sqls.stream().noneMatch (s -> (s.contains(getCellValue(code_bank)) && s.contains(getCellValue(code_branch)))))
                    )
                    {
                        sqls.add(
                            holder
                                    .replace("$name_bank", getCellValue(name_bank))

                                    .replace("$code_bank", getCellValue(code_bank))

                                    .replace("$name_branch", getCellValue(name_branch))

                                    .replace("$code_branch", getCellValue(code_branch))

                                    .replace("$bic", getCellValue(bic))

                    );


            }
        }
        queries = sqls;

    }

    public void readProperties (){
        System.out.print("Please index of name_bank ( start count from 0 )           : \t");
        String name_bank = scanner.next();

        System.out.print("Please index of code_bank ( start count from 0 )        : \t");
        String code_bank = scanner.next();

        System.out.print("Please index of name_branch ( start count from 0 )          : \t");
        String name_branch = scanner.next();

        System.out.print("Please index of code_branch ( start count from 0 )        : \t");
        String code_branch = scanner.next();

        System.out.print("Please index of bic ( start count from 0 )        : \t");
        String bic = scanner.next();


        props.put("name_branch", Integer.valueOf(name_branch));
        props.put("code_branch", Integer.valueOf(code_branch));
        props.put("name_bank", Integer.valueOf(name_bank));
        props.put("code_bank", Integer.valueOf(code_bank));
        props.put("bic", Integer.valueOf(bic));


    }
}
