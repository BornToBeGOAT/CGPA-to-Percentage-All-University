package cf.phenomilan.cgpitopercentage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_calculator_activity.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.math.RoundingMode

class calculator_activity : AppCompatActivity() {

    private lateinit var mAdview2: AdView
    private lateinit var sharedpref: Sharedpref
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedpref = Sharedpref(this)

        if (sharedpref.loadNightMode()) {
            setTheme(R.style.Darktheme)
        } else if (sharedpref.loadAmoledMode()) {
            setTheme(R.style.Amoledtheme)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_activity)

        ///////////for ads////////////////////
        MobileAds.initialize(this) {}
        mAdview2 = findViewById(R.id.adMob2)
        val adRequest =
            AdRequest.Builder().build()
        mAdview2.loadAd(adRequest)

        /////////////for number///////////
        one_id.setOnClickListener {
            appendOnText("1", true)
        }
        two_id.setOnClickListener {
            appendOnText("2", true)
        }
        three_id.setOnClickListener {
            appendOnText("3", true)
        }
        four_id.setOnClickListener {
            appendOnText("4", true)
        }
        five_id.setOnClickListener {
            appendOnText("5", true)
        }
        six_id.setOnClickListener {
            appendOnText("6", true)
        }
        seven_id.setOnClickListener {
            appendOnText("7", true)
        }
        eight_id.setOnClickListener {
            appendOnText("8", true)
        }
        nine_id.setOnClickListener {
            appendOnText("9", true)
        }
        zero_id.setOnClickListener {
            appendOnText("0", true)
        }
        dot_id.setOnClickListener {
            appendOnText(".", true)
        }
        ///////////////for operators//////
        plus_id.setOnClickListener {
            appendOnText("+", false)
        }
        minus_id.setOnClickListener {
            appendOnText("-", false)
        }
        multiply_id.setOnClickListener {
            appendOnText("*", false)
        }
        divide_id.setOnClickListener {
            appendOnText("/", false)
        }
        open_bracket.setOnClickListener {
            appendOnText("(", false)
        }
        close_bracket.setOnClickListener {
            appendOnText(")", false)
        }

        clear_id.setOnClickListener {
            expression_id.text = ""
            result_id.text = ""
        }
        equal_id.setOnClickListener {
            try {
                val express = ExpressionBuilder(expression_id.text.toString()).build()
                val result = express.evaluate()
                val longResult =
                    result.toBigDecimal().setScale(2, RoundingMode.CEILING)?.toDouble()
                result_id.text = longResult.toString()

            } catch (e: Exception) {
                Toast.makeText(this, e.message.toString() + " error", Toast.LENGTH_SHORT).show()
            }
        }

        cut_id.setOnClickListener {
            val expression = expression_id.text.toString().trim()
            if (expression.isNotEmpty()) {
                expression_id.text = expression.substring(0, expression.length - 1)

            }
            result_id.text = ""
        }

    }


    fun appendOnText(string: String, canClear: Boolean) {

        if (result_id.text.isNotEmpty()) {
            expression_id.text = ""
        }
        if (canClear) {
            result_id.text = ""
            expression_id.append(string)
        } else {
            expression_id.append(result_id.text)
            expression_id.append(string)
            result_id.text = ""
        }
    }

}
