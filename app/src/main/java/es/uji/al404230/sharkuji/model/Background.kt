package es.uji.al404230.sharkuji.model

class Background (x : Float, y : Float) {

    private var backgound4 = Coordinates(x,y,-5f)
    private var backgound3 = Coordinates(x,y,-10f)
    private var backgound2 = Coordinates(x,y,-15f)
    private var backgound1 = Coordinates(x,y,-20f)

    val backgounds = listOf(backgound4, backgound3, backgound2, backgound1)

    fun updateCoordenades() {
        for (background in backgounds)
            background.updateCoordinatesX()//x += background.speed
    }

    fun getDirection(): Int {
        if (backgound4.speed > 0)
            return 1
        return -1
    }


}