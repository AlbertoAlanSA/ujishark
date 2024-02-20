package es.uji.al404230.sharkuji.controller

import es.uji.al404230.sharkuji.Direction
import es.uji.al404230.sharkuji.GestureDetector
import es.uji.al404230.sharkuji.model.Coordinates
import es.uji.al404230.sharkuji.model.Model
import es.uji.al404230.sharkuji.view.MainActivity
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class Controller (private val model: Model, private val view: MainActivity): IGameController {
    private var gestureDetector: GestureDetector
    private var more = false
    private var numberEnergyAndSpeed = 1
    private var timerSpeed = numberEnergyAndSpeed
    private var timerEnergy = numberEnergyAndSpeed
    private var game = -1
    private var numberFish = 30
    private var timerFish = 0
    private var numberSubmarine = 20
    private var timerSubmarine =numberSubmarine
    private var submarineleft = true
    private var numberCoins = 40
    private var timerCoins = numberCoins
    init{
        gestureDetector = GestureDetector()
    }

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {

        view.dontShowHUD(game)
        if(game==1) {
            if(model.shark.energy<=0){ // cuando el tiburón muere
                view.setSharkAlive(false)
                model.shark.coordinates.speed=0f
                view.displayDeath(Coordinates(  (model.shark.collisionCoordinatesFinish.x -model.shark.collisionCoordinatesStart.x)/2+model.shark.coordinates.x,
                                                (model.shark.collisionCoordinatesFinish.y -model.shark.collisionCoordinatesStart.y)/2+model.shark.coordinates.y, 0f))
            }
            more = false //variable para la velocidad
            if (touchEvents != null) for (event in touchEvents) {
                val correctedX = view.normalizeX(event.x)
                val correctedY = view.normalizeY(event.y)


                @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
                when (event.type) {
                    TouchHandler.TouchType.TOUCH_DOWN -> gestureDetector.onTouchDown(
                        correctedX,
                        correctedY
                    )
                    TouchHandler.TouchType.TOUCH_UP -> when (gestureDetector.onTouchUp(
                        correctedX,
                        correctedY
                    )) {
                        GestureDetector.Gestures.SWIPE -> model.shark.direction =
                            gestureDetector.direction
                        GestureDetector.Gestures.CLICK -> modifyMoreSpeedShark()
                        GestureDetector.Gestures.NONE -> {}
                    }
                    TouchHandler.TouchType.TOUCH_DRAGGED -> {}
                }
            }

            //coordenadas
            model.parallax1.updateCoordenades()
            model.parallax2.updateCoordenades()
            model.shark.updateCoordinates()
            model.seaLife.updateCoordinates()
            model.submarine.updateCoordinatesX()
            model.mine.updateCoordinatesY()

            //colisiones
            model.seaLifeInside()
            if(model.submarineCollisions()) view.displaySubmarine(Coordinates(-1f,-1f,0f))
            if(model.mineCollisions()) view.displayMine(Coordinates(-1f,-1f,0f))
            var i =0
            //pescaos
            while(i< model.seaLife.list.size){
                if(model.shark.checkCollision(model.seaLife.list[i].coordinates)){
                    view.displayDeath(model.seaLife.list[i].coordinates)
                    model.killFish(model.seaLife.list[i])
                }else i++
            }
            //monedas
            while(i < model.coinsList.size){
                if(model.shark.checkCollision(model.coinsList[i])){
                    model.getCoin(model.coinsList[i])
                }else i++
            }
            //minas
            if(model.shark.checkCollision(model.mine)){
                view.displayExplosion(model.mine)
                if(view.assets.fishSpriteList[10].isEnded) {
                    view.displayMine(Coordinates(-1f, -1f, 0f))
                    view.assets.fishSpriteList[10].restart()
                    model.shark.energy -= 4
                }
            }


            //temporizadores
            if (timerEnergy < 0 && model.shark.energy > 0){
                model.shark.energy -= 1
                timerEnergy=numberEnergyAndSpeed
            }else timerEnergy--

            if (!more && timerSpeed < 0) {
                timerSpeed = numberEnergyAndSpeed
                modifyLessSpeedShark()
            } else timerSpeed--

            if(timerFish<0) {
                model.createFish()
                timerFish = numberFish
            }else timerFish--

            if(timerCoins<0){
                model.createCoins()
                timerCoins=numberCoins
            }else timerCoins--

            if(timerSubmarine<0  ){
                //meter probabilidad si se quiere
                if(view.submarineCoordinates.x==-1f && view.submarineCoordinates.y == -1f) { //NO esta el submarino -> creamos uno nuevo
                    model.setSubmarine(submarineleft)
                    submarineleft = !submarineleft
                    view.displaySubmarine(model.submarine)
                }else{ // esta el submarino -> creamos minas
                    if(view.mineCoordinates.x == -1f && view.mineCoordinates.y == -1f) { //NO hay mina -> la creamos
                        model.setMine()
                        view.displayMine(model.mine)
                    }
                }
                timerSubmarine=numberSubmarine
            }else timerSubmarine--

            //UPDATE SPRITES
            //tiburon
            when(model.shark.direction){
                Direction.DOWN -> view.assets.sharkDownAnimated.update(deltaTime * 2.5f)
                Direction.LEFT -> view.assets.sharkLeftAnimated.update(deltaTime * 2.5f)
                Direction.RIGHT -> view.assets.sharkRightAnimated.update(deltaTime * 2.5f)
                Direction.UP -> view.assets.sharkUpAnimated.update(deltaTime * 2.5f)
            }

            //peces
            for(fish in model.seaLife.list) {
                when(fish.kind){
                    "normal" ->{    if(fish.coordinates.direction == Direction.LEFT) view.assets.fishSpriteList[0].update(deltaTime * 2.5f)
                    else view.assets.fishSpriteList[1].update(deltaTime * 2.5f)}
                    "dart"   ->{    if(fish.coordinates.direction == Direction.LEFT) view.assets.fishSpriteList[2].update(deltaTime * 2.5f)
                    else view.assets.fishSpriteList[3].update(deltaTime * 2.5f)}
                    "big"    ->{    if(fish.coordinates.direction == Direction.LEFT) view.assets.fishSpriteList[4].update(deltaTime * 2.5f)
                    else view.assets.fishSpriteList[5].update(deltaTime * 2.5f)}
                }
            }
            //monedas
            view.assets.coinAnimated.update(deltaTime * 2.5f)

            //coordenadas especiales ( death, submarine, explosion)
            if (view.deathCoordinates != Coordinates(-1f, -1f, 0f)) {
                view.assets.fishSpriteList[6].update(deltaTime * 2.5f)
                if(view.assets.fishSpriteList[6].isEnded){
                    view.assets.fishSpriteList[6].restart()
                    view.displayDeath(Coordinates(-1f, -1f, 0f))
                    if(model.shark.energy<=0)game = 0
                }
            }
            if (view.submarineCoordinates != Coordinates(-1f, -1f, 0f)) {
                if(view.submarineCoordinates.speed>0) view.assets.fishSpriteList[7].update(deltaTime * 2.5f)
                else view.assets.fishSpriteList[8].update(deltaTime * 2.5f)
            }
            if (view.explosionCoordinates != Coordinates(-1f, -1f, 0f)) {
                view.assets.fishSpriteList[10].update(deltaTime * 2.5f)
            }

        }else{ //estamos en la pantalla de inicio o de restart -> solo se puede tocar la pantalla

            if (touchEvents != null) for (event in touchEvents) {
                val correctedX = view.normalizeX(event.x)
                val correctedY = view.normalizeY(event.y)


                @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
                when (event.type) {
                    TouchHandler.TouchType.TOUCH_DOWN -> gestureDetector.onTouchDown(
                        correctedX,
                        correctedY
                    )
                    TouchHandler.TouchType.TOUCH_UP -> when (gestureDetector.onTouchUp(
                        correctedX,
                        correctedY
                    )) {
                        GestureDetector.Gestures.SWIPE -> {}

                        GestureDetector.Gestures.CLICK -> start()
                        GestureDetector.Gestures.NONE -> {}
                    }
                    TouchHandler.TouchType.TOUCH_DRAGGED -> {}
                }
            }
        }
    }

    private fun modifyMoreSpeedShark(){
        if(kotlin.math.abs(model.shark.coordinates.speed) < 20f){
            //model.shark.energy +=1
            model.shark.coordinates.speed *= 1.2f
        }
        timerSpeed = numberEnergyAndSpeed
        more=true
    }
    private fun modifyLessSpeedShark(){
        if(kotlin.math.abs(model.shark.coordinates.speed) > 1.5f){

            model.shark.coordinates.speed /= 1.2f
        }
    }
    private fun start(){
        more = false
        numberEnergyAndSpeed = 60
        timerSpeed = numberEnergyAndSpeed
        timerEnergy = numberEnergyAndSpeed
        model.seaLife.clearSeaLife()
        view.setSharkAlive(true)
        model.setShark()
        game = 1
    }

}