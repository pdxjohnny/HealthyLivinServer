package net.carpoolme.db;

import net.carpoolme.datastructures.DLL;
import net.carpoolme.datastructures.Tree23;
import net.carpoolme.storage.Serializer;
import net.carpoolme.storage.Storage;
import net.carpoolme.utils.BasicParser;
import net.carpoolme.utils.JSONParser;

import java.io.InputStream;
import java.io.InvalidObjectException;
import java.nio.file.Paths;

/**
 * Created by John Andersen on 5/26/16.
 */
public class Table extends DLL<Object[][]> {
    private Storage storage;

    // The indexes for quick access to records
    private Tree23 searchIndexes = new Tree23();
    // Maintain these indexes on each record
    private DLL<String> maintainIndexes = new DLL<String>();

    // For parsing records
    private BasicParser parser = new BasicParser();

    private String primaryKey = "id";

    public Table(final Table table) {
        this(table.storage, table.primaryKey, table.maintainIndexes.toArray(new String[table.maintainIndexes.size()]));
    }

//    public Table(final Table table, final Tree23 tree23) {
//        this(table.storage, table.primaryKey, table.maintainIndexes.toArray(new String[table.maintainIndexes.size()]));
//    }

    public Table(final Storage mStorage, final String mPrimaryKey, final String[] indexes) {
        this(mStorage, mPrimaryKey, indexes, null);
    }

    public Table(final Storage mStorage, final String mPrimaryKey, final String[] indexes, final String addIndex) {
        String[] newIndexes = indexes;
        if (addIndex != null) {
            newIndexes = new String[indexes.length + 1];
            newIndexes[0] = addIndex;
            System.arraycopy(indexes, 0, newIndexes, 1, indexes.length);
        }
        primaryKey = mPrimaryKey;
        storage = mStorage;
        addIndexes(newIndexes);
        loadFromStorage(storage);
    }

    public synchronized void loadFromStorage(Storage mStorage) {
        mStorage.disableWrite();
        InputStream[] records = mStorage.allRecords();
        for (int i = 0; i < records.length; ++i) {
            add((Object[][]) Serializer.toObject(records[i]));
        }
        mStorage.enableWrite();
    }

    public synchronized boolean addIndexes(final String[] indexes) throws IndexOutOfBoundsException {
        // Add the index to the tree of indexes that the table should have
        for (int i = indexes.length - 1; i >= 0; --i) {
            if (!searchIndexes.contains(indexes[i])) {
                try {
                    // Create a tree to store the index data
                    searchIndexes.add(indexes[i], new Tree23());
                    // Add to list of indexes to maintain
                    if (!maintainIndexes.contains(indexes[i])) {
                        maintainIndexes.add(indexes[i]);
                    }
                } catch (InvalidObjectException ignored) {
                    // Error adding
                    return false;
                }
            }
        }
        // Now add the indexes to the existing entries in the table
        Object[][] record;
        for (int i = 0; i < size(); ++i) {
            record = get(i);
            addIndexesToData(indexes, record);
        }
        return true;
    }

    private synchronized boolean addIndexesToData(final String[] indexes, final Object[][] record) {
        // Now add the indexes to the existing entries in the table
        Comparable fieldData;
        Tree23 index;
        for (int i = indexes.length - 1; i >= 0; --i) {
            // These always return a primitive type
            fieldData = (Comparable) parser.getKey(record, indexes[i]);
            if (fieldData == null) {
                continue;
            }
            index = (Tree23) searchIndexes.get(indexes[i]);
            try {
                index.add(fieldData, record);
            } catch (InvalidObjectException ignored) {
                // Somehow record was null, that is bad
                return false;
            }
        }
        return true;
    }

    public synchronized boolean add(final Object[][] addData) throws IndexOutOfBoundsException {
        return add(parser.getKey(addData, primaryKey).toString(), addData);
    }

    public synchronized boolean add(final String storageKey, final Object[][] addData) throws IndexOutOfBoundsException {
        // Only insert if it does not exist
        try {
            get(primaryKey, (Comparable) parser.getKey(addData, primaryKey));
        } catch (IndexOutOfBoundsException ignored) {
            System.out.println("DEBUG: Inserting " + new JSONParser().toString(addData));
            String[] allIndexes = maintainIndexes.toArray(new String[maintainIndexes.size()]);
            return addIndexesToData(allIndexes, addData) && super.add(addData) && storage.writeRecord(Paths.get(storageKey), Serializer.toInputStream(addData));
        }
        return false;
    }

    public Table rawSearch(final String searchKey, final Comparable matchData) {
        Table table = null;
        Object[][] record;
        Comparable fieldData;
        for (int i = 0; i < size(); ++i) {
            record = get(i);
            fieldData = (Comparable) parser.getKey(record, searchKey);
            if (fieldData != null && fieldData.equals(matchData)) {
                if (table == null) {
                    table = new Table(storage, searchKey, maintainIndexes.toArray(new String[maintainIndexes.size()]), searchKey);
                }
                table.add(record);
            }
        }
        if (table == null) {
            // Did not find the data
            throw new IndexOutOfBoundsException();
        }
        return table;
    }

    // Get with index fallback to raw search
    public synchronized Table get(final String searchIndex, final Comparable matchData) throws IndexOutOfBoundsException {
        // Retrieve by index lookup
        Tree23 index;
        try {
            index = (Tree23) searchIndexes.get(searchIndex);
        } catch (IndexOutOfBoundsException ignored) {
            System.out.printf("WARN: No index by the name of \"%s\" found\n", searchIndex);
            // Retrieve by dataTable search
            return rawSearch(searchIndex, matchData);
        }
//        return new Table(this, index.get(matchData));
        return null;
    }
}