package net.carpoolme.db;

import net.carpoolme.datastructures.DLL;
import net.carpoolme.datastructures.Tree23;
import net.carpoolme.utils.BasicParser;

import java.io.InvalidObjectException;

/**
 * Created by John Andersen on 5/26/16.
 */
public class Table {
    // Stores all of our data
    private DLL tableData = new DLL();
    // The indexes for quick access to records
    private Tree23 searchIndexes = new Tree23();
    // Maintain these indexes on each record
    private DLL maintainIndexes = new DLL();

    // For parsing records
    private BasicParser parser = new BasicParser();

    public Table(final String[] indexes) throws IndexOutOfBoundsException {
        addIndexes(indexes);
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
        for (int i = 0; i < tableData.size(); ++i) {
            record = (Object[][]) tableData.get(i);
            addIndexesToData(indexes, record);
        }
        return true;
    }

    private synchronized boolean addIndexesToData(final String[] indexes, final Object[][] record) {
        // Now add the indexes to the existing entries in the table
        String fieldData;
        Tree23 index;
        for (int i = indexes.length - 1; i >= 0; --i) {
            fieldData = (String) parser.getKey(record, indexes[i]);
            if (fieldData == null) {
                continue;
            }
            index = (Tree23) searchIndexes.get(indexes[i]);
            try {
                index.add(fieldData, record);
                // Somehow record was null, keep on adding other records
            } catch (InvalidObjectException ignored) {
                return false;
            }
        }
        return true;
    }

    public synchronized boolean add(final Object[][] addData) throws IndexOutOfBoundsException {
        String[] allIndexes = (String[]) maintainIndexes.toArray();
        if (addIndexesToData(allIndexes, addData))
            if (tableData.add(addData)) {
                return true;
            } else {
                return false;
            }
        else {
            return false;
        }
    }

    public synchronized Object get(final int index) throws IndexOutOfBoundsException {
        return tableData.get(index);
    }

    public Object rawSearch(final String searchKey) {
        Object[][] record;
        String fieldData;
        for (int i = 0; i < tableData.size(); ++i) {
            record = (Object[][]) tableData.get(i);
            fieldData = (String) parser.getKey(record, searchKey);
            if (fieldData != null) {
                return record;
            }
        }
        // Did not find the data
        throw new IndexOutOfBoundsException();
    }

    // Get with index fallback to raw search
    public synchronized Object get(final String searchIndex, final String searchKey) throws IndexOutOfBoundsException {
        // Retrieve by index lookup
        Tree23 index = (Tree23) searchIndexes.get(searchIndex);
        try {
            return index.get(searchKey);
        } catch (IndexOutOfBoundsException ignored) {}
        // Retrieve by dataTable search
        return rawSearch(searchKey);
    }

    // Try each index fallback to raw search
    public synchronized Object get(final String searchKey) throws IndexOutOfBoundsException {
        // Retrieve by index lookup
        Tree23 index;
        for (int i = 0; i < searchIndexes.size(); ++i) {
            try {
                index = (Tree23) searchIndexes.value(i);
                return index.get(searchKey);
            } catch (IndexOutOfBoundsException ignored) {}
        }
        return rawSearch(searchKey);
    }
}