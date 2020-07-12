import java.sql.*;

public class Database {

    private Connection connection;
    private Query query;

    /**
     * The class constructor
     * @param database to be connected to
     * @param username
     * @param password
     */
    public Database(String database, String username, String password) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + database, username, password);
            this.connection = connection;
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    /**
     * A help method for executing any query on
     * the database with additional parameters
     * @param query the initial query
     * @param params - the values for the query
     * @return the number of affected rows
     * @throws SQLException
     */
    private int query(String query, Object[] params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        if (params != null) {
            int index = 1;
            for (Object param : params) {
                preparedStatement.setObject(index, param);
                index++;
            }
        }
        return preparedStatement.executeUpdate();
    }

    /**
     * Deletes the data from a table,
     * uses the private help method query()
     * @param table to be deleted from
     * @param requirement specificationn
     * @param param - the values for the query
     * @return the number of rows affected
     * @throws SQLException
     */
    public int delete(String table, String requirement, Object[] param) throws SQLException {
        query = new Query();
        query.delete(table).where(requirement);
        return query(query.getQuery(), param);
    }

    /**
     * Inserts new data to a table,
     * uses the private help method query()
     * @param table to be inserted intp
     * @param params - the values for the query
     * @return the number of rows affected
     * @throws SQLException
     */
    public int insert(String table, Object[] params) throws SQLException {
        query = new Query();
        query.insert(table).values(params);
        return query(query.getQuery(), params);
    }

    /**
     * Updates the data in a table
     * @param table to be updated
     * @param columns to be updated
     * @param requirement specification
     * @param params - the values for the query
     * @return the number of rows affected
     * @throws SQLException
     */
    public int update(String table, String[] columns, String requirement, Object[] params) throws SQLException {
        query = new Query();
        query.update(table).set(columns).where(requirement);
        return query(query.getQuery(), params);
    }

    /**
     * Selects data from a table by calling
     * an overloaded method
     * @param table to be selected from
     * @param columns the columns to be selected
     * @param params - the values for the query
     * @return the result set of the query
     * @throws SQLException
     */
    public ResultSet select(String table, Object[] columns, Object[] params) throws SQLException {
        return this.select(table, columns, "", params);
    }

    /**
     * Selects data from a table
     * @param table to be selected from
     * @param columns the columns to be selected
     * @param requirement specification
     * @param params - the values for the query
     * @return the result set of the query
     * @throws SQLException
     */
    public ResultSet select(String table, Object[] columns, String requirement, Object[] params) throws SQLException {
        query = new Query();
        query.select(columns).from(table).where(requirement);
        PreparedStatement preparedStatement = connection.prepareStatement(query.getQuery());
        if (params != null) {
            int index = 1;
            for (Object param : params) {
                preparedStatement.setObject(index, params);
                index++;
            }
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
}
