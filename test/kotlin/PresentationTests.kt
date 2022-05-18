package com.github.derekbum.composeslidespresenter

import org.junit.Test
import kotlin.test.assertEquals

class PresentationTests {

    @Test fun `parse text as presentation`() {

        val presentationTest = Presentation()

        presentationTest.parseAsPresentation(emptyList())
        assertEquals(Presentation(slides = emptyArray()), presentationTest)

        presentationTest.slides = emptyArray()
        presentationTest.parseAsPresentation(listOf("slide1.png"))
        assertEquals(Presentation(slides = arrayOf("slide1.png")), presentationTest)

        presentationTest.slides = emptyArray()
        presentationTest.parseAsPresentation(listOf("slide1.png", "", "  ", "-- comment", "# comment", "slide2.png"))
        assertEquals(Presentation(slides = arrayOf("slide1.png", "slide2.png")), presentationTest)

        presentationTest.slides = emptyArray()
        presentationTest.parseAsPresentation(listOf("slide{{next 3}}.png", "some-slide.png", "slide{{next 3}}.png"))
        presentationTest.expandSlidesWithTemplate()
        assertEquals(
            Presentation(slides = arrayOf(
                "slide001.png", "slide002.png", "slide003.png",
                "some-slide.png",
                "slide004.png", "slide005.png", "slide006.png"
            )), presentationTest
        )
    }

    @Test fun `move to next, previous slide`() {
        val presentationTest = Presentation(slides = arrayOf("slide1", "slide2", "slide3"))

        assertEquals("slide1", presentationTest.slides[presentationTest.index])

        assertEquals(-1, presentationTest.moveSlide(-1))
        assertEquals("slide1", presentationTest.slides[presentationTest.index])
        assertEquals(1, presentationTest.moveSlide(1))
        assertEquals("slide2", presentationTest.slides[presentationTest.index])
        assertEquals(1, presentationTest.moveSlide(1))
        assertEquals("slide3", presentationTest.slides[presentationTest.index])
        assertEquals(-1, presentationTest.moveSlide(1))
        assertEquals("slide3", presentationTest.slides[presentationTest.index])
        assertEquals(1, presentationTest.moveSlide(-1))
        assertEquals("slide2", presentationTest.slides[presentationTest.index])
        assertEquals(1, presentationTest.moveSlide(-1))
        assertEquals("slide1", presentationTest.slides[presentationTest.index])
        assertEquals(-1, presentationTest.moveSlide(-1))
        assertEquals("slide1", presentationTest.slides[presentationTest.index])
    }


}