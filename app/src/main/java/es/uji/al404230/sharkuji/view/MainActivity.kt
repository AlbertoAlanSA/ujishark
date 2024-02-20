package es.uji.al404230.sharkuji.view

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import es.uji.al404230.sharkuji.Direction
import es.uji.al404230.sharkuji.R
import es.uji.al404230.sharkuji.assets.Assets
import es.uji.al404230.sharkuji.controller.Controller
import es.uji.al404230.sharkuji.model.Coordinates
import es.uji.al404230.sharkuji.model.Model
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController

@Suppress("DEPRECATION")
class MainActivity : GameActivity() {

    //CONSTANTES
    companion object {
        const val STAGE_WIDTH = 480
        const val STAGE_HEIGHT = 320
    }
    // VARIABLES LATEINIT
    private lateinit var graphics : Graphics

    // VARIABLES INICIALIZADAS
    var assets: Assets = Assets
    private var sharkWidth = assets.SHARK_SHEET_WIDTH * assets.RATIO
    private var sharkHeight = assets.SHARK_SHEET_HEIGHT * assets.RATIO
    private var model = Model(sharkWidth,sharkHeight, Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels )

    private var width = 0
    private var height = 0

    private var sharkAlive = true

    private var scaleX : Float = 0f
    private var scaleY : Float = 0f

    var deathCoordinates = Coordinates(-1f, -1f, 0f)
    var explosionCoordinates = Coordinates(-1f, -1f, 0f)
    var submarineCoordinates = Coordinates(-1f, -1f, 0f)
    var mineCoordinates = Coordinates(-1f, -1f, 0f)
    private var game =-1

    // FUNCIONES PRINCIPALES
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        assets.createResizableAssets(this)

        landscapeFullScreenOnCreate()
    }

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        this.width = width
        this.height = height

        scaleX = STAGE_WIDTH.toFloat()/this.width
        scaleY = STAGE_HEIGHT.toFloat()/this.height

        model.setWidth(width)
        graphics = Graphics(width,height)
    }

    override fun onDrawingRequested() : Bitmap {
        for (i in 0..3) {
            graphics.drawBitmap(
                assets.parallax[i],
                model.parallax1.backgounds[i].x,
                model.parallax1.backgounds[i].y
            )
            graphics.drawBitmap(
                assets.parallax[i],
                model.parallax2.backgounds[i].x,
                model.parallax2.backgounds[i].y
            )
        }
        if(game ==1) { //game =-1 primera vez, game =0 pantalla end, game = 1 juego activo
            model.correctParallaxPosition()

            //tiburón
            if(sharkAlive) {
                when (model.shark.direction) {
                    Direction.DOWN -> graphics.drawBitmap(
                        assets.sharkDownAnimated.currentFrame,
                        model.shark.coordinates.x,
                        model.shark.coordinates.y
                    )
                    Direction.LEFT -> graphics.drawBitmap(
                        assets.sharkLeftAnimated.currentFrame,
                        model.shark.coordinates.x,
                        model.shark.coordinates.y
                    )
                    Direction.RIGHT -> graphics.drawBitmap(
                        assets.sharkRightAnimated.currentFrame,
                        model.shark.coordinates.x,
                        model.shark.coordinates.y
                    )
                    Direction.UP -> graphics.drawBitmap(
                        assets.sharkUpAnimated.currentFrame,
                        model.shark.coordinates.x,
                        model.shark.coordinates.y
                    )
                }
            }

            //coordenadas de sprites especiales (muerte, submarino, minas, explosion
            if(deathCoordinates.x != -1f && deathCoordinates.y != -1f) graphics.drawBitmap(assets.fishSpriteList[6].currentFrame, deathCoordinates.x, deathCoordinates.y)

            if(submarineCoordinates.x != -1f && submarineCoordinates.y != -1f){
                if(submarineCoordinates.speed>0) graphics.drawBitmap(assets.fishSpriteList[7].currentFrame, submarineCoordinates.x, submarineCoordinates.y)
                else  graphics.drawBitmap(assets.fishSpriteList[8].currentFrame, submarineCoordinates.x, submarineCoordinates.y)
            }

            if(mineCoordinates.x != -1f && mineCoordinates.y != -1f) graphics.drawBitmap(assets.fishSpriteList[9].currentFrame, mineCoordinates.x, mineCoordinates.y)

            if(explosionCoordinates.x != -1f && explosionCoordinates.y != -1f) graphics.drawBitmap(assets.fishSpriteList[10].currentFrame, explosionCoordinates.x, explosionCoordinates.y)

            //pescaos
            for(fish in model.seaLife.list) {
                when(fish.kind){
                    "normal" ->{    if(fish.coordinates.direction == Direction.LEFT) graphics.drawBitmap(assets.fishSpriteList[0].currentFrame, fish.coordinates.x, fish.coordinates.y)
                    else graphics.drawBitmap(assets.fishSpriteList[1].currentFrame, fish.coordinates.x, fish.coordinates.y)}
                    "dart"   ->{    if(fish.coordinates.direction == Direction.LEFT) graphics.drawBitmap(assets.fishSpriteList[2].currentFrame, fish.coordinates.x, fish.coordinates.y)
                    else graphics.drawBitmap(assets.fishSpriteList[3].currentFrame, fish.coordinates.x, fish.coordinates.y)}
                    "big"    ->{    if(fish.coordinates.direction == Direction.LEFT) graphics.drawBitmap(assets.fishSpriteList[4].currentFrame, fish.coordinates.x, fish.coordinates.y)
                    else graphics.drawBitmap(assets.fishSpriteList[5].currentFrame, fish.coordinates.x, fish.coordinates.y)}
                }
            }

            //monedas
            for(coin in model.coinsList)
                graphics.drawBitmap(assets.coinAnimated.currentFrame, coin.x, coin.y)

            //hud
            val x = width.toFloat() / 3
            val y = height.toFloat() / 1.11f
            graphics.drawBitmap(assets.energyIcon, x, y)

            when (model.shark.energy) {
                0 -> graphics.drawBitmap(assets.energyBar0, x + 100f, y)
                1 -> graphics.drawBitmap(assets.energyBar1, x + 100f, y)
                2 -> graphics.drawBitmap(assets.energyBar2, x + 100f, y)
                3 -> graphics.drawBitmap(assets.energyBar3, x + 100f, y)
                4 -> graphics.drawBitmap(assets.energyBar4, x + 100f, y)
                5 -> graphics.drawBitmap(assets.energyBar5, x + 100f, y)
                6 -> graphics.drawBitmap(assets.energyBar6, x + 100f, y)
                7 -> graphics.drawBitmap(assets.energyBar7, x + 100f, y)
                8 -> graphics.drawBitmap(assets.energyBar8, x + 100f, y)
                9 -> graphics.drawBitmap(assets.energyBar9, x + 100f, y)
                10 -> graphics.drawBitmap(assets.energyBar10, x + 100f, y)
                11 -> graphics.drawBitmap(assets.energyBar11, x + 100f, y)
                12 -> graphics.drawBitmap(assets.energyBar12, x + 100f, y)
                13 -> graphics.drawBitmap(assets.energyBar13, x + 100f, y)
                14 -> graphics.drawBitmap(assets.energyBar14, x + 100f, y)
                15 -> graphics.drawBitmap(assets.energyBar15, x + 100f, y)


            }
            graphics.drawBitmap(assets.coinAnimated.currentFrame, x + 300f, y + 28f)
            graphics.setTextSize(35)
            graphics.setTextColor(resources.getColor(R.color.orange))
            graphics.drawText(x + 350, y + 62f, model.shark.coins.toString())
            graphics.drawText(x + 400, y + 62f, model.shark.direction.toString())

        }else{

            val x = (width.toFloat() /2) -400
            val y = height.toFloat() / 2

            graphics.setTextSize(60)
            graphics.setTextColor(resources.getColor(R.color.orange))

            if(game == -1){ //primera vez
                graphics.drawText(x , y , "TOUCH THE SCREEN TO START")
            }else{//restart
                graphics.drawText(x+50 , y - 100f, "YOU'VE COLLECTED "+model.shark.coins+" COINS")
                graphics.drawText(x , y + 100f, "TOUCH THE SCREEN TO RESTART")
            }
        }
        return graphics.frameBuffer
    }

    override fun buildGameController(): IGameController = Controller(model, this)


    // FUNCIONES AUXILIARES Y OTROS
    fun normalizeX(x: Int): Float {
        return x * scaleX
    }
    fun normalizeY(y: Int): Float {
        return y * scaleY
    }
    fun displayDeath(coordinates: Coordinates) {
        deathCoordinates = coordinates
    }
    fun displaySubmarine(coordinates: Coordinates){
        submarineCoordinates = coordinates
    }
    fun displayExplosion(coordinates: Coordinates) {
        explosionCoordinates = coordinates
    }
    fun displayMine(coordinates: Coordinates){
        mineCoordinates=coordinates
    }
    fun setSharkAlive(boolean: Boolean){
        sharkAlive=boolean
    }
    fun dontShowHUD(game : Int){
        this.game = game
    }

}