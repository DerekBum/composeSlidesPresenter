package com.github.derekbum.composeslidespresenter

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PresentationTest {

    @Test
    fun parseAsPresentation() {
        val presentationTest = Presentation()

        presentationTest.parseAsPresentation(emptyList())
        kotlin.test.assertEquals(Presentation(slides = emptyArray()), presentationTest)

        presentationTest.slides = emptyArray()
        presentationTest.parseAsPresentation(listOf("slide1.png"))
        kotlin.test.assertEquals(Presentation(slides = arrayOf("slide1.png")), presentationTest)

        presentationTest.slides = emptyArray()
        presentationTest.parseAsPresentation(listOf("slide1.png", "", "  ", "-- comment", "# comment", "slide2.png"))
        kotlin.test.assertEquals(Presentation(slides = arrayOf("slide1.png", "slide2.png")), presentationTest)

        presentationTest.slides = emptyArray()
        presentationTest.parseAsPresentation(listOf("slide{{next 3}}.png", "some-slide.png", "slide{{next 3}}.png"))
        presentationTest.expandSlidesWithTemplate()
        kotlin.test.assertEquals(
            Presentation(
                slides = arrayOf(
                    "slide001.png", "slide002.png", "slide003.png",
                    "some-slide.png",
                    "slide004.png", "slide005.png", "slide006.png"
                )
            ), presentationTest
        )
    }

    @Test
    fun moveSlide() {
        val presentationTest = Presentation(slides = arrayOf("slide1", "slide2", "slide3"))

        kotlin.test.assertEquals("slide1", presentationTest.slides[presentationTest.index])

        kotlin.test.assertEquals(-1, presentationTest.moveSlide(-1))
        kotlin.test.assertEquals("slide1", presentationTest.slides[presentationTest.index])
        kotlin.test.assertEquals(1, presentationTest.moveSlide(1))
        kotlin.test.assertEquals("slide2", presentationTest.slides[presentationTest.index])
        kotlin.test.assertEquals(1, presentationTest.moveSlide(1))
        kotlin.test.assertEquals("slide3", presentationTest.slides[presentationTest.index])
        kotlin.test.assertEquals(-1, presentationTest.moveSlide(1))
        kotlin.test.assertEquals("slide3", presentationTest.slides[presentationTest.index])
        kotlin.test.assertEquals(1, presentationTest.moveSlide(-1))
        kotlin.test.assertEquals("slide2", presentationTest.slides[presentationTest.index])
        kotlin.test.assertEquals(1, presentationTest.moveSlide(-1))
        kotlin.test.assertEquals("slide1", presentationTest.slides[presentationTest.index])
        kotlin.test.assertEquals(-1, presentationTest.moveSlide(-1))
        kotlin.test.assertEquals("slide1", presentationTest.slides[presentationTest.index])
    }
}