package com.example.testmvvm.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testmvvm.R
import kotlinx.android.synthetic.main.activity_employee.employeeName

class EmployeeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        val name = intent.getStringExtra(Extra.EMPLOYEE_NAME)

        employeeName.text = name
    }
}