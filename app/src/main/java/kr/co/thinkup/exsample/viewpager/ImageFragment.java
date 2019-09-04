package kr.co.thinkup.exsample.viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import kr.co.thinkup.exsample.R;


/**
 * 2019-08-02 by yh.Choi
 */
public class ImageFragment extends Fragment {

    private static final String TAG = "ImageFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_fragment_image, container, false);

        ImageView iv_content = view.findViewById(R.id.iv_content);

        if(getArguments() != null) {
            Bundle args = getArguments();

            iv_content.setImageResource(args.getInt("imgRes"));
        }

        return view;
    }
}
