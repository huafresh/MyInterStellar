package com.hua.myinterstellar_core;

import com.hua.myinterstellar_core.bean.BinderBean;

interface IDispatcher {
    void registerRemoteService(String name, in BinderBean transferInfo);
    void unRegisterRemoteService(String name);
    BinderBean getTransferInfo(String name);
}
