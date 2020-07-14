package com.longrise.sjdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetPK {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/springboot?charset=utf8mb4&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String UNAME = "root";
    private static final String UPWD = "root";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, UNAME, UPWD);
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet pkInfo = metaData.getPrimaryKeys(null, "%", "users");
        System.out.println(pkInfo == null);
        while (pkInfo.next()) {
            System.out.print("数据库名称:" + pkInfo.getString("TABLE_CAT") + "  ");
            System.out.print("表名称:" + pkInfo.getString("TABLE_NAME") + "  ");
            System.out.print("主键列的名称:" + pkInfo.getString("COLUMN_NAME") + "  ");
            System.out.println("类型:" + pkInfo.getString("PK_NAME"));
        }
        System.out.println("------------------------------分隔符--------------------------------------------");
        // 获取表的相对应的列的名字
        ResultSet tableInfo = metaData.getColumns(null, "%", "audiolist", "%");
        while (tableInfo.next()) {
            // 表的名字
            System.out.print("表名:" + tableInfo.getString("TABLE_NAME") + "  ");
            // 列的名称
            System.out.print("列名:" + tableInfo.getString("COLUMN_NAME") + "  ");
            // 默认值
            System.out.print("默认值 :" + tableInfo.getString("COLUMN_DEF") + "\t");
            // 字段的类型
            System.out.print("字段的类型:" + tableInfo.getString("TYPE_NAME") + "  ");
            // 是否可以为空
            System.out.print("是否可以为空:" + tableInfo.getString("IS_NULLABLE") + "  ");
            // 是否为自增
            System.out.print("是否为自增:" + tableInfo.getString("IS_AUTOINCREMENT") + "  ");
            // 字段说明
            System.out.print("字段说明:" + tableInfo.getString("REMARKS") + "  ");
            // 长度(有时候是错的)
            System.out.println("长度:" + tableInfo.getString("COLUMN_SIZE"));
        }
    }
}