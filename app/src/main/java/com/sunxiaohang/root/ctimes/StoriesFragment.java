package com.sunxiaohang.root.ctimes;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunxiaohang.root.ctimes.utils.Utils;
import com.wang.avi.AVLoadingIndicatorView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import java.util.ArrayList;

public class StoriesFragment extends Fragment {
    private ViewPager viewPager;
    private AVLoadingIndicatorView indicatorView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stories,container,false);
        viewPager = rootView.findViewById(R.id.stories_viewpager);
        indicatorView = rootView.findViewById(R.id.loading_indicator);
        indicatorView.show();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        final ArrayList<String> subscribedSubjects = getArguments().getStringArrayList("subjects");
        viewPager.setAdapter(new StoriesViewPagerAdapter(subscribedSubjects,indicatorView));
        MagicIndicator magicIndicator = rootView.findViewById(R.id.view_pager_stories_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return subscribedSubjects.size();
            }
            @Override
            public IPagerTitleView getTitleView(Context context,final int i) {
                ColorTransitionPagerTitleView view = new ColorTransitionPagerTitleView(context);
                view.setNormalColor(getResources().getColor(R.color.colorFont));
                view.setSelectedColor(Color.parseColor(Utils.getRandomColor()));
                view.setText(Html.fromHtml(Utils.getCapColorText(subscribedSubjects.get(i))));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(i);
                    }
                });
                return view;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator,viewPager);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(indicatorView.isShown())indicatorView.hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(indicatorView.isShown())indicatorView.hide();
    }
}
