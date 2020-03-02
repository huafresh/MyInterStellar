// ITransfer.aidl
package com.hua.myinterstellar_core;
import com.hua.myinterstellar_core.bean.StellarMethod;
import com.hua.myinterstellar_core.bean.Reply;
interface ITransfer {
     Reply invoke(String serviceName, inout StellarMethod method);
}
