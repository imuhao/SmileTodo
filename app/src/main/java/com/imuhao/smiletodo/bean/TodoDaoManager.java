package com.imuhao.smiletodo.bean;

import com.imuhao.smiletodo.App;

import java.util.List;

import static com.imuhao.smiletodo.App.getSession;

/**
 * Created by smile on 16-10-25.
 */

public class TodoDaoManager {


    /**
     * 查询所有未完成的任务
     */
    public static List<TodoBean> queryAll() {
        return getSession().getTodoBeanDao().queryBuilder()
                .where(TodoBeanDao.Properties.IsComplete.eq(false)).list();
    }

    /**
     * 添加一个bean对象到数据库中
     */
    public static long addTodo(TodoBean bean) {
        return getSession().getTodoBeanDao().insert(bean);
    }

    /**
     * 根据id获取到执行的bean对象
     */
    public static TodoBean query(long id) {
        return getSession().getTodoBeanDao().queryBuilder().where(TodoBeanDao.Properties.Id.eq(id)).build().unique();
    }

    /**
     * 根据id从数据库删除一个bean对象
     */
    public static void remove(TodoBean bean) {
        App.getSession().getTodoBeanDao().delete(bean);
    }

    /**
     * 更新bean对象
     */
    public static void update(TodoBean bean) {
        App.getSession().getTodoBeanDao().update(bean);
    }

    public static void removeAll() {
        App.getSession().getTodoBeanDao().getSession().deleteAll(TodoBean.class);
    }
}
