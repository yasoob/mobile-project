package me.yasoob.minesweeper.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.yasoob.minesweeper.MainActivity
import me.yasoob.minesweeper.R
import me.yasoob.minesweeper.model.Field
import me.yasoob.minesweeper.model.MinesweeperModel

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var paintBackground : Paint = Paint()
    var paintLine : Paint = Paint()
    var paintText: Paint = Paint()

    var bitmapExplosion: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.explosion
    )
    var bitmapFlag: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.flag
    )

    init {
        paintBackground.color = Color.GREEN
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 7f

        paintText.color = Color.WHITE
        paintText.style = Paint.Style.FILL_AND_STROKE
        paintText.strokeWidth = 7f
        paintText.textSize = 120f
        paintText.textAlign = Paint.Align.CENTER

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (MinesweeperModel.inProgress) {
            (context as MainActivity).updateFlagCount(
                context.getString(
                    R.string.flag_count,
                    MinesweeperModel.flagsLeft()
                )
            )
        }
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        drawBoard(canvas)
        drawMines(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmapExplosion = Bitmap.createScaledBitmap(bitmapExplosion, width/5, height/5, false)
        bitmapFlag = Bitmap.createScaledBitmap(bitmapFlag, width/5, height/5, false)
    }

    private fun drawBoard(canvas: Canvas?) {
        // border
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)

        // two horizontal lines
        for (counter in 1..4){
            canvas?.drawLine(
                0f, (counter * height / 5).toFloat(),
                width.toFloat(), (counter * height / 5).toFloat(),
                paintLine
            )
        }

        // two vertical lines
        for (counter in 1..4){
            canvas?.drawLine(
                (counter * width / 5).toFloat(), 0f,
                (counter * width / 5).toFloat(), height.toFloat(),
                paintLine
            )
        }
    }

    private fun textDrawPosition(row: Int, col: Int): Pair<Float,Float> {
        var xOffset: Float = (width.toFloat()/5)/2
        var yOffset: Float = height.toFloat()/5 - ((paintText.ascent() + paintText.descent())/2)
        var x: Float = col * width.toFloat()/5 + xOffset
        var y: Float = (row + 2) * height.toFloat()/5 - yOffset
        return Pair(x, y)
    }

    private fun drawMines(canvas: Canvas?){
        for (row in 0..4){
            for (col in 0..4) {
                var fieldContent: Field = MinesweeperModel.getFieldContent(row, col)
                if (fieldContent.wasClicked && fieldContent.type==MinesweeperModel.MINE && !fieldContent.isFlagged){
                    canvas?.drawBitmap(bitmapExplosion, col*width.toFloat()/5, row*height.toFloat()/5, null)
                } else if (fieldContent.isFlagged){
                    canvas?.drawBitmap(bitmapFlag, col*width.toFloat()/5, row*height.toFloat()/5, null)
                } else if (fieldContent.wasClicked && fieldContent.type == MinesweeperModel.EMPTY) {
                    var pos = textDrawPosition(row, col)
                    canvas?.drawText(fieldContent.minesAround.toString(), pos.first, pos.second, paintText)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width/5)
            val tY = event.y.toInt() / (height/5)

            if (tX < 5 && tY < 5 && MinesweeperModel.inProgress){

                // Check whether to flag the mine or not and then set field content
                if ((context as MainActivity).toggleBtnChecked()){
                    MinesweeperModel.setFieldContent(tY, tX, true)
                } else {
                    MinesweeperModel.setFieldContent(tY, tX, false)
                }

                // Check if user won the game or lost
                if (MinesweeperModel.didUserWin()){
                    (context as MainActivity).showStatus(context.getString(R.string.win_msg))
                    paintBackground.color = Color.LTGRAY
                    MinesweeperModel.inProgress = false
                    (context as MainActivity).updateFlagCount(context.getString(R.string.win_msg))
                } else if (MinesweeperModel.didUserLose()) {
                    (context as MainActivity).showStatus(context.getString(R.string.lost_msg))
                    paintBackground.color = Color.LTGRAY
                    MinesweeperModel.inProgress = false
                    (context as MainActivity).updateFlagCount(context.getString(R.string.lost_msg))
                }
            }
            invalidate()
        }
        return true
    }

    fun reset(){
        MinesweeperModel.resetArray()
        paintBackground.color = Color.GREEN
        MinesweeperModel.inProgress = true
        invalidate()
    }

}