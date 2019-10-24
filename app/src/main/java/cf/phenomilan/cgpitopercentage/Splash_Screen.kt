package cf.phenomilan.cgpitopercentage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("NAME_SHADOWING")
class Splash_Screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }, 3000)
        val animation = AnimationUtils.loadAnimation(this, R.anim.animation)
        app_logo.startAnimation(animation)

    }

}

