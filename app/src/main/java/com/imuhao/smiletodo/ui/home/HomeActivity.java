package com.imuhao.smiletodo.ui.home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.bean.TodoBean;
import com.imuhao.smiletodo.bean.TodoDaoManager;
import com.imuhao.smiletodo.ui.about.AboutActivity;
import com.imuhao.smiletodo.ui.addtask.AddTaskActivity;
import com.imuhao.smiletodo.ui.setting.SettingActivity;
import com.imuhao.smiletodo.utils.ThemeUtils;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity
    implements SwipeRefreshLayout.OnRefreshListener {
  private FloatingActionButton mActionButton;
  private RecyclerView mRvTodo;
  private SwipeRefreshLayout mRefreshLayout;
  private Toolbar mToolbar;
  private TodoAdapter mAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    initView();
    initData();
  }

  private void initView() {
    initToolbar();
    initActionButton();
    initRecyclerView();
    initThemeColor();
  }

  private void initActionButton() {
    mActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
    mActionButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
        startActivity(intent);
      }
    });
  }

  private void initRecyclerView() {
    mRvTodo = (RecyclerView) findViewById(R.id.reciclerView);
    mRvTodo.setLayoutManager(new LinearLayoutManager(this));
    mRvTodo.setAdapter(mAdapter = new TodoAdapter());
    mAdapter.register(TodoBean.class, new TodoViewProvider());

    //添加item之间的分割线
    mRvTodo.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    //设置左右滑动删除

    //ItemTouchHelper helper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(mAdapter));
    //helper.attachToRecyclerView(mRvTodo);

    mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
    mRefreshLayout.setOnRefreshListener(this);
  }

  private void initToolbar() {
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.inflateMenu(R.menu.menu);
    mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        //设置页面
        if (itemId == R.id.action_setting) {
          Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
          startActivity(intent);
        }
        //关于我页面
        if (itemId == R.id.action_about) {
          Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
          startActivity(intent);
        }
        return true;
      }
    });
  }

  @Override protected void onRestart() {
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
      @Override public void call(Subscriber<? super List<TodoBean>> subscriber) {
        List<TodoBean> todoBeen = TodoDaoManager.queryAll();
        subscriber.onNext(todoBeen);
        subscriber.onCompleted();
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<TodoBean>>() {
          @Override public void call(List<TodoBean> todoBeen) {
            if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
              mRefreshLayout.setRefreshing(false);
            }
            mAdapter.setItems(todoBeen);
            //mTodoAdapter.setData(todoBeen);
          }
        });
  }

  @Override public void onRefresh() {
    initData();
  }
}
