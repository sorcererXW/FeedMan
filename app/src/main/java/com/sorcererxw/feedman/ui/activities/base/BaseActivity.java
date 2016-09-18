package com.sorcererxw.feedman.ui.activities.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/5
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getContentViewId();

    protected abstract void init(Bundle saveInstance);

    protected void handleIntent(Intent intent) {

    }

    @Override
    protected void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        handleIntent(getIntent());
        init(saveInstance);
    }
}
