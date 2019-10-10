package cf.phenomilan.cgpitopercentage

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main2.*
import java.math.RoundingMode
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    lateinit var spinner: Spinner
    lateinit var result: TextView
    var percentage: Double? = null
    private lateinit var mAdview: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        ///////////for ads////////////////////
        MobileAds.initialize(this) {}
        mAdview = findViewById(R.id.adMob)
        val adRequest =
            AdRequest.Builder().build()
        mAdview.loadAd(adRequest)


        spinner = this.findViewById(R.id.spinner_id)
        result = findViewById(R.id.percentage_text)

        val options = arrayOf(
            "Mumbai University", //0
            "Pune University", //1
            "Kolkata University",//2 -0.5*10
            "JNTU Hyderabad",//3  -0.5*10
            "Delhi Technological University",//4 10
            "Anna University",//5 10
            "Andhra University",//6 10
            "Lucknow University",//7 10
            "Bangalore University",//8 10
            "Dr.Ambedkar Institute of Tech",//9 cgpa - 0.75 *10
            "VTU Karnataka", //10 cgpa - 0.75 *10
            "APJ Abdul Kalam University(KTU)" //11  //kerala
        )


        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            @SuppressLint("SetTextI18n")
            override fun onNothingSelected(parent: AdapterView<*>?) {
                result.text = "Select"
            }


            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val sharedpref = getSharedPreferences("position_text", Context.MODE_PRIVATE)
                val editor = sharedpref.edit()
                editor.putInt("pos", position)
                editor.apply()
            }
        }

        submit_button.setOnClickListener {
            val cgpa = cgpa_id.text.toString()
            val sharedpref = getSharedPreferences("position_text", Context.MODE_PRIVATE)
            val pos = sharedpref.getInt("pos", 0)
            if (cgpa.isEmpty() || cgpa.toFloat() > 10.0) {

                cgpa_id.error = "please enter valid CGPA"
                cgpa_id.requestFocus()
                percentage_text.text = " "
                return@setOnClickListener
            }
            if (pos == 0) {
                Mumbai(cgpa)
            } else if (pos == 1) {
                Pune(cgpa)
            } else if (pos == 2 || pos == 3) {
                Kolkata(cgpa) //same for JNTU HYBD
            } else if (pos in 4..8) { //position>=3 && position<=7
                MultiplyBy10(cgpa)
            } else if (pos == 9 || pos == 10) {
                Ambedkar(cgpa)
            } else if (pos == 11) {
                ApjAbdulKalam(cgpa)
            }
        }

        share_icon.setOnClickListener {
            val intent = Intent()
            val message = "Download CGPI to Percentage: For all Univeristy App for free \n"
            val app_link: String =
                message + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, app_link)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "select apps to share"))
        }

        email_icon.setOnClickListener {
            val rec = "milansam360@gmail.com".toString()
            val intent1 = Intent(Intent.ACTION_SEND)
            intent1.data = Uri.parse("mailto:")
            intent1.putExtra(Intent.EXTRA_EMAIL, arrayOf(rec))
            intent1.type = "text/plain"
            startActivity(Intent.createChooser(intent1, "Select Email Client"))
        }

        rate_icon.setOnClickListener {
            val intent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=cf.phenomilan.cgpitopercentage")
                )
            startActivity(intent)
        }

        cal_btn.setOnClickListener {
            val intent = Intent(this, calculator_activity::class.java)
            startActivity(intent)
        }


    }

    // private var backpressed = false
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure to Exit?")
        builder.setCancelable(true)
        builder.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, which -> exit() })
        val alertdialog = builder.create()
        alertdialog.show()
    }

    private fun exit() {
        moveTaskToBack(true)
        exitProcess(-1)
    }

    private fun Mumbai(cgpa: String) {
        if (cgpa.toFloat() < 7.0) {
            percentage = (cgpa.toDouble() * 7.1) + 12
        } else {
            percentage = (cgpa.toDouble() * 7.4) + 12
        }
        val df = percentage?.toBigDecimal()?.setScale(2, RoundingMode.CEILING)?.toDouble()
        result.text = "$df%"
        result.isVisible = true
    }

    private fun Pune(cgpa: String) {
        percentage = (cgpa.toDouble()) * 8.80
        val df = percentage?.toBigDecimal()?.setScale(2, RoundingMode.CEILING)?.toDouble()
        result.text = "$df%"
        result.isVisible = true
    }

    private fun Kolkata(cgpa: String) { //same for JNTU Hydrabad
        percentage = (cgpa.toDouble() - 0.5) * 10
        val df = percentage?.toBigDecimal()?.setScale(2, RoundingMode.CEILING)?.toDouble()
        result.text = "$df%"
        result.isVisible = true
    }

    private fun MultiplyBy10(cgpa: String) {
        percentage = cgpa.toDouble() * 10
        val df = percentage?.toBigDecimal()?.setScale(2, RoundingMode.CEILING)?.toDouble()
        result.text = "$df%"
        result.isVisible = true
    }

    fun Ambedkar(cgpa: String) { // same for VTU
        percentage = (cgpa.toDouble() - 0.75) * 10
        val df = percentage?.toBigDecimal()?.setScale(2, RoundingMode.CEILING)?.toDouble()
        result.text = "$df%"
        result.isVisible = true
    }

    fun ApjAbdulKalam(cgpa: String) {
        percentage = (cgpa.toDouble() * 10) - 3.75
        val df = percentage?.toBigDecimal()?.setScale(2, RoundingMode.CEILING)?.toDouble()
        result.text = "$df%"
        result.isVisible = true
    }
}

