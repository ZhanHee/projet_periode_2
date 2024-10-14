package dao;

import model.mapper.Mapper;
import sql.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenericDAO<T> {
    private final MySQLConnection mySQLConnection = new MySQLConnection();
    private final Mapper<T> mapper;

    public GenericDAO(Mapper<T> mapper) {
        this.mapper = mapper;
    }

    private Connection getConnection() throws SQLException {
        return mySQLConnection.getConnection();
    }

    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {

        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute SELECT & DELETE operations
     * @param sql SQL string (with '?' for parameters)
     * @param params parameters arguments
     * @return affected or selected objects
     */
    public List<T> executeQuery(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<T> results = new ArrayList<>();
        try {
            conn = getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
                if (params != null && params.length != 0) {
                    for (int i = 0; i < params.length; i++){
                        stmt.setObject(i+1, params[i]);
                    }
                }

                rs = stmt.executeQuery();

                while (rs.next()) {
                    T result = this.mapper.map(rs);
                    results.add(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return results;
    }


    /**
     * Execute INSERT operations
     * @param sql SQL string (with '?' for parameters)
     * @param params parameters arguments
     * @return affected row count
     */
    public Integer executeInsert(String sql, Object...params){
        Connection conn = null;
        PreparedStatement stmt = null;
        Integer createdId = null;
        try {
            conn = getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                if (params != null && params.length != 0) {
                    for (int i = 0; i< params.length; i++){
                        stmt.setObject(i+1, params[i]);
                    }
                }

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Create failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        createdId = generatedKeys.getInt(1);
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        return createdId;
    }

    public void executeInsertWithoutGeneratedKeys(String sql, Object...params){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
                if (params != null && params.length != 0) {
                    for (int i = 0; i< params.length; i++){
                        stmt.setObject(i+1, params[i]);
                    }
                }

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Execute UPDATE operations
     * @param sql SQL string (with '?' for parameters)
     * @param params parameters arguments
     */
    public void executeUpdate(String sql, Object...params){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                if (params != null && params.length != 0) {
                    for (int i = 0; i< params.length; i++){
                        stmt.setObject(i+1, params[i]);
                    }
                }

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
    }

}