// ITransfer.aidl
package com.hua.myinterstellar_core;

interface ICallback {
     void onSuccess(in Bundle result);
     void onFail(String errorMsg);
}
