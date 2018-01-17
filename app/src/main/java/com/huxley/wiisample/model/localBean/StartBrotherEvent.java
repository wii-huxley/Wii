package com.huxley.wiisample.model.localBean;

import com.huxley.fragmentation.base.SupportFragment;

/**
 * Created by huxley on 2017/9/17.
 */

public class StartBrotherEvent {
    public SupportFragment targetFragment;

    public StartBrotherEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
