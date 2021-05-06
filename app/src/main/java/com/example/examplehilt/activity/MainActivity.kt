package com.example.examplehilt.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import com.example.examplehilt.R
import com.example.examplehilt.database.entity.ReceiptHistory
import com.example.examplehilt.database.viewmodel.ReceiptViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_history.*
import kotlinx.android.synthetic.main.toolbar_custom.view.*
import kotlinx.android.synthetic.main.toolbar_design_logo.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val TAG: String = "MainActivity"
    private var mDish = 0
    private var isCheckedTakephoto = false
    private val REQUEST_CAPTURE_IMAGE = 100
    private var imageBitmap: Bitmap? = null
    private var mAmount = ""
    private var mTip = ""
    // Calendar
    private val calendar: Calendar = Calendar.getInstance()
    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat("yyyy MMMM dd")

    private val mReceiptViewModel: ReceiptViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"TipViewModel Instance ${mReceiptViewModel.getInstance()}")

        initToolbar()
        initEvent()
        initValue()
    }

    private fun initToolbar() {
        toolbar.customToolbar.setMenuRightDisplay(true)
        toolbar.customToolbar.setHideBackButton(true)
        toolbar.customToolbar.setMenuRightImage(R.drawable.ic_history)
        toolbar.customToolbar.setHideToolbarUnderLine(true)
    }

    private fun initEvent() {
        ivMenuRight.setOnClickListener(onClicked)
        buttonSavePayment.setOnClickListener(onClicked)
        iButtonMinus.setOnClickListener(onClicked)
        iButtonPlus.setOnClickListener(onClicked)

        checkboxTakePhoto.setOnClickListener(onClicked)
    }

    private fun initValue() {
        tvDish.text = mDish.toString()
//        mTipViewModel = ViewModelProvider(this).get(TipViewModel::class.java)
    }

    private var onClicked = View.OnClickListener {
        when (it.id) {
            R.id.ivMenuRight -> {
                moveToActivitySlideToRight(this@MainActivity, HistoryActivity::class.java)
            }
            R.id.checkboxTakePhoto -> {
                isCheckedTakephoto = !isCheckedTakephoto
            }
            R.id.buttonSavePayment -> {
                savePayment()
            }
            R.id.iButtonPlus -> {
                if (mDish >= 0) {
                    mDish++
                    tvDish.text = mDish.toString()
                }
            }
            R.id.iButtonMinus -> {
                if (mDish > 0) {
                    mDish--
                    tvDish.text = mDish.toString()
                }
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun savePayment() {
        mAmount = ieAmount.text.toString().trim()
        mTip = ieTip.text.toString().trim()

        if(mAmount.isEmpty()) {
            Toast.makeText(this, getString(R.string.required_amount), Toast.LENGTH_LONG).show()
            return
        }

        if(mTip.isEmpty()) {
            Toast.makeText(this, getString(R.string.required_tip), Toast.LENGTH_LONG).show()
            return
        }

        if(isCheckedTakephoto) {
            val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (pictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE)
            }
            else {
                Toast.makeText(this, getString(R.string.error_camera), Toast.LENGTH_LONG).show()
                return
            }
        }
        else {
            saveTip()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_CAPTURE_IMAGE && resultCode === RESULT_OK) {
            if(data!!.extras != null) {
                imageBitmap = data.extras!!.get("data") as Bitmap
                saveTip()
            }
            else {
                Toast.makeText(this, "Something wrong", Toast.LENGTH_LONG).show()
            }
        }
        else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        }
    }

    private fun Bitmap.toBitmapToBase64(): String {
        val outputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }

    private fun saveTip() {
        var photoAsBase64 = ""
        if(imageBitmap != null) {
            photoAsBase64 = imageBitmap!!.toBitmapToBase64()
        }

        val date: String = dateFormat.format(calendar.time)
        val tip = ReceiptHistory(0, mAmount, mDish.toString(), mTip, photoAsBase64, date)
        mReceiptViewModel.addReceipt(tip)
        Toast.makeText(this, "The payment is successful", Toast.LENGTH_LONG).show()

        if(isCheckedTakephoto) {
            Timer("SettingUp", false).schedule(500) {
                Thread {
                    clearData()
                }
                moveToActivitySlideToRight(this@MainActivity, HistoryActivity::class.java)
            }
        }
        clearData()
    }

    private fun clearData() {
        // set text to empty
        ieAmount.setText("")
        ieTip.setText("")
        // set people to 0
        tvDish.text = "0"
        mDish = 0
        // remove checked to false
        isCheckedTakephoto = false
        findViewById<CheckBox>(R.id.checkboxTakePhoto).isChecked = false
    }
}