package com.imuhao.smiletodo.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;

import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.adapter.DividerItemDecoration;
import com.imuhao.smiletodo.adapter.SimpleItemTouchHelperCallback;
import com.imuhao.smiletodo.adapter.TodoAdapter;
import com.imuhao.smiletodo.model.TodoBean;
import com.imuhao.smiletodo.model.TodoDaoManager;
import com.imuhao.smiletodo.utils.ThemeUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private FloatingActionButton mActionButton;
    private RecyclerView mRvTodo;
    private TodoAdapter mTodoAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("SmileTodo");
        mToolbar.inflateMenu(R.menu.menu);
        mToolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        //设置页面
                        if (itemId == R.id.action_setting) {
                            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                            startActivity(intent);
                        }
                        //关于我页面
                        if (itemId == R.id.action_about) {
                            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    }
                });

        mActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });


        mRvTodo = (RecyclerView) findViewById(R.id.reciclerView);

        mRvTodo.setLayoutManager(new LinearLayoutManager(this));
        mRvTodo.setItemAnimator(new DefaultItemAnimator());
        mTodoAdapter = new TodoAdapter(this);
        mRvTodo.setAdapter(mTodoAdapter);
        //添加item之间的分割线
        mRvTodo.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        //设置左右滑动删除
        ItemTouchHelper helper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(mTodoAdapter));
        helper.attachToRecyclerView(mRvTodo);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(this);
        initThemeColor();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
        initThemeColor();
    }

    private void initThemeColor() {
        int color = ThemeUtils.getThemeColor();
        mRefreshLayout.setColorSchemeColors(color);
        mActionButton.setBackgroundTintList(ColorStateList.valueOf(color));
        mToolbar.setBackgroundColor(color);
    }

    public void initData() {
        Observable.create(new Observable.OnSubscribe<List<TodoBean>>() {
            @Override
            public void call(Subscriber<? super List<TodoBean>> subscriber) {
                List<TodoBean> todoBeen = TodoDaoManager.queryAll();
                subscriber.onNext(todoBeen);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<TodoBean>>() {
                    @Override
                    public void call(List<TodoBean> todoBeen) {
                        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
                            mRefreshLayout.setRefreshing(false);
                        }
                        mTodoAdapter.setData(todoBeen);
                    }
                });
    }

    @Override
    public void onRefresh() {
        initData();
    }
}
