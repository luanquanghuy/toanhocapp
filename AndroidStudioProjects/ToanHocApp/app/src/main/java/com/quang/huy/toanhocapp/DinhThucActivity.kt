package com.quang.huy.toanhocapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dinh_thuc.*

class DinhThucActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dinh_thuc)

        btnCapMatran.setOnClickListener {
            try {
                var capMaTran: Int = edtCapMaTran.text.toString().toInt()
                Log.d("AAA", capMaTran.toString())
                val intent: Intent = Intent(this@DinhThucActivity, TinhDinhThucActivity::class.java)
                if (capMaTran > 0) {
                    intent.putExtra("capmatran", capMaTran)
                    startActivity(intent)
                }else{
                    nhapSaiCapMaTran.setText("Bạn vui lòng nhập lại!")
                }
            }
            catch (e:Exception) {
                nhapSaiCapMaTran.setText("Bạn vui lòng nhập lại!")
            }
        }
    }
}
