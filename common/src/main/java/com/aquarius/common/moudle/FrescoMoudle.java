package com.aquarius.common.moudle;

import android.content.Context;
import com.aquarius.base.BaseMoudle;
import com.facebook.drawee.backends.pipeline.Fresco;

public class FrescoMoudle implements BaseMoudle {
    @Override
    public void init(Context context) {
        Fresco.initialize(context);
    }
}
