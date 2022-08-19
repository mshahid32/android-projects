package repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import models.Marker
import javax.inject.Inject

class MainRepository @Inject constructor() {

    fun getLocation() : MutableLiveData<ArrayList<Marker>> {
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Location");
        val postLiveData = MutableLiveData<ArrayList<Marker>>()
        val list = ArrayList<models.Marker>()
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