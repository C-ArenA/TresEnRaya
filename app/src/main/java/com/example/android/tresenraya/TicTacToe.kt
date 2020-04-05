package com.example.android.tresenraya

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// When a value is declared outside a class it can be accessed anywhere, even in other files
// However, if it has a "private" tag prefixed it'll only be accessed from this file

const val NO_PLAYER = 0
const val PLAYER_X = 1
const val PLAYER_O = 2

val WINS: Array<List<Int>> = arrayOf(
    // rows
    listOf(1,2,3),
    listOf(4,5,6),
    listOf(7,8,9),
    // columns
    listOf(1,4,7),
    listOf(2,5,8),
    listOf(3,6,9),
    // diagonal
    listOf(1,5,9),
    listOf(3,5,7)
)

class TicTacToe {

    var currentPlayer: Int = NO_PLAYER

    lateinit var gameStateMatrix: Array<Array<Int>>
    // these two arrays will be filled with the numbers of each matrix position using the next logic:
    //
    // 1 | 2 | 3
    //-----------
    // 4 | 5 | 6
    //-----------
    // 7 | 8 | 9
    //
    // Int he case of X, if x hasn't completed its 5 movements its default position will be 0
    private val xfilledSpaces: MutableList<Int> = mutableListOf()
    private var yfilledSpaces: MutableList<Int> = mutableListOf()

    var winningWay: Int = 0

    private val _stillPlaying: MutableLiveData<Boolean> = MutableLiveData()
    val stillPlaying: LiveData<Boolean>
        get() = _stillPlaying

    private val _xWon: MutableLiveData<Boolean> = MutableLiveData()
    val xWon: LiveData<Boolean>
        get() = _xWon

    private val _yWon: MutableLiveData<Boolean> = MutableLiveData()
    val yWon: LiveData<Boolean>
        get() = _yWon

    init {

        resetGame()
    }

    fun resetGame() {
        // It returns a 3x3 matrix of ceros (NO_PLAYER)
        //
        // 0 | 0 | 0
        //-----------
        // 0 | 0 | 0
        //-----------
        // 0 | 0 | 0
        //
        gameStateMatrix = Array(3){
            Array(3){
                NO_PLAYER
            }
        }
        // Sets the first player to be the PlayerX
        currentPlayer = PLAYER_X
        // Resets the results for the players
        _xWon.value = false
        _yWon.value = false
        // Resets the results vectors for players X and Y
        xfilledSpaces.clear()
        yfilledSpaces.clear()
        // Saves the state of the game in a boolean variable. This variable is being observed
        // So, its change to true will make the buttons to be activated
        _stillPlaying.value = true

    }

    fun nextGameStep(row: Int, col: Int) {
        // updates the state matrix
        gameStateMatrix[row][col] = currentPlayer
        // updates the current player
        updatePlayer()
        // checks the state of the game
        checkGameState()
    }


    // sets the new player
    private fun updatePlayer(){
        // updates the current player and the button state
        currentPlayer = if (currentPlayer == PLAYER_X) {
            PLAYER_O
        } else {
            PLAYER_X
        }
    }

    // checks if the game has finished whether some player has won or they have drawn (empatado)
    private fun checkGameState(){
        // TODO: Implement the logic to indicate the program a game has finished
        // checks the state of the game
        var filledSpaces = 0
        var indexOffset = 1
        var fieldIndex: Int

        gameStateMatrix.forEachIndexed { rowIndex, it ->
            it.forEachIndexed { columnIndex, spaceValue ->
                fieldIndex = indexOffset + columnIndex + rowIndex
                when(spaceValue) {
                    NO_PLAYER -> {}
                    PLAYER_X -> {
                        filledSpaces++
                        xfilledSpaces.add(fieldIndex)
                    }
                    PLAYER_O -> {
                        filledSpaces++
                        yfilledSpaces.add(fieldIndex)
                    }
                    else -> throw IllegalArgumentException("Wrong type of player")
                }
            }
            indexOffset+=2
        }


        // Checks if x won
        for (i in 0..7){
            winningWay = i
            _xWon.value = (xfilledSpaces.containsAll(WINS[winningWay]))
            if (_xWon.value!!) {
                _stillPlaying.value = false
                return
            }
        }
        // Checks if o won
        for (i in 0..7){
            winningWay = i
            _yWon.value = (yfilledSpaces.containsAll(WINS[i]))
            // Toast.makeText(Application().applicationContext, "Ganaste O!!!!!!!!", Toast.LENGTH_SHORT).show()
            if (_yWon.value!!) {
                _stillPlaying.value = false
                return
            }
        }

        if (filledSpaces >= 9) {
            _stillPlaying.value = false
        }

    }

}