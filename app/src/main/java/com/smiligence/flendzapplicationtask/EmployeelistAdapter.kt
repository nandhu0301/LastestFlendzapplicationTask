package com.smiligence.flendzapplicationtask

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class EmployeelistAdapter(val context: Context, val employeeList: List<Usetdetails>) :
RecyclerView.Adapter<EmployeelistAdapter.EmployeeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeelistAdapter.EmployeeListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.employeelist_cardview, parent, false)
        return EmployeelistAdapter.EmployeeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeelistAdapter.EmployeeListViewHolder, position: Int) {


        holder.nameTextView.setText(employeeList.get(position).name)
        holder.emailTextView.setText(employeeList.get(position).email)
        holder.emailTextView.setTextColor(ContextCompat.getColor(context, R.color.blue))

         holder.cardView.setOnClickListener {
             val intent = Intent(context, EmployeeDetailActivity::class.java)
             intent.putExtra("employeeid", position + 1)
             context.startActivity(intent)

         }

        holder.emailTextView.setOnClickListener(View.OnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:" + employeeList.get(position).email)
            context.startActivity(emailIntent)
        })

    }

    override fun getItemCount(): Int {
         return employeeList.size;
    }

    class EmployeeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.user_name)
        val emailTextView: TextView = itemView.findViewById(R.id.user_emailid)
       val cardView : CardView = itemView.findViewById(R.id.card_view)
    }
}



