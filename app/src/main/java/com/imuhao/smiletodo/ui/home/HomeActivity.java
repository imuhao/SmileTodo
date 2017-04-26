package com.imuhao.smiletodo.ui.home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.bean.TodoBean;
import com.imuhao.smiletodo.bean.TodoDaoManager;
import com.imuhao.smiletodo.inter.ItemTouchHelperCallback;
import com.imuhao.smiletodo.inter.SimpleItemTouchHelperCallback;
import com.imuhao.smiletodo.ui.about.AboutActivity;
import com.imuhao.smiletodo.ui.addtask.AddTaskActivity;
import com.imuhao.smiletodo.ui.base.BaseRefreshActivity;
import com.imuhao.smiletodo.ui.setting.SettingActivity;
import com.imuhao.smiletodo.utils.AlertUtils;
import com.imuhao.smiletodo.utils.ListUtils;
import com.imuhao.smiletodo.utils.ThemeUtils;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HomeActivity extends BaseRefreshActivity implements ItemTouchHelperCallback {
  private FloatingActionButton mActionButton;
  private RecyclerView mRvTodo;
  private Toolbar mToolbar;
  private TodoAdapter mAdapter;
  private LinearLayout mLlEmpty;
  private TodoDataObserver observer;

  @Override protected int getLayoutId() {
    return R.layout.activity_home;
  }

  public void initView() {
    mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty_layout);
    mActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
    mRvTodo = (RecyclerView) findViewById(R.id.recyclerView);
    mToolbar = (Toolbar) findViewById(R.id.toolbar);

    initToolbar();
    initActionButton();
    initRecyclerView();
    initThemeColor();
  }

  private void initActionButton() {
    mActionButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
        startActivity(intent);
      }
    });
  }

  private void initRecyclerView() {
    mRvTodo.setLayoutManager(new LinearLayoutManager(this));
    mRvTodo.setAdapter(mAdapter = new TodoAdapter());
    mAdapter.register(TodoBean.class, new TodoViewProvider());
    observer = new TodoDataObserver();
    mAdapter.registerAdapterDataObserver(observer);
    mRvTodo.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    ItemTouchHelper helper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(this));
    helper.attachToRecyclerView(mRvTodo);
  }

  private void initToolbar() {
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

  @Override protected void onResume() {
    super.onResume();
    initThemeColor();
    loadData(true);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mAdapter.unregisterAdapterDataObserver(observer);
  }

  private void initThemeColor() {
    int color = ThemeUtils.getThemeColor();
    mActionButton.setBackgroundTintList(ColorStateList.valueOf(color));
    mToolbar.setBackgroundColor(color);
  }

  @Override protected void loadData(final boolean clear) {

    Observable.just(true)
        .map(new Func1<Boolean, List<TodoBean>>() {
          @Override public List<TodoBean> call(Boolean aBoolean) {
            return TodoDaoManager.queryAll();
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(new Action1<List<TodoBean>>() {
          @Override public void call(List<TodoBean> todoBeen) {
            showEmptyView(todoBeen);
          }
        })
        .subscribe(new Action1<List<TodoBean>>() {
          @Override public void call(List<TodoBean> todoBeen) {
            setRefresh(false);
            List<TodoBean> items =
                clear ? new ArrayList<TodoBean>() : new ArrayList<TodoBean>(mAdapter.getItems());
            items.addAll(todoBeen);
            mAdapter.setItems(items);
          }
        });
  }

  private void showEmptyView(List list) {
    if (list == null || list.isEmpty()) {
      //显示空视图
      mLlEmpty.setVisibility(View.VISIBLE);
      setVisibility(View.GONE);
    } else {
      //显示数据
      setVisibility(View.VISIBLE);
      mLlEmpty.setVisibility(View.GONE);
    }
  }

  /**
   * 当滑动删除时调用该方法
   */
  @Override public void onItemRemoved(RecyclerView.ViewHolder viewHolder) {
    int position = viewHolder.getAdapterPosition();
    List<TodoBean> items = mAdapter.getItems();
    if (items.isEmpty() || position > items.size() - 1) {
      return;
    }
    TodoBean removeBean = items.remove(position);
    TodoDaoManager.remove(removeBean);
    //mAdapter.notifyItemChanged(position);

    AlertUtils.show("删除 " + removeBean.getTitle() + " 成功!");
    loadData(true);
  }

  public class TodoDataObserver extends RecyclerView.AdapterDataObserver {
    @Override public void onChanged() {
      super.onChanged();
      ListUtils.sortTop(mAdapter.getItems());
    }
  }
}
