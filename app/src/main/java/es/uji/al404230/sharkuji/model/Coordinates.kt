package es.uji.al404230.sharkuji.model

import es.uji.al404230.sharkuji.Direction

open class Coordinates (var x: Float, var y: Float, var speed: Float){
    open fun updateCoordinatesX() {
        x += speed
    }
    fun updateCoordinatesY() {
        y += speed
    }
    open fun updateCoordinates(){
        updateCoordinatesY()
        updateCoordinatesX()
    }
}

class FishCoordinates(x: Float, y: Float, speed: Float,  var direction: Direction) : Coordinates (x, y, speed) {
    override fun updateCoordinates() {
        when (direction) {
            Direction.DOWN ->   y += speed
            Direction.LEFT ->   x -= speed
            Direction.RIGHT ->  x += speed
            Direction.UP ->     y -= speed
        }
    }
}