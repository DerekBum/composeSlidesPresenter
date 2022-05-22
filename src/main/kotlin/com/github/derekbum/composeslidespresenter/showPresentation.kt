package com.github.derekbum.composeslidespresenter

import androidx.compose.ui.awt.ComposePanel
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import java.awt.*
import java.io.File
import javax.swing.*

var frame = JFrame()

var file: File = File("")

private var prevType = ""

class ShowSlide: DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {

        file = File(presentation.slides[presentation.index])

        if (presentation.type == "Editor") {

            frame.contentPane.removeAll()
            frame.repaint()

            prevType = "Editor"
            SwingUtilities.invokeLater { TextEditorSwing() }
        } else {

            presentationSlidePath.value = File(presentation.slides[presentation.index])

            if (prevType == "Editor" || presentation.index == 0) {
                frame.contentPane.removeAll()
                frame.repaint()

                prevType = "Image"

                val composePanel = ComposePanel().apply {
                    setContent {
                        preferredSize = Dimension(Toolkit.getDefaultToolkit().screenSize)
                        App()
                    }
                }

                frame.contentPane.add(composePanel, BorderLayout.CENTER)
                frame.pack()
            }
        }
    }
}