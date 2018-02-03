package ch.bildspur.visualpush.visual.types

enum class BlendMode(val value : Int) {
    BLEND(1),
    ADD(2),
    SUBTRACT(4),
    LIGHTEST(8),
    DARKEST(16),
    DIFFERENCE(32),
    EXCLUSION(64),
    MULTIPLY(128),
    SCREEN(256),
    OVERLAY(512),
    HARD_LIGHT(1024),
    SOFT_LIGHT(2048),
    DODGE(4096),
    BURN(8192);
}