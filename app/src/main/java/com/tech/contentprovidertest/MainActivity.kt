package com.tech.contentprovidertest

import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    var list:ListView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list=findViewById(R.id.listview)
   var status=ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)

   if(status==PackageManager.PERMISSION_GRANTED)
   {
       getContacts()
   }else
   {
       ActivityCompat.requestPermissions(this,
               arrayOf(android.Manifest.permission.READ_CONTACTS),12)
   }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            getContacts()
        }else
        {
            Toast.makeText(this,"user is not allowed to access contact details",Toast.LENGTH_LONG).show()
        }
    }

    private fun getContacts() {

        var contentResolver=getContentResolver()

       // var uri=ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME

      var cusror= if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
          contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null)
      } else {
          TODO("VERSION.SDK_INT < O")
      }

        var from=arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER)

        var int= intArrayOf(R.id.tv1,R.id.tv2)
        var myadapter=SimpleCursorAdapter(this,R.layout.mycontacts,cusror,from,int)
        list?.adapter=myadapter
    }
}