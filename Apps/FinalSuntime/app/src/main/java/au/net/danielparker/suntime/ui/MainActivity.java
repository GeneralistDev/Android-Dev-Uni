package au.net.danielparker.suntime.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.EActivity;

import au.net.danielparker.suntime.R;

/**
 * Created by danielparker on 28/10/14.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab presetsTab = actionBar.newTab()
                                    .setText(R.string.presets)
                                    .setTabListener(new TabListener<LocationsFragment_>(
                                            this, "Presets", LocationsFragment_.class
                                    ));

        actionBar.addTab(presetsTab);

        ActionBar.Tab customTab = actionBar.newTab()
                                    .setText(R.string.custom)
                                    .setTabListener(new TabListener<CustomFragment_>(
                                            this, "Custom", CustomFragment_.class
                                    ));

        actionBar.addTab(customTab);
    }

    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {

        private Fragment mFragment = null;
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;

        public TabListener(Activity activity, String tag, Class<T> clz) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (mFragment == null) {
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                ft.attach(mFragment);
                //fragmentTransaction.show(mFragment);
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.detach(mFragment);
                //fragmentTransaction.hide(mFragment);
            }
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    }
}
