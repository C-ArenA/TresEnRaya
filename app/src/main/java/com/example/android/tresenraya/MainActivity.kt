package com.example.android.tresenraya

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.tresenraya.databinding.ActivityMainBinding

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

class MainActivity : AppCompatActivity() {

    private var currentPlayer: Int = NO_PLAYER
    private var stillPlaying: Boolean = true
    private lateinit var gameStateMatrix: Array<Array<Int>>
    private lateinit var binding: ActivityMainBinding
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

    var xWon: Boolean = false
    var yWon: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resetGame()
        setAllTheButtons()
    }

    private fun resetGame() {
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
        // Saves the state of the game in a boolean variable
        stillPlaying = true
        // Sets the first player to be the PlayerX
        currentPlayer = PLAYER_X
        // Lambda with a receiver
        with(binding){
            button00.isEnabled = true
            button00.text = ""
            button01.isEnabled = true
            button01.text = ""
            button02.isEnabled = true
            button02.text = ""
            button10.isEnabled = true
            button10.text = ""
            button11.isEnabled = true
            button11.text = ""
            button12.isEnabled = true
            button12.text = ""
            button20.isEnabled = true
            button20.text = ""
            button21.isEnabled = true
            button21.text = ""
            button22.isEnabled = true
            button22.text = ""

            startButton.visibility = View.INVISIBLE
            startButton.isEnabled = false

            gameResult.text = ""

            imageLineView.visibility = View.GONE
        }
    }

    private fun setAllTheButtons() {

    with(binding){
        button00.setOnClickListener { it -> it as Button
            // updates the state matrix
            gameStateMatrix[0][0] = currentPlayer
            // game action after button has been pressed. Next game state, next step
            nextGameStep(it)
        }
        button01.setOnClickListener { it -> it as Button
            // updates the state matrix
            gameStateMatrix[0][1] = currentPlayer
            // game action after button has been pressed. Next game state, next step
            nextGameStep(it)
        }
        button02.setOnClickListener { it -> it as Button
            // updates the state matrix
            gameStateMatrix[0][2] = currentPlayer
            // game action after button has been pressed. Next game state, next step
            nextGameStep(it)
        }
        button10.setOnClickListener { it -> it as Button
            // updates the state matrix
            gameStateMatrix[1][0] = currentPlayer
            // game action after button has been pressed. Next game state, next step
            nextGameStep(it)
        }
        button11.setOnClickListener { it -> it as Button
            // updates the state matrix
            gameStateMatrix[1][1] = currentPlayer
            // game action after button has been pressed. Next game state, next step
            nextGameStep(it)
        }
        button12.setOnClickListener { it -> it as Button
            // updates the state matrix
            gameStateMatrix[1][2] = currentPlayer
            // game action after button has been pressed. Next game state, next step
            nextGameStep(it)
        }
        button20.setOnClickListener { it -> it as Button
            // updates the state matrix
            gameStateMatrix[2][0] = currentPlayer
            // game action after button has been pressed. Next game state, next step
            nextGameStep(it)
        }
        button21.setOnClickListener { it -> it as Button
            // updates the state matrix
            gameStateMatrix[2][1] = currentPlayer
            // game action after button has been pressed. Next game state, next step
            nextGameStep(it)
        }
        button22.setOnClickListener { it -> it as Button
            // updates the state matrix
            gameStateMatrix[2][2] = currentPlayer
            // game action after button has been pressed. Next game state, next step
            nextGameStep(it)
        }

        startButton.setOnClickListener {
            resetGame()
        }
    }

    }

    private fun nextGameStep(it: Button) {
        // updates the screen and the button state
        updateButtonProperties(it)
        // updates the current player
        updatePlayer()
        // checks the state of the game
        checkGameState()
    }

    private fun updateButtonProperties(currentButton: Button){
        // updates the screen
        when(currentPlayer){
            PLAYER_X -> currentButton.text = "X"
            PLAYER_O -> currentButton.text = "O"
            else -> currentButton.text = "?"
        }
        // disables the button that has been pressed
        currentButton.isEnabled = false
    }

    // sets the new player
    private fun updatePlayer(){
        // updates the current player and the button state
        if (currentPlayer == PLAYER_X) {
            currentPlayer = PLAYER_O
        } else {
            currentPlayer = PLAYER_X
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
            xWon = (xfilledSpaces.containsAll(WINS[i]))
            if (xWon) {
                imageWinnerSetter(i)
                break
            }
        }
        // Checks if o won
        for (i in 0..7){
            yWon = (yfilledSpaces.containsAll(WINS[i]))
            if (yWon) {
                imageWinnerSetter(i)
                break
            }
        }

        if (xWon || yWon || filledSpaces >= 9) {
            stillPlaying = false
            binding.startButton.visibility = View.VISIBLE
            binding.startButton.isEnabled = true
            xfilledSpaces.clear()
            yfilledSpaces.clear()
            if (xWon) {
                Toast.makeText(applicationContext, "Ganaste X!!!!!!!!", Toast.LENGTH_SHORT).show()
                binding.gameResult.text = getString(R.string.xWonMessage)
                binding.imageLineView.visibility = View.VISIBLE
                return
            }
            if (yWon) {

                Toast.makeText(applicationContext, "Ganaste O!!!!!!!!", Toast.LENGTH_SHORT).show()
                binding.gameResult.text = getString(R.string.yWonMessage)
                binding.imageLineView.visibility = View.VISIBLE
                return
            }
            // When the game finishes withdrawed if xWon and yWon are false
            if (filledSpaces  >= 9) {

                Toast.makeText(applicationContext, "Game has finished. Empate. Press Start", Toast.LENGTH_SHORT).show()
                binding.gameResult.text = getString(R.string.empate)
            }
        }

        xfilledSpaces.clear()
        yfilledSpaces.clear()
    }

    private fun imageWinnerSetter(wayOfWinning: Int) {
        when(wayOfWinning) {
            0 -> binding.imageLineView.setImageResource(R.drawable.ic_win1)
            1 -> binding.imageLineView.setImageResource(R.drawable.ic_win2)
            2 -> binding.imageLineView.setImageResource(R.drawable.ic_win3)
            3 -> binding.imageLineView.setImageResource(R.drawable.ic_win4)
            4 -> binding.imageLineView.setImageResource(R.drawable.ic_win5)
            5 -> binding.imageLineView.setImageResource(R.drawable.ic_win6)
            6 -> binding.imageLineView.setImageResource(R.drawable.ic_win7)
            7 -> binding.imageLineView.setImageResource(R.drawable.ic_win8)
            else -> binding.imageLineView.setImageResource(R.drawable.ic_win1)
        }
    }


}
