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
//        val permissions = arrayOf(
//            android.Manifest.permission.INTERNET,
//            android.Manifest.permission.ACCESS_NETWORK_STATE
//        )


        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }, 3000)
        val animation = AnimationUtils.loadAnimation(this, R.anim.animation)
        app_logo.startAnimation(animation)

//        if (!hasPermission(this, *permissions)) {
//
//            ActivityCompat.requestPermissions(this, permissions, 12)
//        } else {
//            Handler().postDelayed({
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                this.finish()
//            }, 1000)
//        }


    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//
//            12 -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
//                ) {
//                    Handler().postDelayed({
//                        val intent = Intent(this, MainActivity::class.java)
//                        startActivity(intent)
//                        this.finish()
//                    }, 1000)
//                    return
//
//                } else {
//                    Toast.makeText(this, "Please Grant All Permission", Toast.LENGTH_SHORT).show()
//                    this.finish()
//                }
//            }
//            else -> {
//
//                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
//                this.finish()
//                return
//
//            }
//        }
//    }

//    private fun hasPermission(context: Context, vararg permission: String): Boolean {
//        var hasAllPermission = true
//        for (permission in permission) {
//            val res = context.checkCallingOrSelfPermission(permission)
//            if (res != PackageManager.PERMISSION_GRANTED) {
//                hasAllPermission = false
//            }
//        }
//        return hasAllPermission
//    }
}

