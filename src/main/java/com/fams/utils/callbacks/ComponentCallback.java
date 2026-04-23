package com.fams.utils.callbacks;

import com.fams.view.pmv.components.AspectComponent;
import com.fams.view.pmv.components.BaseComponent;

@FunctionalInterface
public interface ComponentCallback {
    void call(BaseComponent component);
}
