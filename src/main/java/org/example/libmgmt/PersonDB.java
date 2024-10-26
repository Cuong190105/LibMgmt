package org.example.libmgmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDB {
    public List<Person> getDB() throws Exception {
        List<Person> list = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();
        String sql = "SELECT * FROM libraryDB.person";
        PreparedStatement print = con.prepareStatement(sql);
        ResultSet result = print.executeQuery();
        while (result.next()) {
            Person person = new Person();
            person.setId(result.getInt("id"));
            person.setName(result.getString("name"));
            person.setPassword(result.getString("password"));
            list.add(person);
        }
        return list;
    }

    public static void add(Person person) throws Exception {
        Connection con = ConnectionDB.getConnection();
        String sql = "INSERT INTO libraryDB.person(id,name,password,fine) VALUES (?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, person.getId());
        ps.setString(2, person.getName());
        ps.setString(3, person.getPassword());
        ps.setFloat(4, person.getFine());

        ps.executeUpdate();
    }
}
