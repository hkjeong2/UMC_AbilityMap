package com.abilitymap

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.abilitymap.databinding.ActivityOnboardingBinding

class OnboardingActivity : FragmentActivity() {

    private lateinit var binding : ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
        initViewPager()
    }

    private fun initClickListener(){
        binding.tvSkipOnboarding.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvSkipOnboarding.setOnClickListener {
            startActivity(Intent(this, ChooseOptionActivity::class.java))
            finish()
        }
    }

    private fun initViewPager(){
        //뷰페이저 초기화 및 어뎁터 연결
        val onBoardingAdapter = OnboardingVPAdapter(this)

        binding.vpViewpagerOnboarding.adapter = onBoardingAdapter
        binding.indicatorOnboarding.setViewPager(binding.vpViewpagerOnboarding)
        binding.indicatorOnboarding.createIndicators(4,0)
        binding.vpViewpagerOnboarding.setCurrentItem(0)

        binding.tvSelectButtonOnboarding.setOnClickListener {
            //마지막 페이지에서 다음 버튼 클릭 시 화면 전환, 이전 페이지일 시 다음 페이지로 이동
            if (binding.vpViewpagerOnboarding.currentItem==3){
                startActivity(Intent(this, ChooseOptionActivity::class.java))
                finish()
            }else{
                binding.vpViewpagerOnboarding.setCurrentItem(binding.vpViewpagerOnboarding.currentItem+1)
            }
        }

        //뷰페이저 스크롤 기능 구현 및 indicator와 페이지 동기화
        binding.vpViewpagerOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0){
                    binding.vpViewpagerOnboarding.setCurrentItem(position)
                }

            }
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.indicatorOnboarding.animatePageSelected(position)
            }
        })



    }

}