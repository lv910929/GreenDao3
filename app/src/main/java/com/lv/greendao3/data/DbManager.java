package com.lv.greendao3.data;

import android.content.Context;

import com.lv.greendao3.model.DaoMaster;
import com.lv.greendao3.model.DaoSession;
import com.lv.greendao3.model.Message;
import com.lv.greendao3.model.MessageDao;
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
     * 删除单个联系人
     *
     * @param user
     */
    public static void deleteUser(User user) {
        getUserDao().delete(user);
    }

    /**
     * 修改用户信息
     *
     * @param newUser
     */
    public static void updateUserById(User newUser) {
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

    public static MessageDao getMessageDao() {
        return daoSession.getMessageDao();
    }

    /**
     * 查询所有的消息
     *
     * @return
     */
    public static List<Message> queryMessages() {
        return getMessageDao().queryBuilder().orderAsc(MessageDao.Properties.PushTime).list();
    }

    /**
     * 全部已读
     */
    public static void updateMessageState() {
        List<Message> messages = getMessageDao().queryBuilder().where(MessageDao.Properties.ReadState.eq(Message.UNREAD_STATE)).list();
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            message.setReadState(Message.READ_STATE);
            getMessageDao().update(message);
        }
    }

    /**
     * 清空消息
     */
    public static void cleanMessages() {
        getMessageDao().deleteAll();
    }
}
