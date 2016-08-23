package api.stepsDefinition;


import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import javax.sql.DataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCleanerForTest {

    private final DataSource dataSource;

    public DatabaseCleanerForTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void resetDatabase() throws Exception {

        DataSourceDatabaseTester jdbcDatabaseTester = new DataSourceDatabaseTester(dataSource);
        URL resource = getClass().getClassLoader().getResource("dbunit/empty.xml");
        FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(resource);
        jdbcDatabaseTester.setDataSet(dataSet);
        jdbcDatabaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        jdbcDatabaseTester.onTearDown();
        resetAutoIncrement(dataSource);
    }

    private void resetAutoIncrement(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT TABLE_NAME AS name\n" +
                            "FROM INFORMATION_SCHEMA.TABLES\n" +
                            "WHERE  TABLE_SCHEMA = 'PUBLIC'\n" +
                            "      AND SQL LIKE '% ID %'\n" +
                            "      AND TABLE_NAME NOT LIKE 'DATABASECHANGELOG%'");

            while (resultSet.next()) {
                try (Statement statement2 = connection.createStatement()) {
                    statement2.execute("ALTER TABLE " + resultSet.getString("name") + " ALTER COLUMN ID RESTART WITH 1");
                }
            }
        }
    }
}
