package ch.bildspur.visualpush.controller

import ch.bildspur.visualpush.Sketch
import codeanticode.syphon.SyphonServer
import processing.core.PApplet
import processing.core.PImage

class SyphonController(private val sketch: PApplet)
{
    private lateinit var server: SyphonServer

    fun setup() {
        server = SyphonServer(sketch, Sketch.NAME)
        server.init()
    }

    fun sendScreen() {
        server.sendScreen()
    }

    fun sendImage(p: PImage) {
        if(server.hasClients())
            server.sendImage(p)
    }
}