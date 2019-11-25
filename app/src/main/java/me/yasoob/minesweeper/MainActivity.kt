package me.yasoob.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resetBtn.setOnClickListener{
            minesweeperView.reset()
        }
    }

    fun showStatus(text: String){
        Snackbar.make(layoutMain,
            text, Snackbar.LENGTH_LONG).show()
    }

    fun toggleBtnChecked(): Boolean{
        return toggleBtn.isChecked
    }

    fun updateFlagCount(text: String){
        flagsLeft.text = text
    }
}
