package com.example.demo.studentas.Restaurants;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantRepo implements RestaurantInterface {
    private final DataSource dataSource;
    public RestaurantRepo(DataSource dataSource){
        this.dataSource=dataSource;
    }

    @Override
    public void create(RestaurantEntity restaurant){
        String sql="INSERT into restaurants (name, address, description) VALUES (?,?,?)";
        try (Connection con=dataSource.getConnection();
        PreparedStatement ps=con.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1,restaurant.getName());
            ps.setString(2,restaurant.getAddress());
            ps.setString(3,restaurant.getDescription());
            ps.executeUpdate();
            try(ResultSet idKey= ps.getGeneratedKeys()){
                if(idKey.next()){
                    restaurant.setId(idKey.getLong(1));
                }

            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Error while saving");
        }
    }

    public List<RestaurantEntity> findallByName(String name) {
        List<RestaurantEntity> returnRestoraunt=new ArrayList<>();
        String sql="SELECT * from restaurants WHERE name=?";
        try(Connection con=dataSource.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,name);
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    RestaurantEntity restaurantTemp=new RestaurantEntity();
                    restaurantTemp.setId(rs.getLong("id"));
                    restaurantTemp.setName(rs.getString("name"));
                    restaurantTemp.setAddress(rs.getString("address"));
                    restaurantTemp.setDescription(rs.getString("description"));
                    returnRestoraunt.add(restaurantTemp);
                }

            }

        }
        catch (SQLException e){
            throw new RuntimeException("There is no such restaurant");
        }
        return returnRestoraunt;
    }
    @Override
    public void deleteById(Long id){
        String sql="DELETE from restaurants WHERE id=?";
        try(Connection con=dataSource.getConnection();
            PreparedStatement Deleteps=con.prepareStatement(sql)){
            Deleteps.setLong(1,id);
            Deleteps.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException("There is no restaurant like that to delete");
        }

    }
    @Override
    public Optional<RestaurantEntity> findById(Long id) {
        String sql="SELECT * from restaurants WHERE id=?";
        try(Connection con=dataSource.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setLong(1,id);
            try(ResultSet rs=ps.executeQuery()){
                if (rs.next()){
                    RestaurantEntity restaurantTemp=new RestaurantEntity();
                    restaurantTemp.setId(rs.getLong("id"));
                    restaurantTemp.setName(rs.getString("name"));
                    restaurantTemp.setAddress(rs.getString("address"));
                    restaurantTemp.setDescription(rs.getString("description"));
                    return Optional.of(restaurantTemp);
                }

            }

        }
        catch (SQLException e){
            throw new RuntimeException("There is no such restaurant");
        }
        return Optional.empty();
    }
    @Override
    public List<RestaurantEntity> findAll() {
        List<RestaurantEntity> returnRestoraunt=new ArrayList<>();
        String sql="SELECT * from restaurants";
        try(Connection con=dataSource.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs= ps.executeQuery())
        {
                while(rs.next()){
                    RestaurantEntity restaurantTemp=new RestaurantEntity();
                    restaurantTemp.setId(rs.getLong("id"));
                    restaurantTemp.setName(rs.getString("name"));
                    restaurantTemp.setAddress(rs.getString("address"));
                    restaurantTemp.setDescription(rs.getString("description"));
                    returnRestoraunt.add(restaurantTemp);
                }

            }


        catch (SQLException e){
            throw new RuntimeException("There is no such restaurant");
        }
        return returnRestoraunt;
    }
}
