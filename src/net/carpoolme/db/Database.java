package net.carpoolme.db;

import net.carpoolme.datastructures.Tree23;
import net.carpoolme.storage.FileSystemStorage;
import net.carpoolme.storage.MockStorage;
import net.carpoolme.storage.Storage;

import java.io.InvalidObjectException;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by John Andersen on 5/26/16.
 */
public class Database extends Tree23 {
    public static final String INTERNAL_USERS = "_DB_USERS";

    private Storage storage;

    // Will keep track of other nodes in cluster
    // private DLL clusterMembers = new DLL();

    public Database() {
        try {
            storage = new FileSystemStorage();
        } catch (FileSystemException e) {
            System.out.println("ERROR: failed to initialize db filesystem, records will not be saved");
            e.printStackTrace();
            storage = new MockStorage();
        }
        createTable(INTERNAL_USERS, "id", new String[] {"id", "username"});
    }

    public synchronized boolean createTable(final String tableName, final String primaryKey, final String[] indexes) {
        try {
            get(tableName);
            return false;
        } catch (IndexOutOfBoundsException ignored) {}
        try {
            Path tablePath = Paths.get(tableName);
            Storage downLevel = storage.downLevel(tablePath);
            Table table = new Table(downLevel, primaryKey, indexes);
            add(tableName, table);
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

    public synchronized Table select(final String tableName, final String searchIndex, final String matchValue) {
        Table table = (Table) get(tableName);
        return table.get(searchIndex, matchValue);
    }
}
