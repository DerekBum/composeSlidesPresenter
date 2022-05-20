package com.github.derekbum.composeslidespresenter

import java.awt.Font

fun fontIncAction() {
    val font: Font = textArea.font
    val size: Float = font.size + 1.0f
    textArea.font = font.deriveFont(size)
}

fun fontResetAction() {
    textArea.font = textArea.font.deriveFont(baseFontSize)
}

fun fontDecAction() {
    val font: Font = textArea.font
    val size: Float = font.size - 1.0f
    textArea.font = font.deriveFont(size)
}

fun saveEditorAction() {
    textArea.save()
}