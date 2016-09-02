package com.sorcererxw.feedman.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sorcererxw.feedman.R;

import butterknife.BindView;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/2
 */
public class SourceFragment extends BaseFragment {
    @BindView(R.id.recyclerView_source_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab_source_add)
    FloatingActionButton mFab;

    public static SourceFragment newInstance() {
        return new SourceFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_source;
    }

    @Override
    protected void initView(View view, Bundle saveInstance) {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getContext())
                        .customView(R.layout.layout_edittext, true)
                        .positiveText("add")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog,
                                                @NonNull DialogAction which) {
                                if (dialog.getCustomView() != null) {
                                    EditText editText = (EditText) dialog.getCustomView()
                                            .findViewById(R.id.editText);
                                    add(editText.getText().toString());
                                }
                            }
                        })
                        .build().show();
            }
        });
    }

    private void add(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
