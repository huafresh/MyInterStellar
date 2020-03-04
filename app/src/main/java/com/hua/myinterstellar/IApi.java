package com.hua.myinterstellar;

import com.hua.myinterstellar_core.ICallback;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-01 11:13
 */

public interface IApi {

    int testBasicType(int a, int b);

    String testParcelable(ManInfo manInfo);

    void testInOut(ManInfo manInfo);

    void testCallback(ICallback callback);
}
