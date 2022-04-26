package org.techtown.mydebuging

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.techtown.mydebuging.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val helloTextView: TextView = findViewById(R.id.division_textview)
        var count = 0
        helloTextView.setOnClickListener { helloTextView.text = (helloTextView.text.toString().toInt()+1).toString()  }
        helloTextView.text = "Hello, debugging!"
        helloTextView.text = "0"
        //division()

    }

    fun logging() {
        Log.e(TAG, "ERROR: a serious error like an app crash")
        Log.w(TAG, "WARN: warns about the potential for serious errors")
        Log.i(TAG, "INFO: reporting technical information, such as an operation succeeding")
        Log.d(TAG, "DEBUG: reporting technical information useful for debugging")
        Log.v(TAG, "VERBOSE: more verbose than DEBUG logs")
    }

    fun division() {
        val numerator = 60
        var denominator = 4
        repeat(4) {
            Thread.sleep(3000)
            findViewById<TextView>(R.id.division_textview).text = "${numerator / denominator}"
            Log.v(TAG, "${numerator / denominator}")
            denominator--
        }
    }

}

