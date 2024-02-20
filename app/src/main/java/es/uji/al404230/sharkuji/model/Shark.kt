package es.uji.al404230.sharkuji.model

import android.util.Log
import es.uji.al404230.sharkuji.Direction

class Shark (private var screenWidth : Int, private var screenHeight : Int, private var sharkWidth : Int, private var sharkHeight: Int)  {
    var coins : Int = 0
    var coordinates : Coordinates
    lateinit var collisionCoordinatesStart : Coordinates
    lateinit var collisionCoordinatesFinish : Coordinates
    private var margin : Int
    var energy : Int =15 //de 0 a 15
    var direction : Direction

    init {
        coordinates = Coordinates(100f, 100f, 5f)
        direction = Direction.RIGHT
        margin = 20
    }

    fun updateCoordinates(){
        when (direction) {
            Direction.RIGHT -> {
                if(coordinates.speed < 0) coordinates.speed *= -1 //Se cambia la direccion si se requiere
                if( coordinates.x + sharkWidth < screenWidth - margin) coordinates.updateCoordinatesX() //se mueve si se puede
            }
            Direction.LEFT -> { //NO NECESITA MARGEN
                if(coordinates.speed > 0) coordinates.speed *= -1 //Se cambia la direccion si se requiere
                if(coordinates.x > 0 ) coordinates.updateCoordinatesX() //se mueve si se puede
            }
            Direction.UP -> {
                if(coordinates.speed > 0) coordinates.speed *= -1 //Se cambia la direccion si se requiere
                if(coordinates.y > 0 + margin )coordinates.updateCoordinatesY() //se mueve si se puede
            }
            Direction.DOWN -> {
                if(coordinates.speed < 0) coordinates.speed *= -1 //Se cambia la direccion si se requiere
                if( coordinates.y + sharkHeight < screenHeight - margin)coordinates.updateCoordinatesY() //se mueve si se puede
            }
        }

    }


    //funcion para chequear las colisiones
    fun checkCollision(thing : Coordinates) : Boolean{
        //crear un cuadrado donde es efectiva la colision
        when(direction){
            Direction.DOWN -> {
                collisionCoordinatesStart = Coordinates(coordinates.x, coordinates.y + (sharkHeight/3)*2, coordinates.speed)
                collisionCoordinatesFinish = Coordinates(coordinates.x+sharkWidth, coordinates.y+sharkHeight, coordinates.speed)
            }
            Direction.LEFT -> {
                collisionCoordinatesStart = Coordinates(coordinates.x, coordinates.y+(sharkHeight/3), coordinates.speed)
                collisionCoordinatesFinish = Coordinates(coordinates.x + (sharkWidth/3), coordinates.y+(sharkHeight/3)*2, coordinates.speed)
            }
            Direction.RIGHT -> {
                collisionCoordinatesStart = Coordinates( coordinates.x +(sharkWidth/3)*2, coordinates.y-(sharkHeight/3), coordinates.speed)
                collisionCoordinatesFinish = Coordinates(coordinates.x + sharkWidth, (coordinates.y+(sharkHeight/3)*2), coordinates.speed)
            }
            Direction.UP -> {
                collisionCoordinatesStart = Coordinates(coordinates.x, coordinates.y, coordinates.speed)
                collisionCoordinatesFinish = Coordinates(coordinates.x+sharkWidth, coordinates.y+(sharkHeight/3), coordinates.speed)

            }
        }

        //si esta dentro de la caja de colisiones, return true, sino, false
        return( (thing.x >= collisionCoordinatesStart.x && thing.x <= collisionCoordinatesFinish.x) &&
            (thing.y >= collisionCoordinatesStart.y && thing.y <= collisionCoordinatesFinish.y))

    }
}