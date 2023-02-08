package com.andreirookie.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.edit
import androidx.core.view.isInvisible
import com.andreirookie.tictactoe.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private var currentVolume = 0
    private var currentLevel = 0
    private var currentRules = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        val settings = getSettingsInfo()
        currentVolume = settings.soundVolume
        currentLevel = settings.level
        currentRules = settings.rules

        when (currentRules) {
            1 -> binding.verticalCheckBox.isChecked = true
            2 -> binding.horizontalCheckBox.isChecked = true
            3 -> {
                binding.verticalCheckBox.isChecked = true
                binding.horizontalCheckBox.isChecked = true
            }
            4 -> binding.diagonalCheckBox.isChecked = true
            5 -> {
                binding.diagonalCheckBox.isChecked = true
                binding.verticalCheckBox.isChecked = true
            }
            6 -> {
                binding.diagonalCheckBox.isChecked = true
                binding.horizontalCheckBox.isChecked = true
            }
            7-> {
                binding.diagonalCheckBox.isChecked = true
                binding.verticalCheckBox.isChecked = true
                binding.horizontalCheckBox.isChecked = true
            }
        }

        if (currentLevel == 0) {
            binding.previousLevelButton.visibility = View.INVISIBLE
        } else if (currentLevel == 2) {
            binding.nextLevelButton.visibility = View.INVISIBLE
        }

        binding.soundBar.progress = currentVolume

        binding.difficultyLevel.text =
            resources.getStringArray(R.array.game_levels)[currentLevel]

        binding.nextLevelButton.setOnClickListener {
            currentLevel++

            if (currentLevel == 1) {
                binding.previousLevelButton.visibility = View.VISIBLE
            } else if (currentLevel == 2) {
                binding.nextLevelButton.visibility = View.INVISIBLE
            }
            binding.difficultyLevel.text =
                resources.getStringArray(R.array.game_levels)[currentLevel]

            updateLevel(currentLevel)
        }
        binding.previousLevelButton.setOnClickListener {
            currentLevel--

            if (currentLevel == 0) {
                binding.previousLevelButton.visibility = View.INVISIBLE
            } else if (currentLevel == 1) {
                binding.nextLevelButton.visibility = View.VISIBLE
            }
            binding.difficultyLevel.text =
                resources.getStringArray(R.array.game_levels)[currentLevel]

            updateLevel(currentLevel)
        }

        binding.verticalCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentRules++
            } else {
                currentRules--
            }
            updateRules(currentRules)
        }

        binding.horizontalCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentRules+=2
            } else{
                currentRules-=2
            }
            updateRules(currentRules)
        }

        binding.diagonalCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentRules+=4
            } else{
                currentRules-=4
            }
            updateRules(currentRules)
        }

        binding.soundBar.setOnSeekBarChangeListener(object: OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, volume: Int, p2: Boolean) {
                currentVolume = volume
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                updateSoundVolume(currentVolume)
            }

        })



        setContentView(R.layout.activity_settings)
    }

    // commit() - sync, apply() - async
    private fun updateSoundVolume(volume: Int) {
        with (getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit()) {
            putInt(PREF_VOLUME, volume)
            apply()
        }
    }
    private fun updateLevel(lvl: Int) {
        with (getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit()) {
            putInt(PREF_LEVEL, lvl)
            apply()
        }
    }
    private fun updateRules(rules: Int) {
        with (getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit()) {
            putInt(PREF_RULES, rules)
            apply()
        }
    }




    private fun getSettingsInfo() : SettingsInfo {
        with (getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)) {
            val volume = getInt(PREF_VOLUME, 0)
            val rules = getInt(PREF_RULES, 0)
            val lvl = getInt(PREF_LEVEL, 0)

            return SettingsInfo(volume, rules, lvl)
        }
    }

    data class SettingsInfo(
        val soundVolume: Int,
        val rules: Int,
        val level: Int
    )

    companion object{
        const val PREF_VOLUME = "volume"
        const val PREF_RULES = "rules"
        const val PREF_LEVEL = "level"
    }

}