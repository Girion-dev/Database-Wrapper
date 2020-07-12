/**
 * Class for building the SQL query,
 * uses the 'fluent interface' approach
 * for more readable code
 */
public class Query {

    /**
     * The query itself represented
     * by a single String
     */
    private StringBuilder query;

    /**
     * Deletes data from a table
     * @param table to be deleted from
     * @return the Query object with the appended query
     */
    public Query delete(String table) {
        query = new StringBuilder();
        query.append("DELETE FROM");
        query.append(table);
        return this;
    }

    /**
     * Filter records
     * @param requirement
     * @return the Query object with the appended query
     */
    public Query where(String requirement) {
        query.append(" WHERE ");
        query.append(requirement);
        return this;
    }

    /**
     * Specifies the table in a query
     * @param table
     * @return the Query object with the appended query
     */
    public Query from(String table) {
        query.append(" FROM ");
        query.append(table);
        return this;
    }

    /**
     * Updates the table
     * @param table to be updated
     * @return the Query object with the appended query
     */
    public Query update(String table) {
        query = new StringBuilder();
        query.append("UPDATE ");
        query.append(table);
        query.append(" SET ");
        return this;
    }

    /**
     * Adds new columns
     * @param columns to be added to a table
     * @return the Query object with the appended query
     */
    public Query set(String[] columns) {
        int count = columns.length;
        if (count == 0) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        for (String column : columns) {
            query.append(column);
            query.append(" = ");
            query.append("?");
            query.append(", ");
        }
        query.deleteCharAt(query.lastIndexOf(",")); //Get rid of the last comma
        query.deleteCharAt(query.lastIndexOf(" ")); //Get rid of the last space
        return this;
    }

    /**
     * Inserts new data into a table
     * @param table to be inserted into
     * @return the Query object with the appended query
     */
    public Query insert(String table) {
        query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(table);
        return this;
    }

    /**
     * Adds the params as a values to the query
     * @param params - the values to be added
     * @return the Query object with the appended query
     */
    public Query values(Object[] params) {
        query.append(" VALUES(");
        int count = params.length;
        if (count == 0) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        for (int i = 0; i < count; i++) {
            query.append("?, ");
        }
        query.deleteCharAt(query.lastIndexOf(",")); //Get rid of the last comma
        query.deleteCharAt(query.lastIndexOf(" "));//Get rid of the last space
        query.append(");");
        return this;
    }

    /**
     * Selects a data from a table
     * @param columns to be selected from
     * @return the Query object with the appended query
     */
    public Query select(Object[] columns) {
        query = new StringBuilder();
        query.append("SELECT ");
        if (columns != null) {
            for (Object column : columns) {
                query.append(column);
                query.append(", ");
            }
            query.deleteCharAt(query.lastIndexOf(",")); //Get rid of the last comma
        }
        else {
            query.append("*");
        }
        return this;
    }

    /**
     * Returns the query as a String
     * @return the query as a String
     */
    public String getQuery() {
       return query.toString();
    }
}
