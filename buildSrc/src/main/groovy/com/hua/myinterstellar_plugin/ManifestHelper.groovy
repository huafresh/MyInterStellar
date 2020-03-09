package com.hua.myinterstellar_plugin

import groovy.util.slurpersupport.GPathResult

class ManifestHelper {
    private String mPath
    private GPathResult manifest;

    ManifestHelper(String path){
        this.mPath = path
        manifest = new XmlSlurper().parse(path)
    }

    void changeProcessName(String processName){
        def dispatcherProvider = manifest.application.children().find { providerTag->
            return providerTag."@android:authorities" == "com.hua.myinterstellar.dispatcher.provider"
        }
        println dispatcherProvider
    }

    void insertProviderTag(){

    }
}