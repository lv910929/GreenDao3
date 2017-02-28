package com.lv.greendao3.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Lv on 2017/2/27.
 */
@Entity
public class User {

    public final static int MAN = 1;
    public final static int WOMAN = 2;

    //@Id：通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "NAME")
    private String name;
    @Property(nameInDb = "SEXY")
    private int sexy;
    //@Transient：表明这个字段不会被写入数据库，只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化
    @Transient
    private boolean isSelect;

    @Keep
    public User(Long id, String name, int sexy) {
        this.id = id;
        this.name = name;
        this.sexy = sexy;
        isSelect = false;
    }

    @Keep
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSexy() {
        return this.sexy;
    }

    public void setSexy(int sexy) {
        this.sexy = sexy;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
