package com.hua.myinterstellar_plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.tasks.ManifestProcessorTask
import groovy.xml.MarkupBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class Main implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "hello, my gradle plugin"
        project.extensions.create("myStellarConfig", MyStellarConfig.class)
        def appExtension = project.extensions.getByType(AppExtension)
        appExtension.applicationVariants.all { variant ->
            variant.outputs.each { output ->
                output.getProcessManifestProvider().get().doLast { ManifestProcessorTask task ->
                    MyStellarConfig stellarConfig = project.extensions.getByName("myStellarConfig")
                    def writer = new StringWriter()
                    def markupBuilder = new MarkupBuilder(writer)
                    markupBuilder.provider(
                            'android:name': 'com.hua.myinterstellar_core.StellarContentProvider',
                            'android:authorities': 'com.hua.myinterstellar.dispatcher.provider',
                            'android:process': stellarConfig.processName
                    )
                    def insertContent = writer.toString()
                    task.outputs.files.each { file ->
                        updateManifest(file, insertContent)
                    }
                }
            }
        }
    }

    def updateManifest(File file, String content) {
        println "updateManifest: $file"
        if (file == null || !file.exists()) return
        if (file.isDirectory()) {
            file.listFiles().each {
                updateManifest(it, content)
            }
        } else if (file.name.equalsIgnoreCase("AndroidManifest.xml")) {
            appendManifest(file, content)
        }
    }

    static def appendManifest(File file, String content) {
        def updatedContent = file.getText("utf-8")
                .replaceAll("</application>", "$content \n </application>")
         file.write(updatedContent, "utf-8")
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