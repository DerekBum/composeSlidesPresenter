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
        }

        presentation.index = 0
        if (presentation.slides[0].takeLast(3) == "jpg" || presentation.slides[0].takeLast(3) == "png")
            presentation.type = "Image"
        else presentation.type = "Editor"

        ShowSlide().actionPerformed(event)
    }
}

class NextSlideAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {

        if (!presentationOpened)
            presentation.presentationReader(event)

        if (presentation.moveSlide(1) == -1)
            return

        try {
            ShowSlide().actionPerformed(event)
        } catch (e: java.lang.Exception) {
            return
        }
    }
}

class PreviousSlideAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {

        if (!presentationOpened)
            presentation.presentationReader(event)

        if (presentation.moveSlide(-1) == -1)
            return

        try {
            ShowSlide().actionPerformed(event)
        } catch (e: java.lang.Exception) {
            return
        }
    }
}