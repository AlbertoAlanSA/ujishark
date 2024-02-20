package es.uji.al404230.sharkuji.model

import es.uji.al404230.sharkuji.Direction
import kotlin.random.Random

class Model (private var sharkWidth: Int, private var sharkHeight : Int, private val STAGE_WIDTH: Int, private val STAGE_HEIGHT: Int) {
    var parallax1 : Background = Background(0f,0f)
    var parallax2 : Background
    lateinit var shark : Shark
    internal var seaLife = SeaLifePool()
    private var activeP1 = arrayListOf<Boolean>()
    var coinsList =  ArrayList<Coordinates>()
    private val margin = 0f
    private var parallaxWidth : Float = -1f
    var submarine = Coordinates(-1f, -1f, 0f)
    var mine = Coordinates(-1f, -1f, 0f)

    init {
        parallax2 = if(parallax1.getDirection() == -1) Background(0f,0f)
        else Background(0f,0f)

        for(i in 0..3 ) activeP1.add(true)
    }

    fun setShark(){
        shark = Shark(STAGE_WIDTH, STAGE_HEIGHT, sharkWidth, sharkHeight )
    }

    fun setWidth(w: Int){
        parallaxWidth = w.toFloat()
    }

    fun setSubmarine(left : Boolean) {
        submarine = if(left) Coordinates(STAGE_WIDTH+20f, 50f, -10f)
        else Coordinates(-20f, 50f, 10f)
    }

    fun setMine(){
        mine = Coordinates(submarine.x,submarine.y+10f, 10f )
    }

    fun correctParallaxPosition() {
        if (parallax1.getDirection() == -1) {
            for(i in 0..3 ) {
                if (activeP1[i] && (parallax2.backgounds[i].x < (parallaxWidth - STAGE_WIDTH) + margin)) {
                    parallax1.backgounds[i].x = STAGE_WIDTH + margin
                    activeP1[i] = false
                } else if (!activeP1[i] && (parallax1.backgounds[i].x < (parallaxWidth - STAGE_WIDTH) + margin)) {
                    parallax2.backgounds[i].x = STAGE_WIDTH + margin
                    activeP1[i] = true
                }
            }
        } else {
            for (i in 0..3) {
                if (activeP1[i] && (parallax2.backgounds[i].x >= 0 - margin)) {
                    parallax1.backgounds[i].x = -parallaxWidth - margin
                    activeP1[i] = false
                } else if (!activeP1[i] && (parallax1.backgounds[i].x >= 0 - margin)) {
                    parallax2.backgounds[i].x = -parallaxWidth - margin
                    activeP1[i] = true
                }
            }
        }
        correctSpeed()
    }

    private fun correctSpeed() {
        if((shark.direction == Direction.LEFT && parallax1.getDirection() == -1)||
            (shark.direction == Direction.RIGHT && parallax1.getDirection() == 1)) {
            for (background in parallax1.backgounds)
                background.speed *= -1
            for(background in parallax2.backgounds)
                background.speed *= -1
            for(i in 0..3) activeP1[i]=!activeP1[i]
        }
    }

    private fun randomizeCoinPosition (): Coordinates {
        val width = 20
        val height = 18
        val margin = 25
        val x = Random.nextInt(margin, STAGE_WIDTH - width - margin).toFloat()
        val y = Random.nextInt(margin, STAGE_HEIGHT - height - margin).toFloat()
        return Coordinates(x, y, 0f)
    }

    fun createFish () {
        when (Random.nextInt(0, 3)) {
            0 -> seaLife.addFish("normal", STAGE_WIDTH, STAGE_HEIGHT)
            1 -> seaLife.addFish("dart", STAGE_WIDTH, STAGE_HEIGHT)
            2 -> seaLife.addFish("big", STAGE_WIDTH, STAGE_HEIGHT)
        }
       //TO DO("Identificar dónde gestionar los objetos y añadirlos a ese gestor." + "MOVER EL RANDOMIZADOR A SEALIFE (PASAR EL TIPO Y SACAR AHÍ LOS TAMAÑOS)")
    }

    fun seaLifeInside() {
        seaLife.checkBoundaries(STAGE_WIDTH, STAGE_HEIGHT)
    }

    fun createCoins() {
        coinsList.add(randomizeCoinPosition())
    }
    fun submarineCollisions() : Boolean{
        return (submarine.x <-100) || (submarine.x > STAGE_WIDTH+100)
    }
    fun mineCollisions() : Boolean{
        return mine.y > STAGE_HEIGHT+100
    }
    fun killFish(fish : SeaLife){
        if(shark.energy+ seaLife.eatFish(fish)<=15f)shark.energy+= seaLife.eatFish(fish)
        else shark.energy=15
    }

    fun getCoin(coin : Coordinates){
        shark.coins+=1
        coinsList.remove(coin)
    }
}