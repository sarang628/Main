package com.example.screen_main

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.screen_main.databinding.FragmentMainBinding
import com.example.torang_core.util.ITorangLocationManager
import com.example.torang_core.util.Logger
import com.sarang.toringlogin.LoginManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * [FragmentMainBinding]
 */
@AndroidEntryPoint
class MainFragment : Fragment() {
    @Inject
    lateinit var loginManager: LoginManager

    @Inject lateinit var locationManager : ITorangLocationManager

    //메인 뷰모델
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this

        // Sets BottomNavigation
        val navHostFragment = childFragmentManager.findFragmentById(R.id.fc) as NavHostFragment
        //val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)

        //init LoginManager
        loginManager.onCreate(requireActivity() as AppCompatActivity)

        subScribeUI(binding)

        Logger.d(locationManager.getLastLatitude())
        Logger.d(locationManager.getLastLongitude())

        locationManager.requestLocation()

        return binding.root
    }

    private fun subScribeUI(binding: FragmentMainBinding) {
        mainViewModel.clickMenu
            .observe(
                viewLifecycleOwner,
                { binding.drawerLayout.openDrawer(Gravity.LEFT) })
    }

    fun onBackPressed() {
        if (!mainViewModel.backPressedFlag.value!!) {
            //Snackbar.make(mBinding.fc, "뒤로가기를 누르면 종료됩니다.", Snackbar.LENGTH_SHORT).show()
            mainViewModel.onBackPressed()
        } else
            requireActivity().onBackPressed()
    }
}