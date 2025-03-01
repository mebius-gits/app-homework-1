package imd.ntub.myapplication

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var playerImageView: ImageView
    private lateinit var computerImageView: ImageView
    private lateinit var resultText: TextView
    private lateinit var rockButton: ImageButton
    private lateinit var paperButton: ImageButton
    private lateinit var scissorsButton: ImageButton
    private lateinit var playButton: Button
    private lateinit var playerScoreText: TextView
    private lateinit var computerScoreText: TextView

    // Track the selected choice
    private var playerChoice = 0
    private var computerChoice = 0

    // Track scores
    private var playerScore = 0
    private var computerScore = 0

    // Constants for game choices
    companion object {
        const val ROCK = 1
        const val PAPER = 2
        const val SCISSORS = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        playerImageView = findViewById(R.id.imageView)
        computerImageView = findViewById(R.id.imageView2)
        resultText = findViewById(R.id.resultText)
        rockButton = findViewById(R.id.rockButton)
        paperButton = findViewById(R.id.paperButton)
        scissorsButton = findViewById(R.id.scissorsButton)
        playButton = findViewById(R.id.button)
        playerScoreText = findViewById(R.id.playerScore)
        computerScoreText = findViewById(R.id.computerScore)

        // Set up choice buttons
        rockButton.setOnClickListener {
            selectChoice(ROCK)
        }

        paperButton.setOnClickListener {
            selectChoice(PAPER)
        }

        scissorsButton.setOnClickListener {
            selectChoice(SCISSORS)
        }

        // Set up play button
        playButton.setOnClickListener {
            if (playerChoice != 0) {
                playGame()
            } else {
                resultText.text = "Select a choice first!"
                animateText(resultText)
            }
        }

        // Initialize game display
        resetGameDisplay()
    }

    private fun selectChoice(choice: Int) {
        playerChoice = choice

        // Update player image based on selection
        val resourceId = when (choice) {
            ROCK -> R.drawable.stone
            PAPER -> R.drawable.paper
            SCISSORS -> R.drawable.scissors
            else -> R.drawable.question
        }

        playerImageView.setImageResource(resourceId)

        // Apply selection animation
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        playerImageView.startAnimation(animation)

        // Update status text
        resultText.text = "Ready to play!"
    }

    private fun playGame() {
        // Generate computer choice
        computerChoice = Random.nextInt(1, 4)

        // Update computer image
        val computerResourceId = when (computerChoice) {
            ROCK -> R.drawable.stone
            PAPER -> R.drawable.paper
            SCISSORS -> R.drawable.scissors
            else -> R.drawable.question
        }

        computerImageView.setImageResource(computerResourceId)

        // Apply animation to computer's choice
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        computerImageView.startAnimation(animation)

        // Determine the winner
        val result = determineWinner(playerChoice, computerChoice)
        updateScore(result)
        displayResult(result)

        // Reset for next round
        playerChoice = 0
    }

    private fun determineWinner(player: Int, computer: Int): Int {
        // Return: 1 = Player wins, -1 = Computer wins, 0 = Draw
        return when {
            player == computer -> 0
            (player == ROCK && computer == SCISSORS) ||
                    (player == PAPER && computer == ROCK) ||
                    (player == SCISSORS && computer == PAPER) -> 1
            else -> -1
        }
    }

    private fun updateScore(result: Int) {
        when (result) {
            1 -> {
                playerScore++
                playerScoreText.text = playerScore.toString()
            }
            -1 -> {
                computerScore++
                computerScoreText.text = computerScore.toString()
            }
        }
    }

    private fun displayResult(result: Int) {
        val resultMessage = when (result) {
            1 -> "You win!"
            -1 -> "Computer wins!"
            else -> "It's a draw!"
        }

        resultText.text = resultMessage

        // Set result text color based on outcome
        val colorResId = when (result) {
            1 -> R.color.win
            -1 -> R.color.lose
            else -> R.color.draw
        }

        resultText.setBackgroundResource(R.drawable.result_background)

        // Animate the result text
        animateText(resultText)
    }

    private fun animateText(textView: TextView) {
        val scaleAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        textView.startAnimation(scaleAnimation)
    }

    private fun resetGameDisplay() {
        playerImageView.setImageResource(R.drawable.question)
        computerImageView.setImageResource(R.drawable.question)
        resultText.text = "Make your choice!"
        playerChoice = 0
        computerChoice = 0
    }
}