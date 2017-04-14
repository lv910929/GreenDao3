package com.lv.greendao3.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lv on 2017/3/7.
 */
@Entity
public class Message {

    public final static int UNREAD_STATE = 1;//未读
    public final static int READ_STATE = 2;//已读

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "TITLE")
    private String title;
    @Property(nameInDb = "CONTENT")
    private String content;
    @Property(nameInDb = "PUSH_TIME")
    private String pushTime;
    @Property(nameInDb = "READ_STATE")
    private int readState;

    @Transient
    private boolean isSelect;

    @Keep
    public Message(Long id, String title, String content, String pushTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pushTime = pushTime;
        readState = UNREAD_STATE;
    }

    @Generated(hash = 637306882)
    public Message() {
    }

    @Generated(hash = 1141799329)
    public Message(Long id, String title, String content, String pushTime,
            int readState) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pushTime = pushTime;
        this.readState = readState;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPushTime() {
        return this.pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public int getReadState() {
        return this.readState;
    }

    public void setReadState(int readState) {
        this.readState = readState;
    }

}
