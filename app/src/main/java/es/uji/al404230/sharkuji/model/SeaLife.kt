package es.uji.al404230.sharkuji.model

import es.uji.al404230.sharkuji.Direction
import kotlin.random.Random

const val MARGIN = 25

class SeaLifePool {

    internal var list = ArrayList<SeaLife>()

    fun addFish(kind: String, STAGE_WIDTH: Int, STAGE_HEIGHT: Int) {
        list.add(SeaLife(kind, STAGE_WIDTH, STAGE_HEIGHT))
    }

    fun updateCoordinates() {
        for (fish in list) {
            fish.coordinates.updateCoordinates()
        }
    }

    private fun deleteFish(fish: SeaLife) {
        list.remove(fish)

    }

    internal fun eatFish(fish: SeaLife): Int {
        val energy = fish.energyLevel
        deleteFish(fish)
        return energy
    }

    fun checkBoundaries(STAGE_WIDTH: Int, STAGE_HEIGHT: Int) {
        var i =0
        while(i<list.size) {
            if (!(-50 <= list[i].coordinates.x && list[i].coordinates.x <= STAGE_WIDTH) ||
                !(-50 <= list[i].coordinates.y && list[i].coordinates.y <= STAGE_HEIGHT))
                deleteFish(list[i])
            else i++
        }
    }

    fun clearSeaLife() {
        list = ArrayList()
        // MAYBE MORE EFFICIENT
        /*for (fish in list) {
            deleteFish(fish)
        }*/
    }
}

class SeaLife (val kind: String, private val STAGE_WIDTH: Int, private val STAGE_HEIGHT: Int) {

    lateinit var coordinates: FishCoordinates
    var energyLevel: Int

    init {
        when (kind) {
            "normal" -> {
                coordinates= randomizePosition(32, 32, 2f, MARGIN)
            }
            "dart" -> {
                coordinates= randomizePosition(39, 20, 5f, MARGIN)
            }
            "big" -> {
                coordinates= randomizePosition(54, 49, 10f, MARGIN)
            }
        }
        energyLevel = (coordinates.speed/2).toInt()
    }

    private fun randomizePosition (width: Int, height: Int, speed: Float, margin: Int /*, maximumPosition: Int = 100, minimumPosition: Int = -100*/): FishCoordinates {
        var direction = Direction.DOWN

        //COMENTADO PORQUE EL RANDOM PETA:
        //EN (Random.nextInt(x,y)) EL PARAMETRO X SIEMPRE TIENE QUE SER MENOR QUE EL PARÁMETRO Y, EN EL CÓDIGO COMENTADO ESO NO SE CUMPLE
        //REVISAR

        /*
        var xFrom = margin
        var xTo   = STAGE_WIDTH - width - margin
        var yFrom = margin
        var yTo   = STAGE_HEIGHT - height - margin

        when (Random.nextInt(1, 4)) {
            1 -> { // top
                yFrom = minimumPosition - margin
                yTo   = - height
            }
            2 -> { // right
                direction = Direction.LEFT
                xFrom = STAGE_WIDTH
                xTo = maximumPosition + margin
            }
            3 -> { // bottom
                direction = Direction.UP
                xFrom = minimumPosition - margin
                xTo = - width
            }
            4 -> { // left
                direction = Direction.RIGHT
                yFrom = STAGE_HEIGHT
                yTo   = maximumPosition + margin
            }
        }*/

        val xFrom = margin
        val xTo   = STAGE_WIDTH - width - margin
        val yFrom = margin
        val yTo   = STAGE_HEIGHT - height - margin

        when (Random.nextInt(1, 4)) {
            1 -> { // top
                direction = Direction.DOWN
            }
            2 -> { // right
                direction = Direction.LEFT
            }
            3 -> { // bottom
                direction = Direction.UP
            }
            4 -> { // left
                direction = Direction.RIGHT
            }
        }

        val x = (Random.nextInt( xFrom, xTo)).toFloat()
        val y = (Random.nextInt( yFrom, yTo)).toFloat()

        return FishCoordinates(x, y, speed, direction)
    }
}