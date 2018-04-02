
package model;

import org.springframework.jdbc.datasource.DriverManagerDataSource;


public class conexion {
    
    public DriverManagerDataSource conectar (){
        
        DriverManagerDataSource dataSource =  new DriverManagerDataSource();
        
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/crudspring");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }
    
}
