package ch.bildspur.visualpush.ui.util

import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.paint.Color

fun TreeView<TagItem>.items(current: TreeItem<TagItem> = this.root,
                            items: MutableList<TreeItem<TagItem>> = mutableListOf<TreeItem<TagItem>>())
        : MutableList<TreeItem<TagItem>> {
    items.add(current)

    current.children.forEach {
        this.items(it, items)
    }

    return items
}

fun Color.toRGBCode(): String {
    return String.format("#%02X%02X%02X",
            (this.red * 255).toInt(),
            (this.green * 255).toInt(),
            (this.blue * 255).toInt())
}