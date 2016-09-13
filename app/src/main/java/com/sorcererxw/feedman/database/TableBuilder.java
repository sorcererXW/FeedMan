package com.sorcererxw.feedman.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/13
 */
public class TableBuilder {
    public static final int FLAG_PRIMARY_KEY = 1;
    public static final int FLAG_AUTOINCREMENT = 2;
    public static final int FLAG_NOT_NULL = 4;
    public static final int FLAG_COLLATE_NOCASE = 8;

    public static final String FOREIGN_KEY_CASCADE = "CASCADE";
    public static final String FOREIGN_KEY_NO_ACTION = "NO ACTION";
    public static final String FOREIGN_KEY_RESTRICT = "RESTRICT";

    public static final String TYPE_BOOL = "INTEGER";
    public static final String TYPE_INTEGER = "INTEGER";
    public static final String TYPE_TEXT = "TEXT";

    private String mTableName;

    private StringBuilder mStringBuilder;

    public TableBuilder(String tableName) {
        mTableName = tableName;
    }

    public TableBuilder addTextColumn(String columnName) {
        return addColumn(columnName, TYPE_TEXT, 0);
    }

    public TableBuilder addTextColumn(String columnName, int flags) {
        return addColumn(columnName, TYPE_TEXT, flags);
    }

    public TableBuilder addIntegerColumn(String columnName) {
        return addColumn(columnName, TYPE_INTEGER, 0);
    }

    public TableBuilder addIntegerColumn(String columnName, int flags) {
        return addColumn(columnName, TYPE_INTEGER, flags);
    }

    public TableBuilder addBooleanColumn(String columnName) {
        return addColumn(columnName, TYPE_BOOL, 0);
    }

    public TableBuilder addBooleanColumn(String columnName, int flags) {
        return addColumn(columnName, TYPE_INTEGER, flags);
    }

    public TableBuilder addColumn(String columnName, String type) {
        return addColumn(columnName, type, 0);
    }

    public TableBuilder addColumn(String name, String type, int flags) {
        if (mStringBuilder == null) {
            mStringBuilder = new StringBuilder();
            mStringBuilder.append("CREATE TABLE ").append(this.mTableName).append(" (");
        } else {
            mStringBuilder.append(", ");
        }
        mStringBuilder.append(name).append(" ").append(type);
        if ((flags & FLAG_PRIMARY_KEY) != 0) {
            mStringBuilder.append(" primary key ");
        }
        if ((flags & FLAG_AUTOINCREMENT) != 0) {
            mStringBuilder.append(" autoincrement ");
        }
        if ((flags & FLAG_NOT_NULL) != 0) {
            mStringBuilder.append(" not null ");
        }
        if ((flags & FLAG_COLLATE_NOCASE) != 0) {
            mStringBuilder.append(" collate nocase ");
        }
        return this;
    }

    private List<String> mPrimaryKeyList = new ArrayList<>();

    public TableBuilder addPrimaryKeys(String... columnNames) {
        Collections.addAll(mPrimaryKeyList, columnNames);
        return this;
    }

    private List<String> mForeignKeyList = new ArrayList<>();

    public TableBuilder addForeignKey(String columnName,
                                      String referencedTable,
                                      String referencedColumn,
                                      String onDeleteAction) {
        Object[] objArr = new Object[4];
        objArr[0] = columnName;
        objArr[1] = referencedTable;
        objArr[2] = referencedColumn;
        objArr[3] = onDeleteAction;
        mForeignKeyList
                .add(String.format("FOREIGN KEY(%s) REFERENCES %s(%s) ON DELETE %s", objArr));
        return this;
    }

    private List<String> mUniqueColumnList = new ArrayList<>();

    public TableBuilder addUniqueColumns(String... columnNames) {
        Collections.addAll(mUniqueColumnList, columnNames);
        return this;
    }

    public String build() {
        if (!mPrimaryKeyList.isEmpty()) {
            mStringBuilder.append(", PRIMARY KEY (");
            for (int i = 0; i < mPrimaryKeyList.size(); i++) {
                mStringBuilder.append(mPrimaryKeyList.get(i));
                if (i < mPrimaryKeyList.size() - 1) {
                    mStringBuilder.append(", ");
                }
            }
            mStringBuilder.append(")");
        }
        if (!mForeignKeyList.isEmpty()) {
            mStringBuilder.append(", ");
            for (int i = 0; i < mForeignKeyList.size(); i++) {
                mStringBuilder.append(mForeignKeyList.get(i));
                if (i < mForeignKeyList.size() - 1) {
                    mStringBuilder.append(", ");
                }
            }
        }
        if (!mUniqueColumnList.isEmpty()) {
            mStringBuilder.append(", UNIQUE (");
            for (int i = 0; i < mUniqueColumnList.size(); i++) {
                mStringBuilder.append(mUniqueColumnList.get(i));
                if (i < mUniqueColumnList.size() - 1) {
                    mStringBuilder.append(", ");
                }
            }
            mStringBuilder.append(")");
        }
        return mStringBuilder.append(" );").toString();
    }
}
