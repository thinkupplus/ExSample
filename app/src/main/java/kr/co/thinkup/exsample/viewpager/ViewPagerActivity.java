package kr.co.thinkup.exsample.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import kr.co.thinkup.exsample.R;

/**
 * 2019-08-02 by yh.Choi
 */
public class ViewPagerActivity extends AppCompatActivity {

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


        ViewPager viewPager = findViewById(R.id.viewpager);
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
