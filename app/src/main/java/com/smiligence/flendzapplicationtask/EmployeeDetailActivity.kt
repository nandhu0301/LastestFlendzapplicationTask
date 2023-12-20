package com.smiligence.flendzapplicationtask

import android.app.AlertDialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class EmployeeDetailActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    var  id =0;
    lateinit var recyclerView: RecyclerView
    private lateinit var employeedetailadapter:  EmployeeDetailAdapter
    private lateinit var requestQueue: RequestQueue
    private var employeeList: MutableList<Employeedetail> = mutableListOf()
    var viewGroup: ViewGroup? = null
    var network_alertDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setTitle("Employee Detail")
        setSupportActionBar(toolbar)

        viewGroup = findViewById(android.R.id.content)
        val dialogView: View = LayoutInflater.from(this).inflate(R.layout.internet_dialog, viewGroup, false)
        val builder1 = AlertDialog.Builder(this)
        builder1.setView(dialogView)
        network_alertDialog = builder1.create()

        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


        val intent = intent
        id = intent.getIntExtra("employeeid", 0)


        recyclerView = findViewById(R.id.all_empdetailrecyclerview);


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setNestedScrollingEnabled(false)


        requestQueue = Volley.newRequestQueue(this)

        fetchData()
    }

    fun fetchData() {

        val url = "https://jsonplaceholder.typicode.com/users/" + id


        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
            url, null,
            Response.Listener { response ->
                // Handle the JSON object response
                try {
                    val employee = parseJson(response)
                    employeedetailadapter = EmployeeDetailAdapter(this@EmployeeDetailActivity, employee)
                    recyclerView.adapter = employeedetailadapter
                    employeedetailadapter.notifyDataSetChanged()


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                // Handle errors
                error.printStackTrace()
            }
        )

        requestQueue.add(jsonObjectRequest)

    }

    private fun parseJson(jsonObject: JSONObject): Employeedetail {
        val employee = Employeedetail(
            jsonObject.getInt("id"),
            jsonObject.getString("name"),
            jsonObject.getString("email"),
            jsonObject.getString("phone")
        )

        return employee
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onPause() {
        super.onPause()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {

            network_alertDialog!!.setCancelable(false)
            network_alertDialog!!.show()
        } else {
            network_alertDialog!!.dismiss()
        }
    }


}