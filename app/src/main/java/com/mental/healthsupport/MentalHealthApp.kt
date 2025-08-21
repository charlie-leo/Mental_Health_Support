package com.mental.healthsupport

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * Created by Charles Raj I on 28/07/25
 * @project Mental Health Support
 * @author Charles Raj
 */

class MentalHealthApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this) // Initialize the firebase or starting the firebase
    }


}