package com.smiligence.flendzapplicationtask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeDetailAdapter(val context: Context, val employeeList: Employeedetail) :
    RecyclerView.Adapter<EmployeeDetailAdapter.EmployeedetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeDetailAdapter.EmployeedetailViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.employeeldetail_cardview, parent, false)
        return EmployeeDetailAdapter.EmployeedetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeDetailAdapter.EmployeedetailViewHolder, position: Int) {
        holder.nameTextView.setText(employeeList.name)
        holder.phoneTextView.setText(employeeList.phone)
        holder.emailTextView.setText(employeeList.email)

    }

    override fun getItemCount(): Int {
       return 1
    }


    class EmployeedetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView: TextView = itemView.findViewById(R.id.emp_name)
        val emailTextView: TextView = itemView.findViewById(R.id.emp_emailid)
        val phoneTextView: TextView = itemView.findViewById(R.id.emp_phoneNumber)

    }
}