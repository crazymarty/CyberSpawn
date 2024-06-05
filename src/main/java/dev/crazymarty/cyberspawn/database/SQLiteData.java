package dev.crazymarty.cyberspawn.database;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.*;

public class SQLiteData {

    private final Connection connection;

    public SQLiteData(String path) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS spawnLocation (" +
                    "name TEXT PRIMARY KEY," +
                    "x DOUBLE," +
                    "y DOUBLE," +
                    "z DOUBLE," +
                    "pitch FLOAT," +
                    "yaw FLOAT," +
                    "world STRING)");
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Location setSpawnLocation(Location location) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS( " +
                    "SELECT 1 FROM spawnLocation WHERE name = ?);");
            preparedStatement.setString(1, "spawn");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 1) {
                preparedStatement = connection.prepareStatement("DELETE FROM spawnLocation WHERE name = ?");
                preparedStatement.setString(1, "spawn");
                preparedStatement.executeUpdate();
            }
            preparedStatement = connection.prepareStatement("INSERT INTO spawnLocation (" +
                    "name, x, y, z, pitch, yaw, world) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, "spawn");
            preparedStatement.setDouble(2, location.getX());
            preparedStatement.setDouble(3, location.getY());
            preparedStatement.setDouble(4, location.getZ());
            preparedStatement.setFloat(5, location.getPitch());
            preparedStatement.setFloat(6, location.getYaw());
            preparedStatement.setString(7, location.getWorld().getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getSpawnLocation();
    }

    public Location getSpawnLocation() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT x,y,z,yaw,pitch,world FROM spawnLocation WHERE name = ?");
            preparedStatement.setString(1, "spawn");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double spawnX = resultSet.getDouble("x");
                double spawnY = resultSet.getDouble("y");
                double spawnZ = resultSet.getDouble("z");
                float spawnYaw = resultSet.getFloat("yaw");
                float spawnPitch = resultSet.getFloat("pitch");
                World spawnWorld = Bukkit.getWorld(resultSet.getString("world"));
                return new Location(spawnWorld, spawnX, spawnY, spawnZ, spawnYaw, spawnPitch);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Location setFirstSpawnLocation(Location location) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS( " +
                    "SELECT 1 FROM spawnLocation WHERE name = ?);");
            preparedStatement.setString(1, "firstSpawn");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 1) {
                preparedStatement = connection.prepareStatement("DELETE FROM spawnLocation WHERE name = ?");
                preparedStatement.setString(1, "firstSpawn");
                preparedStatement.executeUpdate();
            }
            preparedStatement = connection.prepareStatement("INSERT INTO spawnLocation (" +
                    "name, x, y, z, pitch, yaw, world) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, "firstSpawn");
            preparedStatement.setDouble(2, location.getX());
            preparedStatement.setDouble(3, location.getY());
            preparedStatement.setDouble(4, location.getZ());
            preparedStatement.setFloat(5, location.getPitch());
            preparedStatement.setFloat(6, location.getYaw());
            preparedStatement.setString(7, location.getWorld().getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getSpawnLocation();
    }

    public Location getFirstSpawnLocation() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT x,y,z,yaw,pitch,world FROM spawnLocation WHERE name = ?");
            preparedStatement.setString(1, "firstSpawn");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double spawnX = resultSet.getDouble("x");
                double spawnY = resultSet.getDouble("y");
                double spawnZ = resultSet.getDouble("z");
                float spawnYaw = resultSet.getFloat("yaw");
                float spawnPitch = resultSet.getFloat("pitch");
                World spawnWorld = Bukkit.getWorld(resultSet.getString("world"));
                return new Location(spawnWorld, spawnX, spawnY, spawnZ, spawnYaw, spawnPitch);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
