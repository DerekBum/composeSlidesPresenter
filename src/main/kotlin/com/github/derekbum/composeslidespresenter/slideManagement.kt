package com.github.derekbum.composeslidespresenter

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys

val presentation = Presentation()

class LoadAllSlides: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {

        val vf = event.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY)
        for (i in 0 until presentation.slides.size) {
            val file = vf?.findFileByRelativePath(presentation.slides[i])
            presentation.slides[i] = file.toString().removePrefix("file://")
        }

        presentation.index = 0
        if (presentation.slides[0].takeLast(3) == "jpg")
            presentation.type = "Image"
        else presentation.type = "Editor"

        ShowSlide().actionPerformed(event)
    }
}

class NextSlideAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        presentation.index++

        if (presentation.index >= presentation.slides.size) {
            presentation.index--
            return
        }

        if (presentation.slides[presentation.index].takeLast(3) == "jpg")
            presentation.type = "Image"
        else presentation.type = "Editor"

        ShowSlide().actionPerformed(event)
    }
}

class PreviousSlideAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {

        if (presentation.index < 0) {
            presentation.index++
            return
        }

        if (presentation.slides[presentation.index].takeLast(3) == "jpg")
            presentation.type = "Image"
        else presentation.type = "Editor"

        ShowSlide().actionPerformed(event)
    }
}