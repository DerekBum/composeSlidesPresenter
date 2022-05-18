package com.github.derekbum.composeslidespresenter

import androidx.compose.runtime.mutableStateOf
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import java.io.File
import javax.swing.JFrame

val presentation = Presentation()
var presentationOpened: Boolean = false
var presentationSlidePath = mutableStateOf(File(""))

class LoadAllSlides: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {

        //window = JFrame()
        frame = JFrame()
        frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        frame.isLocationByPlatform = true
        frame.isVisible = true

        frame.extendedState = JFrame.MAXIMIZED_BOTH

        presentation.presentationReader(event)

        val vf = event.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY)

        for (i in 0 until presentation.slides.size) {
            val file = vf?.findFileByRelativePath(presentation.slides[i])
            presentation.slides[i] = file.toString().removePrefix("file://")
            println(presentation.slides[i])
        }

        presentation.index = 0
        if (presentation.slides[0].takeLast(3) == "jpg" || presentation.slides[0].takeLast(3) == "png")
            presentation.type = "Image"
        else presentation.type = "Editor"

        println(presentation.type)

        ShowSlide().actionPerformed(event)
    }
}

class NextSlideAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {

        //println(presentationOpened)

        if (!presentationOpened)
            presentation.presentationReader(event)

        presentation.index++

        if (presentation.index >= presentation.slides.size) {
            presentation.index--
            return
        }

        if (presentation.slides[presentation.index].takeLast(3) == "jpg" || presentation.slides[presentation.index].takeLast(3) == "png")
            presentation.type = "Image"
        else presentation.type = "Editor"

        println(presentation.type)

        try {
            ShowSlide().actionPerformed(event)
        } catch (e: java.lang.Exception) {
            return
        }
    }
}

class PreviousSlideAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {

        println(presentationOpened)

        if (!presentationOpened)
            presentation.presentationReader(event)

        presentation.index--

        if (presentation.index < 0) {
            presentation.index++
            return
        }

        if (presentation.slides[presentation.index].takeLast(3) == "jpg" || presentation.slides[presentation.index].takeLast(3) == "png")
            presentation.type = "Image"
        else presentation.type = "Editor"

        try {
            ShowSlide().actionPerformed(event)
        } catch (e: java.lang.Exception) {
            return
        }
    }
}