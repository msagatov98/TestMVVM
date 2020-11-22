package com.example.testmvvm.employee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmvvm.*
import kotlinx.android.synthetic.main.activity_main.*

class EmployeeListActivity : AppCompatActivity() {

    private val employees = arrayListOf(
        Employee(ru = "Albar", name = "Magzhan", "", "msagatov98@gmail.com", "", "", "")
    )
    private lateinit var adapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)


        adapter = EmployeeAdapter()

        adapter.setEmployee(employees)

        adapter.setOnItemClickListener(object : EmployeeAdapter.OnItemClickListener {
            override fun onItemClick(employee: Employee) {
                val intent = Intent(this@EmployeeListActivity, EmployeeActivity::class.java)

                intent.putExtra(Extra.EMPLOYEE_NAME, employee.name)

                startActivity(intent)
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }
}