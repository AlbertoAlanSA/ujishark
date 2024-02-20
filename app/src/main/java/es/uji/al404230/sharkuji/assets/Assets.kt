package es.uji.al404230.sharkuji.assets

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import es.uji.al404230.sharkuji.R
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.SpriteSheet
import kotlin.math.roundToInt

object Assets {

    private lateinit var parallaxBg1: Bitmap
    private lateinit var parallaxBg2: Bitmap
    private lateinit var parallaxBg3: Bitmap
    private lateinit var parallaxBg4: Bitmap
    lateinit var parallax: List<Bitmap>

    const val SHARK_SHEET_WIDTH = 98  //392
    const val SHARK_SHEET_HEIGHT = 94 //376
    const val RATIO = 4
    private lateinit var sharkSpriteSheet : SpriteSheet
    lateinit var sharkDownAnimated :AnimatedBitmap
    lateinit var sharkLeftAnimated :AnimatedBitmap
    lateinit var sharkUpAnimated :AnimatedBitmap
    lateinit var sharkRightAnimated :AnimatedBitmap

    //hud
    lateinit var energyIcon : Bitmap
    lateinit var energyBar0 : Bitmap
    lateinit var energyBar1 : Bitmap
    lateinit var energyBar2 : Bitmap
    lateinit var energyBar3 : Bitmap
    lateinit var energyBar4 : Bitmap
    lateinit var energyBar5 : Bitmap
    lateinit var energyBar6 : Bitmap
    lateinit var energyBar7 : Bitmap
    lateinit var energyBar8 : Bitmap
    lateinit var energyBar9 : Bitmap
    lateinit var energyBar10 : Bitmap
    lateinit var energyBar11 : Bitmap
    lateinit var energyBar12 : Bitmap
    lateinit var energyBar13 : Bitmap
    lateinit var energyBar14 : Bitmap
    lateinit var energyBar15 : Bitmap

    //pescaos
    private const val FISH_SHEET_WIDTH = 32
    private const val DART_FISH_SHEET_WIDTH = 39
    private const val DART_FISH_SHEET_HEIGHT = 20
    private const val BIG_FISH_SHEET_WIDTH = 54
    private const val BIG_FISH_SHEET_HEIGHT = 49
    private const val DEATH_SHEET_WIDTH = 52
    private const val DEATH_SHEET_HEIGHT = 53
    private const val EXPLOSION_SHEET_WIDTH = 60 // CREO QUE SON 11 FRAMES, SI SON 10 -> 66
    private const val EXPLOSION_SHEET_HEIGHT = 82
    private lateinit var fishSpriteSheetRight: SpriteSheet
    var fishSpriteList = ArrayList<AnimatedBitmap>()

    private const val SUBMARINE_WIDTH = 292
    private const val SUBMARINE_HEIGHT = 160
    private lateinit var submarineSprite: Bitmap


    //coins
    private const val COIN_SHEET_SIDE = 18
    private lateinit var coinSpriteSheet: SpriteSheet
    lateinit var coinAnimated : AnimatedBitmap

    private fun createShark (context: Context){
        val resources = context.resources

        sharkSpriteSheet = SpriteSheet ( Bitmap.createBitmap( BitmapFactory.decodeResource(resources, R.drawable.rochen)), SHARK_SHEET_HEIGHT, SHARK_SHEET_WIDTH)
        val frames = ArrayList<Bitmap>()
        frames.clear()
        frames.addAll(sharkSpriteSheet.getScaledRow(0, 4, SHARK_SHEET_WIDTH * RATIO, SHARK_SHEET_HEIGHT * RATIO))
        sharkDownAnimated = AnimatedBitmap(4.0f, true, *frames.toTypedArray())

        frames.clear()
        frames.addAll(sharkSpriteSheet.getScaledRow(1, 4, SHARK_SHEET_WIDTH * RATIO, SHARK_SHEET_HEIGHT * RATIO))
        sharkLeftAnimated = AnimatedBitmap(4.0f, true, *frames.toTypedArray())

        frames.clear()
        frames.addAll(sharkSpriteSheet.getScaledRow(2, 4, SHARK_SHEET_WIDTH * RATIO, SHARK_SHEET_HEIGHT * RATIO))
        sharkRightAnimated = AnimatedBitmap(4.0f, true, *frames.toTypedArray())

        frames.clear()
        frames.addAll(sharkSpriteSheet.getScaledRow(3, 4, SHARK_SHEET_WIDTH * RATIO, SHARK_SHEET_HEIGHT * RATIO))
        sharkUpAnimated = AnimatedBitmap(4.0f, true, *frames.toTypedArray())



    }
    private fun createFish(context: Context, correctedSharkWidth: Int) {
        val resources = context.resources
        val frames = ArrayList<Bitmap>()
        frames.clear()

        //FISH  -> 0, 1
        var spriteWidth = (0.3f * correctedSharkWidth).roundToInt()
        spriteWidth += spriteWidth % 2
        fishSpriteSheetRight = SpriteSheet(Bitmap.createBitmap( BitmapFactory.decodeResource(resources, R.drawable.fish)),
            FISH_SHEET_WIDTH, FISH_SHEET_WIDTH)
        frames.clear()
        frames.addAll(fishSpriteSheetRight.getScaledRow(0, 4, spriteWidth, spriteWidth))
        fishSpriteList.add(AnimatedBitmap(4.0f, true, * frames.toTypedArray()))

        frames.clear()
        frames.addAll(fishSpriteSheetRight.getScaledRow(0, 4, -spriteWidth, spriteWidth))
        fishSpriteList.add(AnimatedBitmap(4.0f, true, * frames.toTypedArray()))

        // DART FISH  -> 2, 3
        spriteWidth = (0.3f * correctedSharkWidth).roundToInt()
        var spriteHeight = ((0.3f *correctedSharkWidth* DART_FISH_SHEET_HEIGHT)/ DART_FISH_SHEET_WIDTH).roundToInt()
        spriteWidth += spriteWidth % 2
        spriteHeight+=spriteHeight%2
        fishSpriteSheetRight = SpriteSheet(Bitmap.createBitmap( BitmapFactory.decodeResource(resources, R.drawable.fish_dart)),
            DART_FISH_SHEET_HEIGHT, DART_FISH_SHEET_WIDTH)
        frames.clear()
        frames.addAll(fishSpriteSheetRight.getScaledRow(0, 4, spriteWidth, spriteHeight))
        fishSpriteList.add(AnimatedBitmap(3.0f, true, * frames.toTypedArray()))

        frames.clear()
        frames.addAll(fishSpriteSheetRight.getScaledRow(0, 4, -spriteWidth, spriteHeight))
        fishSpriteList.add(AnimatedBitmap(3.0f, true, * frames.toTypedArray()))

        // BIG FISH  -> 4, 5
        spriteWidth = (0.3f * correctedSharkWidth).roundToInt()
        spriteWidth += spriteWidth % 2
        fishSpriteSheetRight = SpriteSheet(Bitmap.createBitmap( BitmapFactory.decodeResource(resources, R.drawable.fish_big)),
            BIG_FISH_SHEET_HEIGHT, BIG_FISH_SHEET_WIDTH)
        frames.clear()
        frames.addAll(fishSpriteSheetRight.getScaledRow(0, 4, spriteWidth, spriteWidth))
        fishSpriteList.add(AnimatedBitmap(3.0f, true, * frames.toTypedArray()))

        frames.clear()
        frames.addAll(fishSpriteSheetRight.getScaledRow(0, 4, -spriteWidth, spriteWidth))
        fishSpriteList.add(AnimatedBitmap(3.0f, true, * frames.toTypedArray()))

        // DEATH ANIMATION  -> 6
        spriteWidth = (0.3f * correctedSharkWidth).roundToInt()
        spriteWidth += spriteWidth % 2
        fishSpriteSheetRight = SpriteSheet(Bitmap.createBitmap( BitmapFactory.decodeResource(resources, R.drawable.fish_death)),
            DEATH_SHEET_HEIGHT, DEATH_SHEET_WIDTH)
        frames.clear()
        frames.addAll(fishSpriteSheetRight.getScaledRow(0, 6, spriteWidth, spriteWidth))
        fishSpriteList.add(AnimatedBitmap(3.0f, false, * frames.toTypedArray()))

        //SUBMARINE  -> 7, 8
        spriteWidth = (0.5f * correctedSharkWidth).roundToInt()
        spriteWidth += spriteWidth % 2
        spriteHeight = ((0.5f *correctedSharkWidth* SUBMARINE_HEIGHT)/ SUBMARINE_WIDTH).roundToInt()
        spriteHeight+=spriteHeight%2
        frames.clear()
        submarineSprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.idle1_submarine), spriteWidth, spriteHeight, true)
        frames.add(submarineSprite)
        submarineSprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.idle2_submarine), spriteWidth, spriteHeight, true)
        frames.add(submarineSprite)
        submarineSprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.idle3_submarine), spriteWidth, spriteHeight, true)
        frames.add(submarineSprite)
        submarineSprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.idle4_submarine), spriteWidth, spriteHeight, true)
        frames.add(submarineSprite)
        fishSpriteList.add(AnimatedBitmap(3.0f, true, * frames.toTypedArray()))

        frames.clear()
        submarineSprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.idle1_submarine), -spriteWidth, spriteHeight, true)
        frames.add(submarineSprite)
        submarineSprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.idle2_submarine), -spriteWidth, spriteHeight, true)
        frames.add(submarineSprite)
        submarineSprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.idle3_submarine), -spriteWidth, spriteHeight, true)
        frames.add(submarineSprite)
        submarineSprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.idle4_submarine), -spriteWidth, spriteHeight, true)
        frames.add(submarineSprite)
        fishSpriteList.add(AnimatedBitmap(3.0f, true, * frames.toTypedArray()))

        // MINE  -> 9
        spriteWidth = (0.3f * correctedSharkWidth).roundToInt()
        spriteWidth += spriteWidth % 2

        submarineSprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.mine), -spriteWidth, spriteWidth, true)
        frames.clear()
        frames.add(submarineSprite)
        fishSpriteList.add(AnimatedBitmap(3.0f, true, * frames.toTypedArray()))

        // EXPLOSION ANIMATION  -> 10
        spriteWidth = (0.3f * correctedSharkWidth).roundToInt()
        spriteWidth += spriteWidth % 2

        spriteHeight = ((0.3f *correctedSharkWidth* EXPLOSION_SHEET_HEIGHT)/ EXPLOSION_SHEET_WIDTH).roundToInt()
        spriteHeight+=spriteHeight%2

        fishSpriteSheetRight = SpriteSheet(Bitmap.createBitmap( BitmapFactory.decodeResource(resources, R.drawable.explosion)),
            EXPLOSION_SHEET_HEIGHT, EXPLOSION_SHEET_WIDTH)
        frames.clear()
        frames.addAll(fishSpriteSheetRight.getScaledRow(0, 11, spriteWidth, spriteHeight))
        fishSpriteList.add(AnimatedBitmap(3.0f, false, * frames.toTypedArray()))

    }
    fun createResizableAssets(context: Context) {
        val correctedSharkWidth = SHARK_SHEET_WIDTH* (RATIO)
        val resources = context.resources

        createFish(context, correctedSharkWidth)
        createShark(context)
        createHUD(context, 200)

        //coins
        coinSpriteSheet = SpriteSheet( Bitmap.createBitmap( BitmapFactory.decodeResource(resources, R.drawable.coins)), COIN_SHEET_SIDE,
            COIN_SHEET_SIDE)
        var coinWidth = (0.1f * correctedSharkWidth).roundToInt()
        coinWidth += coinWidth % 2
        val frames = ArrayList<Bitmap>()
        frames.clear()
        frames.addAll(coinSpriteSheet.getScaledRow(0, 6, coinWidth, coinWidth))
        coinAnimated= AnimatedBitmap(3.0f, true, *frames.toTypedArray())

        //parallax
        parallaxBg1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,
            R.drawable.bg1
        ), Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels, true)
        parallaxBg2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,
            R.drawable.bg2
        ), Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels, true)
        parallaxBg3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,
            R.drawable.bg3
        ), Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels, true)
        parallaxBg4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,
            R.drawable.bg4
        ), Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels, true)

        parallax = listOf(parallaxBg1, parallaxBg2,parallaxBg4, parallaxBg3)
    }

    private fun createHUD(context: Context, sharkWidth: Int){
        val resources = context.resources

        val energySize: Int = (0.5f * sharkWidth).roundToInt()
        energyIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.energy), energySize, energySize, true)

        val energyBarSize : Int = (0.5f * sharkWidth).roundToInt()
        energyBar0 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar0_empty), energyBarSize*2, energyBarSize, true)
        energyBar1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar1), energyBarSize*2, energyBarSize, true)
        energyBar2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar2), energyBarSize*2, energyBarSize,true)
        energyBar3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar3), energyBarSize*2, energyBarSize,true)
        energyBar4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar4), energyBarSize*2, energyBarSize,true)
        energyBar5 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar5), energyBarSize*2, energyBarSize,true)
        energyBar6 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar6), energyBarSize*2, energyBarSize,true)
        energyBar7 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar7),energyBarSize*2, energyBarSize,true)
        energyBar8 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar8), energyBarSize*2, energyBarSize,true)
        energyBar9 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar9), energyBarSize*2, energyBarSize,true)
        energyBar10 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar10_normal), energyBarSize*2, energyBarSize,true)
        energyBar11 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar11), energyBarSize*2, energyBarSize,true)
        energyBar12 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar12), energyBarSize*2, energyBarSize,true)
        energyBar13 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar13),energyBarSize*2, energyBarSize,true)
        energyBar14 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar14), energyBarSize*2, energyBarSize,true)
        energyBar15 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.health_bar15_full), energyBarSize*2, energyBarSize,true)
    }
}