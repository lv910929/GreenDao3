package com.lv.greendao3.data;

import android.content.Context;

import com.lv.greendao3.model.DaoMaster;
import com.lv.greendao3.model.DaoSession;
import com.lv.greendao3.model.User;
import com.lv.greendao3.model.UserDao;

import java.util.List;
import java.util.Set;

public class DbManager {

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public static DbManager initDatabase(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), "demo.db", null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
        return new DbManager();
    }

    public static UserDao getUserDao() {
        return daoSession.getUserDao();
    }

    public static void addUser(String name, int sexy) {
        User user = new User(null, name, sexy);
        getUserDao().insert(user);
    }

    public static long queryRecord() {
        return getUserDao().queryBuilder().count();
    }

    /**
     * 按照ID排序
     *
     * @return
     */
    public static List<User> queryUserListAll() {
        List<User> userList = getUserDao().queryBuilder().orderAsc(UserDao.Properties.Id).list();
        return userList;
    }

    public static void deleteUser(int num) {
        /**
         *  查询id小于几的数据集合，进行删除
         */
        List<User> userList = (List<User>) getUserDao().queryBuilder().where(UserDao.Properties.Id.le(num)).build().list();
        for (User user : userList) {
            getUserDao().delete(user);
        }
    }

    public static void deleteUserById(int id) {
        User user = getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(id)).build().unique();
        if (user == null) {
            //用户不存在
        } else {
            getUserDao().deleteByKey(user.getId());
        }
    }

    public static void deleteUserList(Set<User> selectUses) {
        for (User user : selectUses) {
            User queryUser = getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(user.getId())).build().unique();
            if (queryUser == null) {
                //用户不存在
            } else {
                getUserDao().deleteByKey(queryUser.getId());
            }
        }
    }

    public static void deleteAllUser() {
        getUserDao().deleteAll();
    }

    //一个是id要大于等于10，同是还要满足username like %90%，注意最后的unique表示只查询一条数据出来即
    public static void updateUser(int id) {
        User user = getUserDao().queryBuilder()
                .where(UserDao.Properties.Id.ge(10), UserDao.Properties.Name.like("%90%")).build().unique();
        if (user == null) {
            //用户不存在
        } else {
            user.setName("王五");
            getUserDao().update(user);
        }
    }

    //查询id介于2到13之间的数据，limit表示查询5条数据。
    public static List<User> queryUserList() {
        List<User> list = getUserDao().queryBuilder()
                .where(UserDao.Properties.Id.between(2, 13)).limit(5).build().list();
        return list;
    }
}
