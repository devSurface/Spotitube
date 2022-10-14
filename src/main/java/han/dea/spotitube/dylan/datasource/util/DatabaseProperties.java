package han.dea.spotitube.dylan.datasource.util;

import java.util.Properties;

public class DatabaseProperties {
    public String getProperties() {
        Properties properties = new Properties();
        properties.setProperty("driver", "com.mysql.jdbc.Driver");
        properties.setProperty("connectionString", "jdbc:mysql://localhost:3306/spotitube?user=root&password=&serverTimezone=UTC");


        return properties.getProperty("connectionString");

    }


}

