package com.quang.huy.toanhocapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_dinh_thuc.*

class DinhThucActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dinh_thuc)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        btnCapMatran.setOnClickListener {
            kiemTraVaChuyenManHinh()
        }
        edtCapMaTran.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                kiemTraVaChuyenManHinh()
                true
            }else{
                false
            }
        })
    }

    override fun onBackPressed() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

    fun kiemTraVaChuyenManHinh(){
        try {
            var capMaTran: Int = edtCapMaTran.text.toString().toInt()
            Log.d("AAA", capMaTran.toString())
            if (capMaTran in 1..1000) {
                var imm : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
                val intent: Intent = Intent(this@DinhThucActivity, TinhDinhThucActivity::class.java)
                intent.putExtra("capmatran", capMaTran)
                finish()
                startActivity(intent)
            }else{
                nhapSaiCapMaTran.setText("Bạn vui lòng nhập lại số 1 - 1000")
            }
        }
        catch (e:Exception) {
            nhapSaiCapMaTran.setText("Bạn vui lòng nhập lại!")
        }
    }
}
