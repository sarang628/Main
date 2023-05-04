package com.example.screen_main

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.screen_main.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * [FragmentMainBinding]
 */
@AndroidEntryPoint
class MainFragment : Fragment() {

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
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)

        //init LoginManager
        //loginManager.onCreate(requireActivity() as AppCompatActivity)

        subScribeUI(binding)

        var badge = binding.bottomNavigationView.getOrCreateBadge(R.id.timeLineFragment)
        badge.number = 99

        var badge1 = binding.bottomNavigationView.getOrCreateBadge(R.id.alarmFragment)
        badge1.isVisible = true

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