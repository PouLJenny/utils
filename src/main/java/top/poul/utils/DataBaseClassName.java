package top.poul.utils;

public enum DataBaseClassName {

    MYSQL("com.mysql.jdbc.Driver"),
    MYSQL8("com.mysql.cj.jdbc.Driver"),
    ORACLE("oracle.jdbc.driver.OracleDriver"),
    ;


    private String qualifiedName;

    DataBaseClassName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }


    public String getQualifiedName() {
        return this.qualifiedName;
    }

}
