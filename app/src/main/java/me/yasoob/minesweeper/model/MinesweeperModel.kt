package me.yasoob.minesweeper.model

data class Field(var type: Short,
                 var minesAround: Int,
                 var isFlagged: Boolean,
                 var wasClicked: Boolean)

object MinesweeperModel {
    val totalMines: Int = 3
    public var inProgress: Boolean = true
    public val EMPTY: Short = 1
    public val MINE: Short = 2
    private var fieldMatrix: Array<Array<Field>> = arrayOf(
        arrayOf(
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false)
        ),
        arrayOf(
            Field(EMPTY, 1, false, false),
            Field(MINE, 1, false, false),
            Field(EMPTY, 2, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false)
        ),
        arrayOf(
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 2, false, false),
            Field(MINE, 1, false, false),
            Field(EMPTY, 1, false, false)
        ),
        arrayOf(
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 2, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false)
        ),
        arrayOf(
            Field(EMPTY, 1, false, false),
            Field(MINE, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false)
        )
    )

    fun resetArray(){
        fieldMatrix = arrayOf(
            arrayOf(
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 1, false, false)
            ),
            arrayOf(
                Field(EMPTY, 1, false, false),
                Field(MINE, 1, false, false),
                Field(EMPTY, 2, false, false),
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 1, false, false)
            ),
            arrayOf(
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 2, false, false),
                Field(MINE, 1, false, false),
                Field(EMPTY, 1, false, false)
            ),
            arrayOf(
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 2, false, false),
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 1, false, false)
            ),
            arrayOf(
                Field(EMPTY, 1, false, false),
                Field(MINE, 1, false, false),
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 1, false, false),
                Field(EMPTY, 1, false, false)
            )
        )
    }

    fun generateRandom(){
        for (i in 0..totalMines){
            val row = (0..5).random()
            val col = (0..5).random()


        }
        var row = 1
        var col = 1
    }

    fun setFieldContent(row: Int, col: Int, flagIt: Boolean){
        // Make sure we don't "unclick" a field which has already been cleared
        // This makes sure an explored field remains explored
        if (fieldMatrix[row][col].wasClicked && !fieldMatrix[row][col].isFlagged){
            return
        }

        if (fieldMatrix[row][col].wasClicked && fieldMatrix[row][col].isFlagged && flagIt){
            fieldMatrix[row][col].isFlagged = !flagIt
            fieldMatrix[row][col].wasClicked = false
            return
        }

        fieldMatrix[row][col].isFlagged = flagIt
        fieldMatrix[row][col].wasClicked = true

    }

    fun getFieldContent(row:Int, col: Int): Field{
        return fieldMatrix[row][col]
    }

    fun didUserWin(): Boolean{
        var userFlagCount = 0
        for (row in fieldMatrix){
            for (field in row){
                if (field.type == MINE && !field.isFlagged){
                    return false
                }
                if (field.type == EMPTY && field.isFlagged){
                    return false
                }
                if (field.isFlagged){
                    userFlagCount++
                }
            }
        }
        return userFlagCount == totalMines
    }

    fun didUserLose(): Boolean{
        var userFlagCount = 0
        for (row in fieldMatrix){
            for (field in row){
                if (field.type == MINE && field.wasClicked && !field.isFlagged) {
                    return true
                }
                else if (field.isFlagged){
                    userFlagCount++
                }
            }
        }
        return userFlagCount >= totalMines
    }

    fun flagsLeft(): Int{
        var userFlagCount = 0
        for (row in fieldMatrix){
            for (field in row){
                if (field.isFlagged){
                    userFlagCount++
                }
            }
        }
        return totalMines - userFlagCount
    }
}