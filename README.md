# GreenDao3
### 学习GreenDao3的一个Demo

注解 | 描述
---|---
@Id | 必须是Long类型的，表示它就是主键，并且默认就是自增的
@Property | 非默认的列名。如果不存在，greenDAO将使用字段名（大写，下划线，而不是骆驼情况下，例如 customName将成为 CUSTOM_NAME）注意：目前只能使用内联常量来指定列名。
@NotNull | 字段不能为空
@Unique |唯一约束
@ToOne |一对一
@ToMany |一对多
@OrderBy |按照字段排序
@generated |由greendao产生的构造函数或方法
@Transient|表明这个字段不会被写入数据库，只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化
@Entity|将java普通类变为一个能够被greenDAO识别的数据库类型的实体类
@indexes|数据库索引
@schema|指定架构名称为实体
@active|无论是更新生成都刷新
@nameInDb|在数据库中的名字，如不写则为实体中类名
@createInDb|是否创建表，默认为true,false时不创建
## ScreenShot
![1](https://github.com/lv910929/GreenDao3/blob/master/art/ScreenShot1.jpg)
![2](https://github.com/lv910929/GreenDao3/blob/master/art/ScreenShot2.jpg)
![3](https://github.com/lv910929/GreenDao3/blob/master/art/ScreenShot3.jpg)

