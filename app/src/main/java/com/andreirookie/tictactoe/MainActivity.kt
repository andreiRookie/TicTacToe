package com.andreirookie.tictactoe

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andreirookie.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        binding.toNewGameButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        binding.toContinueGameButton.setOnClickListener {
            val data = getInfoAboutGame()

            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra(EXTRA_TIME, data.time)
                putExtra(EXTRA_GAME_FIELD, data.gameField)
            }
            startActivity(intent)
        }

        binding.toSettingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    data class InfoGame(val time: Long, val gameField: String)

    private fun getInfoAboutGame() : InfoGame {
        with (getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)) {
            val time = getLong(EXTRA_TIME, 0L) // time ?: 0L
            val gameField = getString(EXTRA_GAME_FIELD,"")

            return if (gameField != null) {
                InfoGame(time, gameField)
            } else {
                InfoGame(0, "")
            }
        }
    }

    companion object {
        const val EXTRA_TIME = "extra_time"
        const val EXTRA_GAME_FIELD = "extra_game_field"
    }
}