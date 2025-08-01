package com.example.vitalizeme.view.main.plans

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.vitalizeme.R
import com.example.vitalizeme.databinding.ActivityPlansBinding
import com.example.vitalizeme.view.main.PlanFragment

class PlansActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPlansBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityPlansBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragment_type = intent.getStringExtra("plan_type")

        val fragment = when(fragment_type){
            "Cardio" -> CardioFragment()
            "yoga" -> YogaFragment()
            "wt" -> WTFragment()
            "stretching" -> StretchingFragment()
            "mediation" -> MeditationFragment()
            else -> PlanFragment()
        }
        val fragmentManager = supportFragmentManager.beginTransaction().replace(R.id.PlansNavContainer,fragment).commit()

    }

}