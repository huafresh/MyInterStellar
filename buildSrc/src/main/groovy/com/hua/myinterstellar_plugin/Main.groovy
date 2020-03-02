package com.hua.myinterstellar_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class Main implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("myStellarConfig", MyStellarConfig.class)
        Task temp2 = project.task("temp2")
        temp2.doLast {
            MyStellarConfig stellarConfig = project.extensions.getByName("myStellarConfig")
            System.out.println("hello, plugin, name = ${stellarConfig.processName}")
        }
    }
}

class MyStellarConfig {
    private String processName

    String getProcessName() {
        return processName
    }

    void setProcessName(String processName) {
        this.processName = processName
    }
}