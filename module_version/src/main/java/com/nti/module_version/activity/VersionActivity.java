package com.nti.module_version.activity;


import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nti.lib_common.activity.BaseActivity;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.module_version.R;
@Route(path = ARouterPath.VERSION_PATH)
public class VersionActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
    }
}