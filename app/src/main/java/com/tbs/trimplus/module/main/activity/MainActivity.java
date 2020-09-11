package com.tbs.trimplus.module.main.activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.base.BaseFragment;
import com.tbs.trimplus.module.main.fragment.BibleFragment;
import com.tbs.trimplus.module.main.fragment.HomeFragment;
import com.tbs.trimplus.module.main.fragment.MineFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    private ArrayList<BaseFragment> fragments;
    private int position = 0;
    private Fragment oldFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();
        initListenter();
    }

    /**
     * 添加的时候指定顺序，调用的时候根据对应的位置
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new BibleFragment());
        fragments.add(new MineFragment());
    }

    //初始化radiogroup的监听
    private void initListenter() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_bible:
                        position = 1;
                        break;
                    case R.id.rb_mine:
                        position = 2;
                        break;
                    default:
                        position = 0;
                        break;
                }
                BaseFragment newFragment = getFragment(position);
                switchFragment(oldFragment, newFragment);
            }
        });
        rgMain.check(R.id.rb_home);
    }

    //从fragment集合中获取fragment
    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            return fragments.get(position);
        }
        return null;
    }

    //切换fragment
    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (oldFragment != nextFragment) {
            oldFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.framelayout, nextFragment).commit();
                } else {
                    //隐藏当前fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }


}
