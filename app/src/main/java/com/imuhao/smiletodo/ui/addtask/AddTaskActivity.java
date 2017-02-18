package com.imuhao.smiletodo.ui.addtask;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.model.TodoBean;
import com.imuhao.smiletodo.model.TodoDaoManager;
import com.imuhao.smiletodo.utils.AlertUtils;
import com.imuhao.smiletodo.utils.ThemeUtils;
import com.imuhao.smiletodo.wight.SmileCircular;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.kyleduo.switchbutton.SwitchButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by smile on 16-10-24.
 * 添加一个todo
 */

public class AddTaskActivity extends AppCompatActivity implements OnDateSetListener {
  private FloatingActionButton mActionButton;
  private TextInputLayout mInputLayout;
  private RelativeLayout mRoot;

  private boolean isEdit;
  private TodoBean mEditBean;
  private TimePickerDialog mTimePickerDialog;
  private SimpleDateFormat mSdf;
  private TextView mTvTime;
  private SmileCircular mSmileCircular;

  private SwitchButton mDataSwitch;
  private LinearLayout mLlTime;
  private TextView mBtnSetTime;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_task);

    initView();
  }

  private void initView() {

    findViewById(R.id.toolbar).setBackgroundColor(ThemeUtils.getThemeColor());
    findViewById(R.id.ll_title).setBackgroundColor(ThemeUtils.getThemeColor());

    mBtnSetTime = (TextView) findViewById(R.id.btn_set_time);
    mSmileCircular = (SmileCircular) findViewById(R.id.smileCircular);

    mSmileCircular.setColor(ThemeUtils.getThemeColor());
    mSmileCircular.startAnim();

    mTvTime = (TextView) findViewById(R.id.tv_time);
    findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    mBtnSetTime.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        setTime();
      }
    });
    mRoot = (RelativeLayout) findViewById(R.id.root);
    mActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
    mActionButton.setBackgroundTintList(ColorStateList.valueOf(ThemeUtils.getThemeColor()));
    mActionButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        String title = mInputLayout.getEditText().getText().toString();
        if (!checkInput(title)) return;

        if (isEdit) {
          edit(title);
        } else {
          add(title);
        }
      }
    });

    mInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);

    isEdit = getIntent().hasExtra("todo");
    if (isEdit) {
      mEditBean = (TodoBean) getIntent().getSerializableExtra("todo");
      mInputLayout.getEditText().setText(mEditBean.getTitle());
      mInputLayout.getEditText().setSelection(mEditBean.getTitle().length());
    }

    //是否设置时间
    mLlTime = (LinearLayout) findViewById(R.id.ll_time);
    mDataSwitch = (SwitchButton) findViewById(R.id.time_switch);
    mDataSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkAnimation(isChecked);
      }
    });
  }

  //切换动画状态
  private void checkAnimation(boolean checked) {
    if (checked) {
      mLlTime.animate().setDuration(500).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
        @Override public void onAnimationStart(Animator animation) {
          mLlTime.setVisibility(View.VISIBLE);
        }
      });
      //显示动画
    } else {
      //隐藏动画
      mLlTime.animate().setDuration(500).alpha(0.0f).setListener(new AnimatorListenerAdapter() {
        @Override public void onAnimationEnd(Animator animation) {
          mLlTime.setVisibility(View.GONE);
        }
      });
    }
  }

  private void add(final String title) {
    Observable.create(new Observable.OnSubscribe<Boolean>() {
      @Override public void call(Subscriber<? super Boolean> subscriber) {
        TodoBean bean = new TodoBean();
        bean.setTitle(title);

        if (!TextUtils.isEmpty(mTvTime.getText())) bean.setTime(mTvTime.getText().toString());

        bean.setComplete(false);
        bean.setIsRemind(false);
        long id = TodoDaoManager.addTodo(bean);
        subscriber.onNext(id != 0);
        subscriber.onCompleted();
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Boolean>() {
          @Override public void call(Boolean aBoolean) {
            if (aBoolean) {

              AlertUtils.show( "添加成功!");
              finish();
            }
          }
        });
  }

  private void edit(final String title) {
    Observable.create(new Observable.OnSubscribe<Boolean>() {
      @Override public void call(Subscriber<? super Boolean> subscriber) {
        mEditBean.setTitle(title);
        if (!TextUtils.isEmpty(mTvTime.getText())) mEditBean.setTime(mTvTime.getText().toString());

        TodoDaoManager.update(mEditBean);
        subscriber.onNext(true);
        subscriber.onCompleted();
      }
    })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Action1<Boolean>() {
          @Override public void call(Boolean aBoolean) {
            Toast.makeText(AddTaskActivity.this, "编辑成功!", Toast.LENGTH_SHORT).show();
            finish();
          }
        });
  }

  //选择时间
  private void setTime() {
    long fourYears = 4L * 365 * 1000 * 60 * 60 * 24L;
    if (mTimePickerDialog == null) {
      mTimePickerDialog = new TimePickerDialog.Builder().setCallBack(this)
          .setCancelStringId("取消")
          .setSureStringId("确认")
          .setTitleStringId("日常")
          .setYearText("年")
          .setMonthText("月")
          .setDayText("日")
          .setHourText("时")
          .setMinuteText("分")
          .setCyclic(false)
          .setMinMillseconds(System.currentTimeMillis())
          .setMaxMillseconds(System.currentTimeMillis() + fourYears)
          .setCurrentMillseconds(System.currentTimeMillis())
          .setThemeColor(ThemeUtils.getThemeColor())
          .setType(Type.ALL)
          .setWheelItemTextNormalColor(
              getResources().getColor(R.color.timetimepicker_default_text_color))
          .setWheelItemTextSelectorColor(ThemeUtils.getThemeColor())
          .setWheelItemTextSize(12)
          .build();
    }
    mTimePickerDialog.show(getSupportFragmentManager(), "smile");
  }

  public boolean checkInput(String title) {
    if (TextUtils.isEmpty(title)) {
      //Snackbar.make(mRoot, "Please enter a title!", Snackbar.LENGTH_SHORT).show();
      AlertUtils.show( "请输入内容!");
      return false;
    }

    return true;
  }

  @Override public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
    if (mSdf == null) {
      mSdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
      mSdf.applyPattern("yyyy-MM-dd HH:mm");
    }
    String time = mSdf.format(new Date(millseconds));
    mTvTime.setText(time);
  }
}
