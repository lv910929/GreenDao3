package com.lv.greendao3.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lv on 2017/3/7.
 */
@Entity
public class Message {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "TITLE")
    private String title;
    @Property(nameInDb = "CONTENT")
    private String content;
    @Property(nameInDb = "PUSH_TIME")
    private String pushTime;
    @Transient
    private boolean isSelect;

    @Generated(hash = 1421355381)
    public Message(Long id, String title, String content, String pushTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pushTime = pushTime;
    }
    @Generated(hash = 637306882)
    public Message() {
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

}
