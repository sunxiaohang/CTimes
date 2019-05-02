package com.sunxiaohang.root.ctimes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class MediaFragment extends Fragment {
    private MagicIndicator mediaMagicIndicator;
    private MagicIndicator autoScrollViewIndicator;
    private ViewPager autoScrollViewPager;
    private ViewPager mediaContentViewPager;
    private AVLoadingIndicatorView loadingIndicator;
    private ArrayList<String> mediaTypes;
    private ArrayList<Drawable> autoScrollImages;
    private final int SCROLL_DURATION = 100;
    private Thread handlerAutoScrollImageThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.what = SCROLL_DURATION;
                handlerAutoScrollImage.sendMessage(message);
            }
        }
    });
    private Handler handlerAutoScrollImage = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SCROLL_DURATION:
                    int forwardIndex = autoScrollViewPager.getCurrentItem();
                    System.out.println("TAG: forward index :"+forwardIndex);
                    System.out.println("TAG: autoScrollImages lengths :"+autoScrollImages.size());
                    if(forwardIndex+1 == autoScrollImages.size()) autoScrollViewPager.setCurrentItem(0);
                    else autoScrollViewPager.setCurrentItem(++forwardIndex);
                    return;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        autoScrollImages = Utils.getAutoScrollImageDrawables(getContext());
        View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_media,container,false);
        mediaContentViewPager = rootView.findViewById(R.id.media_content_view_pager);
        autoScrollViewPager = rootView.findViewById(R.id.media_view_pager_auto_scroll);
        mediaMagicIndicator = rootView.findViewById(R.id.view_pager_media_indicator);
        loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        autoScrollViewIndicator = rootView.findViewById(R.id.auto_scroll_view_pager_indicator);
        loadingIndicator.show();
        //mediaContentViewPager
        mediaContentViewPager.setAdapter(new MediaViewPagerAdapter(loadingIndicator));
        //auto scroll view pager bind adapter
        autoScrollViewPager.setAdapter(new AutoScrollViewPagerAdapter());
        mediaTypes = Utils.getMediaType();
        //bind auto scroll viewpager indicator and auto scroll viewpager
        CommonNavigator autoScrollNavigator = new CommonNavigator(getContext());
        autoScrollNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return autoScrollImages.size();
            }
            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ColorTransitionPagerTitleView view = new ColorTransitionPagerTitleView(context);
                view.setNormalColor(getResources().getColor(R.color.colorFont));
                view.setSelectedColor(Color.parseColor(Utils.getRandomColor()));
                view.setText(i+"");
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autoScrollViewPager.setCurrentItem(i);
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
        autoScrollViewIndicator.setNavigator(autoScrollNavigator);
        ViewPagerHelper.bind(autoScrollViewIndicator,autoScrollViewPager);

        //bind viewpager indicator and viewpager
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mediaTypes.size();
            }
            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ColorTransitionPagerTitleView view = new ColorTransitionPagerTitleView(context);
                view.setNormalColor(getResources().getColor(R.color.colorFont));
                view.setSelectedColor(Color.parseColor(Utils.getRandomColor()));
                view.setText(Html.fromHtml(Utils.getCapColorText(mediaTypes.get(i))));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //未完成
                        mediaContentViewPager.setCurrentItem(i);
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
        mediaMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mediaMagicIndicator,mediaContentViewPager);
        return rootView;
    }
    class AutoScrollViewPagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(autoScrollImages.get(position));
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(layoutParams);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return autoScrollImages.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view,Object o) {
            return view == o;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        handlerAutoScrollImageThread.start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
