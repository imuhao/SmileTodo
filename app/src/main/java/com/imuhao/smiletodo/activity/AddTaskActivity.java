package com.imuhao.smiletodo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.model.TodoBean;
import com.imuhao.smiletodo.model.TodoDaoManager;
import com.imuhao.smiletodo.wight.SmileCircular;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        initView();
    }

    private void initView() {
        mSmileCircular = (SmileCircular) findViewById(R.id.smileCircular);
        mSmileCircular.startAnim();
        mTvTime = (TextView) findViewById(R.id.tv_time);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_set_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });
        mRoot = (RelativeLayout) findViewById(R.id.root);
        mActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mInputLayout.getEditText().getText().toString();
                if (!checkInput(title)) return;

                if (isEdit) edit(title);
                else add(title);
            }
        });
        mInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);

        isEdit = getIntent().hasExtra("todo");
        if (isEdit) {
            mEditBean = (TodoBean) getIntent().getSerializableExtra("todo");
            mInputLayout.getEditText().setText(mEditBean.getTitle());
            mInputLayout.getEditText().setSelection(mEditBean.getTitle().length());
        }


    }

    private void add(final String title) {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                TodoBean bean = new TodoBean();
                bean.setTitle(title);

                if (!TextUtils.isEmpty(mTvTime.getText()))
                    bean.setTime(mTvTime.getText().toString());

                bean.setComplete(false);
                bean.setIsRemind(false);
                long id = TodoDaoManager.addTodo(bean);
                subscriber.onNext(id != 0);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(AddTaskActivity.this, "Insert Success！", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    private void edit(final String title) {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                mEditBean.setTitle(title);
                if (!TextUtils.isEmpty(mTvTime.getText()))
                    mEditBean.setTime(mTvTime.getText().toString());

                TodoDaoManager.update(mEditBean);
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        Toast.makeText(AddTaskActivity.this, "Edit success！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    //选择时间
    private void setTime() {
        long fourYears = 4L * 365 * 1000 * 60 * 60 * 24L;
        if (mTimePickerDialog == null) {
            mTimePickerDialog = new TimePickerDialog.Builder()
                    .setCallBack(this)
                    .setCancelStringId("Cancel")
                    .setSureStringId("Sure")
                    .setTitleStringId("SmileTodo")
                    .setYearText("Year")
                    .setMonthText("Month")
                    .setDayText("Day")
                    .setHourText("Hour")
                    .setMinuteText("Minute")
                    .setCyclic(false)
                    .setMinMillseconds(System.currentTimeMillis())
                    .setMaxMillseconds(System.currentTimeMillis() + fourYears)
                    .setCurrentMillseconds(System.currentTimeMillis())
                    .setThemeColor(getResources().getColor(R.color.backgroupColor))
                    .setType(Type.ALL)
                    .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                    .setWheelItemTextSelectorColor(getResources().getColor(R.color.backgroupColor))
                    .setWheelItemTextSize(12)
                    .build();
        }
        mTimePickerDialog.show(getSupportFragmentManager(), "smile");
    }

    public boolean checkInput(String title) {
        if (TextUtils.isEmpty(title)) {
            View view = getWindow().getDecorView();

            Snackbar.make(view, "Please enter a title!", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        if (mSdf == null) {
            mSdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
            mSdf.applyPattern("yyyy-MM-dd HH:mm");
        }
        String time = mSdf.format(new Date(millseconds));
        mTvTime.setText(time);
    }
}
