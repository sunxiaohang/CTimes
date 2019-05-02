
package com.sunxiaohang.root.ctimes.fabreveal;

import android.view.View;

public interface OnRevealChangeListener {
    void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView);
    void onSecondaryViewAppeared(FABRevealLayout fabRevealLayout, View secondaryView);
}
