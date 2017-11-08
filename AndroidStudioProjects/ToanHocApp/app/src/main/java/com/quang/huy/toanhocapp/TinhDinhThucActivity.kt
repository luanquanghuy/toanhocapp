package com.quang.huy.toanhocapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tinh_dinh_thuc.*

class TinhDinhThucActivity : AppCompatActivity() {

    var row : Int = 0!!
    var column : Int = 0!!
    var soNhap : Double = 0.0
    var n : Int = 0
    var maTran : Array<DoubleArray> = Array(1, {kotlin.DoubleArray(1)})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tinh_dinh_thuc)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        val intent : Intent = getIntent()
        n = intent.getIntExtra("capmatran", 0)
        maTran = Array(n, {DoubleArray(n)})
        edtPhanTu.setHint("Nhập phần tử hàng 1 cột 1")
        btnnhapPhanTu.setOnClickListener {
            nhapPhanTuMaTran()
        }
        edtPhanTu.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                if(row<n) nhapPhanTuMaTran()
                else{
                    tinhDinhThuc()
                }
                true
            }else false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tinhdinhthuc, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.itemLamMoi ->{
                val intent : Intent = Intent(this, DinhThucActivity::class.java)
                finish()
                startActivity(intent)
            }
            R.id.itemHomeTinhDT -> {
                val intent : Intent = Intent(this, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent : Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
    fun nhapPhanTuMaTran(){
        try {
            soNhap = edtPhanTu.text.toString().toDouble()
            if (soNhap.isNaN()) {
                Toast.makeText(this, "Hãy nhập lại phần tử", Toast.LENGTH_SHORT).show()
            } else {
                if (row <= n - 1 && column <= n - 1) {
                    soNhap = edtPhanTu.text.toString().toDouble()
                    maTran[row][column] = soNhap
                    if (column <= n - 1) txtHienThiMT.append("|\t$soNhap\t")
                    else txtHienThiMT.append(("$soNhap"))
                    edtPhanTu.setText("")
                    if(row==n-1&&column==n-2) btnnhapPhanTu.setText("Tính")
                    if (column == n - 1) {
                        row++
                        column = 0
                        txtHienThiMT.append("||\n")
                    } else column++
                    if (row == n && column == 0){
                        Toast.makeText(this, "Đã nhập xong ma trận", Toast.LENGTH_LONG).show()
                        tinhDinhThuc()
                    }
                    if(row<n) edtPhanTu.setHint("Nhập phần tử hàng ${row+1} cột ${column+1}")
                    else{
                        edtPhanTu.visibility = View.INVISIBLE
                        btnnhapPhanTu.visibility = View.INVISIBLE
                        edtPhanTu.isEnabled = false
                    }
                } else {
                    Toast.makeText(this, "Đã nhập xong ma trận", Toast.LENGTH_LONG).show()
                }
            }
        }catch (e : Exception){
            Toast.makeText(this, "Lỗi nhập phần tử, Hãy nhập lại", Toast.LENGTH_SHORT).show()
        }
    }
    fun tinhDinhThuc(){
        if(row==n) {
/*      Tắt bàn phím ảo */
//            var imm : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            var a: Double
            var dt: Double = 1.0!!
            var sizeMaTran = n!!
            var indicesMatran = maTran.indices!!
            var k: Int = 0!!
            for (index in 0 until sizeMaTran - 1) {
                if (maTran[index][index] == 0.0 && k < sizeMaTran) {
                    k = index + 1
                    while (maTran[index][index] == 0.0 && k < sizeMaTran) {
                        if (maTran[k][index] != 0.0) for (i in indicesMatran) maTran[k][i] = maTran[index][i].also { maTran[index][i] = maTran[k][i] }
                        k++
                    }
                    if (maTran[index][index] == 0.0 && k >= sizeMaTran) dt = 0.0
                }
                if (dt == 0.0) break
                for (i in index + 1 until sizeMaTran) {
                    a = maTran[i][index] / maTran[index][index]
                    maTran[i][index]=0.0
                    for (j in index+1 until sizeMaTran) maTran[i][j] -= maTran[index][j] * a
                }
            }
            txtHienThiMT.append("\n\n")
            for (i in indicesMatran) {
                dt *= maTran[i][i]
                for (j in indicesMatran) if (j < n - 1) txtHienThiMT.append("|\t%.3f".format(maTran[i][j]))
                else txtHienThiMT.append("|\t%.3f\t||\n".format(maTran[i][j]))
            }
            txtKetQuaDT.setText("%.4f".format(dt))
            row++
        }else if(row<n){
            Toast.makeText(this, "Bạn hãy nhập đầy đủ các phần tử", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Đã tính định thức xong", Toast.LENGTH_LONG).show()
        }
    }
}

