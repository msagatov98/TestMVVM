package com.example.testmvvm.employee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testmvvm.R

class EmployeeAdapter: RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder>() {

    private var employees: List<Employee> = ArrayList()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_holder, parent, false)
        return EmployeeHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        holder.bind(employees[position])
    }

    override fun getItemCount(): Int {
        return employees.size
    }

    fun setEmployee(notes: List<Employee>) {
        this.employees = notes
    }

    fun getEmployeeAt(position: Int): Employee {
        return employees[position]
    }

    inner class EmployeeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(employee: Employee) {
            itemView.findViewById<TextView>(R.id.employeeName).text = employee.name
            itemView.findViewById<TextView>(R.id.employeeEmail).text = employee.email

            itemView.setOnClickListener {
                val position = adapterPosition

                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(employees[position])
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(employee: Employee)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}