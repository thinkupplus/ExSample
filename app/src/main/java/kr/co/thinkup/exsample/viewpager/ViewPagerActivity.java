package kr.co.thinkup.exsample.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import kr.co.thinkup.exsample.R;

/**
 * 2019-08-02 by yh.Choi
 *
 * https://dev-imaec.tistory.com/13
 */
public class ViewPagerActivity extends AppCompatActivity {

    private static final String TAG = "ViewPagerActivity";

    public TextView             tv_viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpageer);

        ArrayList<Integer> listimages = new ArrayList<>();
        listimages.add(R.drawable.littledeep_tree_style1);
        listimages.add(R.drawable.littledeep_tree_style2);
        listimages.add(R.drawable.littledeep_tree_style3);
        listimages.add(R.drawable.asdf);
        listimages.add(R.drawable.asfrweeewr);
        listimages.add(R.drawable.lkjewr);


        tv_viewpager = findViewById(R.id.tv_viewpager);

        final ViewPager viewPager = findViewById(R.id.viewpager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(fragmentAdapter);

        viewPager.setClipToPadding(false);

        int dpValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int)(dpValue * d);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        for(int i =0; i< listimages.size(); i++) {
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listimages.get(i));
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }

        fragmentAdapter.notifyDataSetChanged();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
//                Log.d(TAG, "onPageScrolled: " + i);
            }

            @Override
            public void onPageSelected(int i) {
                Log.d(TAG, "onPageSelected: " + i);
                setViewPager_Title(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {
//                Log.d(TAG, "onPageScrollStateChanged: " + i );
            }
        });
    }


    public void setViewPager_Title(int page) {

        String msg = String.format(Locale.getDefault(), "Page No = [ %d ]", page);

//        tv_viewpager.setText(msg);

        tv_viewpager.append("\n");
        tv_viewpager.append(msg);

    }
}

class FragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();


    FragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addItem(Fragment fragment) {
        fragments.add(fragment);
    }
}
