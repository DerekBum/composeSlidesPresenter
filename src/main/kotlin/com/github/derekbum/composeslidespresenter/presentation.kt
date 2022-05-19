package com.github.derekbum.composeslidespresenter

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import java.io.File

data class Presentation(
    var slides: Array<String> = emptyArray(),
    var index: Int = 0,
    var type: String = "",
    var windowIndex: Int = 0,
    var frameIndex: Int = 0,
) {

    private fun readSlides(file: File) {
        val allStrings = file.readLines()
        parseAsPresentation(allStrings)
    }

    fun parseAsPresentation(allStrings: List<String>) {
        slides = allStrings.map { it.trim() }
            .filterNot { it.isEmpty() || it.startsWith("--") || it.startsWith("#") }.toTypedArray()
    }

    fun expandSlidesWithTemplate() {
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
        readSlides(slidesFile)
        expandSlidesWithTemplate()
    }

    fun moveSlide(direction: Int): Int {

        index += direction

        if (index >= slides.size || index < 0) {
            index += direction * (-1)
            return -1
        }

        type = if (slides[index].takeLast(3) == "jpg" || slides[index].takeLast(3) == "png")
            "Image"
        else "Editor"

        return 1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Presentation

        if (!slides.contentEquals(other.slides)) return false
        if (index != other.index) return false
        if (type != other.type) return false
        if (windowIndex != other.windowIndex) return false
        if (frameIndex != other.frameIndex) return false

        return true
    }

    override fun hashCode(): Int {
        var result = slides.contentHashCode()
        result = 31 * result + index
        result = 31 * result + type.hashCode()
        result = 31 * result + windowIndex
        result = 31 * result + frameIndex
        return result
    }
}