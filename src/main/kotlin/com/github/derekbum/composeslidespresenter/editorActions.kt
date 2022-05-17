package com.github.derekbum.composeslidespresenter

import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

//class FontIncAction : ActionListener {
fun fontIncAction() {
    val font: Font = textArea.font
    val size: Float = font.size + 1.0f
    textArea.font = font.deriveFont(size)
}
//}

//class FontDecAction : ActionListener {
fun fontDecAction() {
    val font: Font = textArea.font
    val size: Float = font.size - 1.0f
    textArea.font = font.deriveFont(size)
}
//}

//class SaveEditorAction : ActionListener {
fun saveEditorAction() {
    textArea.save()
}
//}