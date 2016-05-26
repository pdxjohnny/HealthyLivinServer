package net.carpoolme.db;

import net.carpoolme.datastructures.Tree23;

import java.io.InvalidObjectException;

/**
 * Created by John Andersen on 5/26/16.
 */
public class Database {
    // Will keep track of other nodes in cluster
    // private DLL clusterMembers = new DLL();

    // Stores all of our tables
    private Tree23 tables = new Tree23();

    public Database() {}

    public synchronized boolean createTable(final String tableName, final String[] indexes) {
        Tree23 index = (Tree23) tables.get(tableName);
        if (index == null) {
            try {
                tables.add(tableName, new Table(indexes));
                return true;
            } catch (IndexOutOfBoundsException | InvalidObjectException ignored) {}
        }
        return false;
    }

    public synchronized boolean addIndexesToTable(final String tableName, final String[] indexes) throws IndexOutOfBoundsException {
        // Add the index to the list of indexes that the table should have
        Table addIndexesTo = (Table) tables.get(tableName);
        return addIndexesTo.addIndexes(indexes);
    }
}
