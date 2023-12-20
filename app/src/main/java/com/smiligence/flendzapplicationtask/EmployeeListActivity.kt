package com.smiligence.flendzapplicationtask

import android.app.AlertDialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley

class EmployeeListActivity : AppCompatActivity() {

    var viewGroup: ViewGroup? = null
    var network_alertDialog: AlertDialog? = null
    lateinit var recyclerView: RecyclerView
    private lateinit var employeelistAdapter:  EmployeelistAdapter
    private lateinit var requestQueue: RequestQueue
    private var employeeList: MutableList<Usetdetails> = mutableListOf()
    lateinit var dbHelper : DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setTitle("Employee List")
        setSupportActionBar(toolbar)


        viewGroup = findViewById(android.R.id.content)
        val dialogView: View = LayoutInflater.from(this).inflate(R.layout.internet_dialog, viewGroup, false)
        val builder1 = AlertDialog.Builder(this)
        builder1.setView(dialogView)
        network_alertDialog = builder1.create()

        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


        recyclerView = findViewById(R.id.all_emplistrecyclerview);


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setNestedScrollingEnabled(false)


        requestQueue = Volley.newRequestQueue(this)
      //  fetchData()
         dbHelper = DatabaseHelper(this)

// Insert an employee

// Retrieve all employees
        val allEmployees = dbHelper.getAllEmployees()
        Log.e("allEmployees",allEmployees.size.toString())

        if (allEmployees.size > 0){
            Toast.makeText(this,"Get data from SQLlitE",Toast.LENGTH_SHORT).show()

            employeelistAdapter = EmployeelistAdapter(this@EmployeeListActivity,allEmployees)
            recyclerView.adapter = employeelistAdapter
            employeelistAdapter.notifyDataSetChanged()


        }else{
            Toast.makeText(this,"Fetch data from Api",Toast.LENGTH_SHORT).show()
            fetchData()
        }



    }

    fun  fetchData(){



        val url = "https://jsonplaceholder.typicode.com/users"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                // Handle the JSON response
                val employees = parseJson(response)

              //  val employeee = Usetdetails(id = 1, name = "Leanne Graham",email ="nandhu@gmail.com")
                dbHelper.insertEmployee(employees)


                employeeList.addAll(employees)

                employeelistAdapter = EmployeelistAdapter(this@EmployeeListActivity,employeeList)
                recyclerView.adapter = employeelistAdapter
                employeelistAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                // Handle errors
                error.printStackTrace()
            }
        )

        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest)
    }



    private fun parseJson(jsonArray: JSONArray): List<Usetdetails> {
        val employees = mutableListOf<Usetdetails>()

        for (i in 0 until jsonArray.length()) {
            val employeeJson = jsonArray.getJSONObject(i)
            val employee = Usetdetails(
                employeeJson.getInt("id"),
                employeeJson.getString("name"),
                employeeJson.getString("email")
            )
            employees.add(employee)
        }

        return employees
    }

}