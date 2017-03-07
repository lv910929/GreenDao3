package com.lv.greendao3.data;

import android.content.Context;

import com.lv.greendao3.MyApp;
import com.lv.greendao3.model.DaoMaster;
import com.lv.greendao3.model.DaoSession;
import com.lv.greendao3.model.Phone;
import com.lv.greendao3.model.PhoneDao;
import com.lv.greendao3.model.User;
import com.lv.greendao3.model.UserDao;
import com.lv.greendao3.utils.MyToast;

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
        String pinyin = MyApp.characterParser.getSelling(name);
        String sortString = pinyin.substring(0, 1).toUpperCase();
        if (sortString.matches("[A-Z]")) {
            user.setSortLetters(sortString.toUpperCase());
        } else {
            user.setSortLetters("#");
        }
        getUserDao().insert(user);
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

    /**
     * 批量删除用户
     *
     * @param selectUses
     */
    public static void deleteUserList(Set<User> selectUses) {
        for (User user : selectUses) {
            deletePhoneList(user.getPhones());
            getUserDao().delete(user);
        }
    }

    /**
     * 修改用户信息
     *
     * @param newUser
     */
    public static void updateUserById(User newUser) {
        String pinyin = MyApp.characterParser.getSelling(newUser.getName());
        String sortString = pinyin.substring(0, 1).toUpperCase();
        if (sortString.matches("[A-Z]")) {
            newUser.setSortLetters(sortString.toUpperCase());
        } else {
            newUser.setSortLetters("#");
        }
        User oldUser = getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(newUser.getId())).build().unique();
        if (oldUser == null) {
            MyToast.showShortToast("用户不存在");
        } else {
            getUserDao().update(newUser);
        }
    }

    public static PhoneDao getPhoneDao() {
        return daoSession.getPhoneDao();
    }

    /**
     * 添加号码
     */
    public static void addPhone(Phone phone) {
        getPhoneDao().insert(phone);
    }

    /**
     * 根据用户ID查号码
     */
    public static List<Phone> queryPhonesByUserId(long userId) {
        List<Phone> phones = getPhoneDao()._queryUser_Phones(userId);
        return phones;
    }

    /**
     * 修改号码
     */
    public static void updatePhone(Phone newPhone) {
        getPhoneDao().update(newPhone);
    }

    /**
     * 删除号码
     */
    public static void deletePhoneList(List<Phone> phoneList) {
        getPhoneDao().deleteInTx(phoneList);
    }

}
