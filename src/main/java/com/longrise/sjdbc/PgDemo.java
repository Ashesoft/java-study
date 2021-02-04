package com.longrise.sjdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PgDemo {
  private static final String URL = "jdbc:postgresql://10.184.72.103:5433/TEAMNEW";
  private static final String UNAME = "postgres";
  private static final String UPWD = "postgres";
  private static Map<String, String> lssb = new HashMap<>(14200);

  public static void main(String[] args) {
    connectionPg("D:\\Desktop\\vv.xlsx");
  }

  public static void connectionPg(String path) {
    String sql = "select id, sqrsfz from lsp_lssbtemp c where not exists (select 1  from lsp_lssbtemp  where sqrsfz = c.sqrsfz and createtime > c.createtime)";
    try {
      Class.forName("org.postgresql.Driver");
      try (Connection connection = DriverManager.getConnection(URL, UNAME, UPWD);
          PreparedStatement prepareStatement = connection.prepareStatement(sql);
          ResultSet rs = prepareStatement.executeQuery();) {
        while (rs.next()) {
          lssb.put(rs.getString("sqrsfz"), rs.getString("id"));
        }
        // getTxDataByExecl(connection, path); //同行
        getTzlsDataByExecl(connection, path); // 同住留深
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static XSSFWorkbook getExcelFile(String path) throws IOException {
    File file = new File(path);
    if (!file.isFile() && !file.exists()) {
      System.out.println("不是文件或文件不存在");
    }
    FileInputStream fIP = new FileInputStream(file);
    return new XSSFWorkbook(fIP);
  }

  public static void getTxDataByExecl(Connection connection, String path) {
    try ( 
      XSSFWorkbook workbook = getExcelFile(path);
      PreparedStatement ps = connection.prepareStatement("insert into lsp_lssbryxx (id, creator, createtime, sbid, pname, pzjlx, psfz, pdh, txrsfjhfs, txrjhfssj, lx) values (?, ?, now(), ?, ?, ?, ?, ?, ?, ?, 'tx')")
    ){ 
      XSSFSheet sheet = workbook.getSheet("同行人员信息");
      for (Row row : sheet) {
        if("id".equals(row.getCell(0).toString())){
          continue;
        }
        ps.setString(1, row.getCell(0).toString());
        ps.setString(2, row.getCell(1).toString());
        //ps.setTimestamp(3, new Timestamp(row.getCell(2).getDateCellValue().getTime()));
        ps.setString(4, lssb.get(row.getCell(3).toString()));
        ps.setString(5, row.getCell(4).toString());
        ps.setInt(6, (int) row.getCell(5).getNumericCellValue());
        ps.setString(7, row.getCell(6).toString());
        ps.setString(8, row.getCell(7).toString());
        ps.setInt(9, (int)row.getCell(8).getNumericCellValue());
        ps.setTimestamp(10, new Timestamp(row.getCell(9).getDateCellValue().getTime()));
        ps.addBatch();
      }
      ps.executeBatch(); 
      System.out.println("=============");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void getTzlsDataByExecl(Connection connection, String path) {
    try ( 
      XSSFWorkbook workbook = getExcelFile(path);
      PreparedStatement ps = connection.prepareStatement("insert into lsp_lssbryxx (id, creator, createtime, sbid, pname, pzjlx, psfz, pdh, lx) values (?, ?, now(), ?, ?, ?, ?, ?, 'ls')")
    ){ 
      XSSFSheet sheet = workbook.getSheet("同住留深人员信息");
      for (Row row : sheet) {
        if("id".equals(row.getCell(0).toString())){
          continue;
        }
        ps.setString(1, row.getCell(0).toString());
        ps.setString(2, row.getCell(1).toString());
        //ps.setTimestamp(3, new Timestamp(row.getCell(2).getDateCellValue().getTime()));
        ps.setString(4, lssb.get(row.getCell(3).toString()));
        ps.setString(5, row.getCell(4).toString());
        ps.setInt(6, (int) row.getCell(5).getNumericCellValue());
        ps.setString(7, row.getCell(6).toString());
        ps.setString(8, row.getCell(7).toString());
        ps.addBatch();
      }
      ps.executeBatch(); 
      System.out.println("=============");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // 生成id
  public static void ss(){
    for (int i = 1; i <= 3707; i++) {
        String ss = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(ss);
    }
  }
}