package com.quang.huy.toanhocapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tinh_dinh_thuc.*

class TinhDinhThucActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tinh_dinh_thuc)

        val intent : Intent = getIntent()
        val n : Int = intent.getIntExtra("capmatran", 0)
        var soNhap : Double
        var maTran : Array<DoubleArray> = Array(n, {DoubleArray(n)})
        var row : Int = 0!!
        var column : Int = 0!!
        edtPhanTu.setHint("Nhap phan tu $row $column")
        btnnhapPhanTu.setOnClickListener {
            try {
                soNhap = edtPhanTu.text.toString().toDouble()
                if (soNhap.isNaN()) {
                    Toast.makeText(this, "Hãy nhập lại phần tử", Toast.LENGTH_SHORT).show()
                } else {
                    if (row <= n - 1 && column <= n - 1) {
                        soNhap = edtPhanTu.text.toString().toDouble()
                        maTran[row][column] = soNhap
                        if (column <= n - 1) txtHienThiMT.append("$soNhap\t")
                        else txtHienThiMT.append(("$soNhap"))
                        edtPhanTu.setText("")
                        edtPhanTu.setHint("Nhập phần tử $row $column")
                        if (row == n - 1 && column == n - 1) Toast.makeText(this, "Đã nhập xong ma trận", Toast.LENGTH_LONG).show()
                        if (column == n - 1) {
                            row++
                            column = 0
                            txtHienThiMT.append("\n")
                        } else column++
                    } else {
                        Toast.makeText(this, "Da nhap xong", Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e : Exception){
                Toast.makeText(this, "Hãy nhập lại phần tử", Toast.LENGTH_SHORT).show()
            }
        }
        btnTinhDinhThuc.setOnClickListener {
            if(row==n) {
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
                        for (j in index until sizeMaTran) maTran[i][j] -= maTran[index][j] * a
                    }
                }
                txtHienThiMT.append("\n")
                for (i in indicesMatran) {
                    dt *= maTran[i][i]
                    for (j in indicesMatran) if (j < n - 1) txtHienThiMT.append("${maTran[i][j]}\t")
                    else txtHienThiMT.append("${maTran[i][j]}\n")
                }
                txtHienThiMT.append("\n$dt")
                row++
            }else if(row<n){
                Toast.makeText(this, "Bạn hãy nhập đầy đủ các phần tử", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Đã tính định thức xong", Toast.LENGTH_LONG).show()
            }
        }
    }
}
