package com.github.derekbum.composeslidespresenter

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.util.containers.toArray
import java.io.File
import com.intellij.openapi.vfs.*

class Presentation {
    var slides: Array<String> = emptyArray()
    var index = 0
    var type: String = ""
    var windowIndex = 0
    var frameIndex = 0

    private fun readSlides(file: File) {
        val allStrings = file.readLines()
        slides = allStrings.map { it.trim() }
            .filterNot { it.isEmpty() || it.startsWith("--") || it.startsWith("#") }.toTypedArray()
    }

    private fun expandSlidesWithTemplate() {
        var counter = 1
        fun String.expandIntoMultipleSlides(): List<String> {
            val i1 = indexOf("{{next ")
            val i2 = i1 + "{{next ".length
            val i3 = indexOf("}}")
            val i4 = i3 + "}}".length
            val amount = substring(i2, i3).toInt()

            val from = counter
            val to = counter + amount
            counter = to

            return from.until(to).map {
                val index = String.format("%03d", it)
                replaceRange(i1, i4, index)
            }
        }

        slides = slides.flatMap {
            if (it.contains("{{next ")) it.expandIntoMultipleSlides() else listOf(it)
        }.toTypedArray()
    }

    fun presentationReader(event: AnActionEvent) {
        presentationOpened = true

        val vf = event.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY)
        val path = vf?.findFileByRelativePath("/slides.txt")
        val slidesFile = File(path.toString().removePrefix("file://"))
        presentation.readSlides(slidesFile)
        presentation.expandSlidesWithTemplate()
    }
}