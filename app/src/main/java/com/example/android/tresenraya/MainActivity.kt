package com.example.android.tresenraya

import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.android.tresenraya.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val myGame = TicTacToe()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtonsListeners()

        myGame.xWon.observe(this, Observer {
            // This helps us use Boolean since we know it cannot be null, but Kotlin doesn't know it (That's why it writes !)
            it?.let {hasXWon ->
                if (hasXWon){
                    Toast.makeText(applicationContext, "Ganaste X!!!!!!!!", Toast.LENGTH_SHORT).show()
                    finishedGameViewsProperties()
                    binding.gameResult.text = getString(R.string.xWonMessage)
                }
            }
        })

        myGame.yWon.observe(this, Observer {
            // This helps us use Boolean since we know it cannot be null, but Kotlin doesn't know it (That's why it writes !)
            it?.let { hasYWon ->
                if (hasYWon){
                    Toast.makeText(applicationContext, "Ganaste O!!!!!!!!", Toast.LENGTH_SHORT).show()
                    finishedGameViewsProperties()
                    binding.gameResult.text = getString(R.string.yWonMessage)
                }
            }
        })

        myGame.stillPlaying.observe(this, Observer {
            // This helps us use Boolean since we know it cannot be null, but Kotlin doesn't know it (That's why it writes !)
            it?.let { playing ->
                if (playing){
                        resetViews()
                } else if (!myGame.xWon.value!! && !myGame.yWon.value!!){
                    Toast.makeText(applicationContext, "Game has finished. Empate. Press Start", Toast.LENGTH_SHORT).show()
                    binding.gameResult.text = getString(R.string.empate)
                    binding.startButton.visibility = View.VISIBLE
                    binding.startButton.isEnabled = true
                }
            }
        })
    }


    private fun setButtonsListeners() {

    with(binding){
        button00.setOnClickListener { it -> it as Button
            // updates the screen and the button state
            updateButtonProperties(it)
            // game action after button has been pressed. Next game state, next step
            myGame.nextGameStep(0,0)
        }
        button01.setOnClickListener { it -> it as Button
            // updates the screen and the button state
            updateButtonProperties(it)
            // game action after button has been pressed. Next game state, next step
            myGame.nextGameStep(0,1)
        }
        button02.setOnClickListener { it -> it as Button
            // updates the screen and the button state
            updateButtonProperties(it)
            // game action after button has been pressed. Next game state, next step
            myGame.nextGameStep(0,2)
        }
        button10.setOnClickListener { it -> it as Button
            // updates the screen and the button state
            updateButtonProperties(it)
            // game action after button has been pressed. Next game state, next step
            myGame.nextGameStep(1,0)
        }
        button11.setOnClickListener { it -> it as Button
            // updates the screen and the button state
            updateButtonProperties(it)
            // game action after button has been pressed. Next game state, next step
            myGame.nextGameStep(1,1)
        }
        button12.setOnClickListener { it -> it as Button
            // updates the screen and the button state
            updateButtonProperties(it)
            // game action after button has been pressed. Next game state, next step
            myGame.nextGameStep(1,2)
        }
        button20.setOnClickListener { it -> it as Button
            // updates the screen and the button state
            updateButtonProperties(it)
            // game action after button has been pressed. Next game state, next step
            myGame.nextGameStep(2,0)
        }
        button21.setOnClickListener { it -> it as Button
            // updates the screen and the button state
            updateButtonProperties(it)
            // game action after button has been pressed. Next game state, next step
            myGame.nextGameStep(2,1)
        }
        button22.setOnClickListener { it -> it as Button
            // updates the screen and the button state
            updateButtonProperties(it)
            // game action after button has been pressed. Next game state, next step
            myGame.nextGameStep(2,2)
        }

        startButton.setOnClickListener {
            myGame.resetGame()
        }
    }

    }

    private fun updateButtonProperties(currentButton: Button){
        // updates the screen
        when(myGame.currentPlayer){
            PLAYER_X -> currentButton.text = "X"
            PLAYER_O -> currentButton.text = "O"
            else -> currentButton.text = "?"
        }
        // disables the button that has been pressed
        currentButton.isEnabled = false
    }

    private fun resetViews() {
        // Lambda with a receiver
        with(binding) {
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

    private fun finishedGameViewsProperties(){
        imageWinnerSetter(wayOfWinning = myGame.winningWay)

        with(binding){
            startButton.visibility = View.VISIBLE
            startButton.isEnabled = true

            binding.imageLineView.visibility = View.VISIBLE

            button00.isEnabled = false
            button01.isEnabled = false
            button02.isEnabled = false
            button10.isEnabled = false
            button11.isEnabled = false
            button12.isEnabled = false
            button20.isEnabled = false
            button21.isEnabled = false
            button22.isEnabled = false
        }


    }
}
