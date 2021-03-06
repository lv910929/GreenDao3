package com.lv.greendao3.model;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PHONE".
*/
public class PhoneDao extends AbstractDao<Phone, Long> {

    public static final String TABLENAME = "PHONE";

    /**
     * Properties of entity Phone.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PhoneNumber = new Property(1, String.class, "phoneNumber", false, "PHONE_NUMBER");
        public final static Property PhoneType = new Property(2, int.class, "phoneType", false, "PHONE_TYPE");
        public final static Property UserId = new Property(3, Long.class, "userId", false, "USER_ID");
    }

    private Query<Phone> user_PhonesQuery;

    public PhoneDao(DaoConfig config) {
        super(config);
    }
    
    public PhoneDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PHONE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PHONE_NUMBER\" TEXT," + // 1: phoneNumber
                "\"PHONE_TYPE\" INTEGER NOT NULL ," + // 2: phoneType
                "\"USER_ID\" INTEGER);"); // 3: userId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PHONE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Phone entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(2, phoneNumber);
        }
        stmt.bindLong(3, entity.getPhoneType());
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(4, userId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Phone entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(2, phoneNumber);
        }
        stmt.bindLong(3, entity.getPhoneType());
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(4, userId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Phone readEntity(Cursor cursor, int offset) {
        Phone entity = new Phone( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // phoneNumber
            cursor.getInt(offset + 2), // phoneType
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // userId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Phone entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPhoneNumber(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPhoneType(cursor.getInt(offset + 2));
        entity.setUserId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Phone entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Phone entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Phone entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "phones" to-many relationship of User. */
    public List<Phone> _queryUser_Phones(Long userId) {
        synchronized (this) {
            if (user_PhonesQuery == null) {
                QueryBuilder<Phone> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.UserId.eq(null));
                user_PhonesQuery = queryBuilder.build();
            }
        }
        Query<Phone> query = user_PhonesQuery.forCurrentThread();
        query.setParameter(0, userId);
        return query.list();
    }

}
