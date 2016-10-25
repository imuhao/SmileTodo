package com.imuhao.smiletodo.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.io.Serializable;

/**
 * Created by smile on 16-10-25.
 */
@Entity(active = true)
public class TodoBean implements Serializable {

    public static final long serialVersionUID = 1;

    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String title;       //任务的名称
    private String time;        //执行的时间
    private boolean isComplete = false; //是否已完成
    private boolean isRemind = false;   //是否提醒
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1091505684)
    private transient TodoBeanDao myDao;


    @Generated(hash = 2091220736)
    public TodoBean(Long id, @NotNull String title, String time, boolean isComplete,
                    boolean isRemind) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.isComplete = isComplete;
        this.isRemind = isRemind;
    }

    @Generated(hash = 1563990781)
    public TodoBean() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isRemind() {
        return isRemind;
    }

    public void setRemind(boolean remind) {
        isRemind = remind;
    }


    @Override
    public String toString() {
        return "TodoBean{" +
                "title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", isComplete=" + isComplete +
                ", isRemind=" + isRemind +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsComplete() {
        return this.isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public boolean getIsRemind() {
        return this.isRemind;
    }

    public void setIsRemind(boolean isRemind) {
        this.isRemind = isRemind;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 386798789)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTodoBeanDao() : null;
    }
}
