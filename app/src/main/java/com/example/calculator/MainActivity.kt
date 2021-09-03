c
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mukesh.OtpView
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    private lateinit var tvInputValue: TextView
    private lateinit var tvOutputValue: TextView
    private var expression = "0"
    private var result = ""
    private lateinit var sp: SharedPreferences
    private val resultKey = "result"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = getSharedPreferences("result_pref", MODE_PRIVATE)
        if (!Constants.firstRun) {
            showPasscodeDialog()
        }
        initialization()

        getResultIfExist()
    }

    private fun getResultIfExist() {
        val result = sp.getString(resultKey, null)

        if (result != null) {
            tvOutputValue.text = result
        }
    }

    override fun onBackPressed() {
        showAlert()

    }

    private fun showPasscodeDialog() {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_passcode)
        val otpPasscode = dialog.findViewById<OtpView>(R.id.otp_passcode)
        otpPasscode.setOtpCompletionListener {
            if (SharedPreferencesManager.getInstance(this).passCode == otpPasscode.text.toString()) {
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Incorrect Passcode", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.setCancelable(false)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Confirmation Dialog")
        //set message for alert dialog
        builder.setMessage("Do you want to save the result?")
        builder.setIcon(R.drawable.icons8_calculator_24)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            if (result.isEmpty()) {
                return@setPositiveButton
            }
            saveResult()
        }
        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            Toast.makeText(applicationContext, "Exit Cancelled", Toast.LENGTH_LONG).show()
        }

        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            Toast.makeText(applicationContext, "Result Not Saved", Toast.LENGTH_LONG).show()
            val editor: SharedPreferences.Editor = sp.edit()
            editor.putString(resultKey, "")
            editor.apply()
            finish()

        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

///...!------------------------------------------------------------------------------!...///

    private fun initialization() {
        tvInputValue = findViewById(R.id.tv_input_value)
        tvOutputValue = findViewById(R.id.tv_output_value)

    }

    fun onACPressed(view: android.view.View) {
        expression = "0"
        tvInputValue.text = expression
        result = ""
        tvOutputValue.text = result

    }

    fun onDelPressed(view: android.view.View) {
        expression = if (expression.length > 1) {
            expression.substring(0, expression.length - 1)

        } else {
            "0"

        }
        tvInputValue.text = expression
    }

    fun onMinusPressed(view: android.view.View) {

        expressionNotEqual("0", "-")

    }

    fun onPlusPressed(view: android.view.View) {

        expressionNotEqual("0", "+")


    }

    fun onDividePressed(view: android.view.View) {

        expressionNotEqual("0", "/")

    }

    fun onMultiplyPressed(view: android.view.View) {

        expressionNotEqual("0", "*")
    }

    fun onEqualsPressed(view: android.view.View) {

        try {
            val exp = Expression(expression)
            result = exp.calculate().toString()

            val f = DecimalFormat("##.00")
            result = f.format(result.toDouble()).toString()

            if (result.contains(".00")) {
                result = result.substring(0, result.length - 3)
            } else if (result.contains(".0")) {
                result = result.substring(0, result.length - 2)
            }

            tvOutputValue.text = result
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    fun onDotPressed(view: android.view.View) {

        expressionNotEqual("0", ".")

    }

///...!----------------------------------------------------------------------!...///

    fun onOneZeroPressed(view: android.view.View) {
        expressionNotEqual("0", "0")

    }

    fun onTwoZerosPressed(view: android.view.View) {
        expressionNotEqual("0", "00")
    }

    fun onOnePressed(view: android.view.View) {
        expressionCondition("1")
    }

    fun onTwoPressed(view: android.view.View) {
        expressionCondition("2")
    }

    fun onThreePressed(view: android.view.View) {
        expressionCondition("3")
    }

    fun onFourPressed(view: android.view.View) {
        expressionCondition("4")
    }

    fun onFivePressed(view: android.view.View) {
        expressionCondition("5")
    }

    fun onSixPressed(view: android.view.View) {
        expressionCondition("6")
    }

    fun onSevenPressed(view: android.view.View) {
        expressionCondition("7")
    }

    fun onEightPressed(view: android.view.View) {
        expressionCondition("8")
    }

    fun onNinePressed(view: android.view.View) {
        expressionCondition("9")
    }

    private fun expressionCondition(value: String) {
        if (expression == "0") {
            expression = value
        } else {
            expression += value
        }
        tvInputValue.text = expression
    }

    private fun expressionNotEqual(conditionalValue: String, valueInput: String) {
        if (expression != conditionalValue) {
            expression += valueInput
            tvInputValue.text = expression
        }
    }

    private fun saveResult() {
        val editor: SharedPreferences.Editor = sp.edit()
        editor.putString(resultKey, result)
        editor.apply()
        Toast.makeText(applicationContext, "Saved Result", Toast.LENGTH_LONG).show()
        finish()

    }


}