package net.carpoolme.db;

import net.carpoolme.datastructures.Tree23;

import java.io.InvalidObjectException;

/**
 * Created by John Andersen on 5/26/16.
 */
public class Database extends Tree23 {
    public static final String INTERNAL_USERS = "_DB_USERS";

    // Will keep track of other nodes in cluster
    // private DLL clusterMembers = new DLL();

    public Database() {
        createTable(INTERNAL_USERS, new String[] {"id", "username"});
    }

    public synchronized boolean createTable(final String tableName, final String[] indexes) {
        try {
            get(tableName);
            return false;
        } catch (IndexOutOfBoundsException ignored) {}
        try {
            add(tableName, new Table(indexes));
            return true;
        } catch (IndexOutOfBoundsException | InvalidObjectException ignored) {}
        return false;
    }

    public synchronized boolean alterTable(final String tableName, final String[] indexes) throws IndexOutOfBoundsException {
        // Add the index to the list of indexes that the table should have
        Table table = (Table) get(tableName);
        return table.addIndexes(indexes);
    }

    public synchronized boolean insert(final String tableName, final Object[][] data) {
        Table table = (Table) get(tableName);
        return table.add(data);
    }

    public synchronized Object[][] select(final String tableName, final Comparable searchIndex, final String searchKey) {
        Table table = (Table) get(tableName);
        return table.get(searchIndex, searchKey);
    }

    public synchronized Object[][] select(final String tableName, final String searchKey) {
        Table table = (Table) get(tableName);
        return table.get(searchKey);
    }
}
