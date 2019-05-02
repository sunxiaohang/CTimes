package com.sunxiaohang.root.ctimes;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

public class MoviesFragment extends Fragment{
    private MagicIndicator indicator;
    private ViewPager viewPager;
    private ArrayList<String> viewPagerString = new ArrayList<>();
    private AVLoadingIndicatorView avLoadingIndicatorView;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_movies,container,false);
        indicator = rootView.findViewById(R.id.movies_indicator);
        viewPager = rootView.findViewById(R.id.movies_view_pager);
        avLoadingIndicatorView = rootView.findViewById(R.id.av_loading_indicator);
        avLoadingIndicatorView.show();
        viewPagerString.add("onPlaying");
        viewPagerString.add("comingSoon");
        viewPagerString.add("topBand");
        viewPager.setAdapter(new MoviesViewPagerAdapter(getContext(),viewPagerString,avLoadingIndicatorView));
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return viewPagerString.size();
            }
            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ColorTransitionPagerTitleView view = new ColorTransitionPagerTitleView(context);
                view.setNormalColor(getResources().getColor(R.color.colorFont));
                view.setSelectedColor(Color.parseColor(Utils.getRandomColor()));
                view.setText(Html.fromHtml(Utils.getCapColorText(viewPagerString.get(i))));
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
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator,viewPager);
        return rootView;
    }
}
