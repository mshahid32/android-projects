package com.example.easytravel.remote


import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.example.easytravel.Models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import javax.inject.Inject
import kotlin.Comparator
import kotlin.collections.ArrayList

class FirebaseSource @Inject constructor() {
    private val firebaseDatabase : FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun currentUser() = auth.currentUser

    fun login(email: String, password: String) : MutableLiveData<Boolean> {
        val postLiveData = MutableLiveData<Boolean>()
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            postLiveData.value = task.isSuccessful
        }
        return postLiveData
    }

    fun register(email: String, password: String) : MutableLiveData<String>{
        val postLiveData = MutableLiveData<String>()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                postLiveData.value = task.result.user!!.uid
            }else{
                postLiveData.value = "error :" + task.exception!!.message
            }
        }
        return postLiveData
    }

    fun setUser(id : String , user : Users){
        firebaseDatabase.reference.child("Users").child(id).setValue(user)
    }

    fun getBusesDetail(busTime : String) : MutableLiveData<ArrayList<Buses>> {
        val database = firebaseDatabase.getReference(busTime)
        val postLiveData = MutableLiveData<ArrayList<Buses>>()
        val list = ArrayList<Buses>()

        database.orderByChild("busNo").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (dataSnapshot in snapshot.children) {
                    val buses = dataSnapshot.getValue(Buses::class.java)

                    if (buses != null) {
                        list.add(buses)
                    }
                }
                postLiveData.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return postLiveData
    }

    fun getUserDetail() : MutableLiveData<Users> {
        val database = firebaseDatabase.getReference("Users").child(auth.uid.toString())
        val postLiveData = MutableLiveData<Users>()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data  = snapshot.getValue(Users::class.java)
                if (data != null){
                    postLiveData!!.value = data
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return postLiveData
    }

    fun CheckBusPoint(pointName : String) : MutableLiveData<Int> {
        val database = firebaseDatabase.getReference("Points")
        val postLiveData = MutableLiveData<Int>()
        var flag  = false
        database.orderByChild("busNo").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val points = dataSnapshot.child("pointName").getValue(String::class.java)
                    val distance = dataSnapshot.child("distance").getValue(String::class.java)
                    if (points == pointName){
                        flag = true
                        postLiveData.value = distance!!.toInt()
                    }
                }
                if (!flag){
                    postLiveData.value = 0
                }

            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return postLiveData
    }
    fun BusesTiming(busTime: String,pointName : String) : MutableLiveData<Buses> {
        val database = firebaseDatabase.getReference(busTime)
        val postLiveData = MutableLiveData<Buses>()
        database.orderByChild("busNo").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val points = dataSnapshot.child("points").getValue(String::class.java)
                    val buses = dataSnapshot.getValue(Buses::class.java)
                    if (points != null) {
                        if (points.contains(pointName)){
                            postLiveData!!.value = buses
                            break
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return postLiveData
    }

    fun getUserPaymentDetail() : MutableLiveData<ArrayList<PaymentHistory>> {
        val database = firebaseDatabase.reference.child("Users")
            .child(auth.currentUser!!.uid).child("paymentHistory")
        val postLiveData = MutableLiveData<ArrayList<PaymentHistory>>()
        val list = ArrayList<PaymentHistory>()

        database.orderByChild("date").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (dataSnapshot in snapshot.children) {
                    val payment = dataSnapshot.getValue(PaymentHistory::class.java)

                    if (payment != null) {
                        list.add(payment)
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Collections.sort(list, Comparator<PaymentHistory> { o1, o2 -> o1.date!!.compareTo(o2.date!!)  }.reversed())
                }
                postLiveData.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return postLiveData
    }

    fun getTripDetail() : MutableLiveData<ArrayList<Trip>> {
        val database = firebaseDatabase.reference.child("Trip")
        val postLiveData = MutableLiveData<ArrayList<Trip>>()
        val list = ArrayList<Trip>()

        database.orderByChild("date").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (dataSnapshot in snapshot.children) {
                    val payment = dataSnapshot.getValue(Trip::class.java)

                    if (payment != null) {
                        list.add(payment)
                    }
                }
                postLiveData.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return postLiveData
    }

    fun updateUserName(name : String){
        firebaseDatabase.reference.child("Users").
        child(auth.currentUser!!.uid).child("userName").setValue(name)
    }

    fun updatePhoneNumber (phone : String){
        firebaseDatabase.reference.child("Users").
        child(auth.currentUser!!.uid).child("contact").setValue(phone)
    }

    fun getLocation() : MutableLiveData<ArrayList<Marker>> {
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Location");
        val postLiveData = MutableLiveData<ArrayList<Marker>>()
        val list = ArrayList<Marker>()
        database.orderByChild("title").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val marker = dataSnapshot.getValue(Marker::class.java)

                    if (marker != null) {
                        list.add(marker)
                        marker.id =dataSnapshot.key
                    }
                }
                postLiveData.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return postLiveData
    }
    
}