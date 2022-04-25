package kr.hs.dgsw.smartfarm2.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.smartfarm2.R
import kr.hs.dgsw.smartfarm2.databinding.ActivityMainBinding
import kr.hs.dgsw.smartfarm2.view.fragment.ControlFragment
import kr.hs.dgsw.smartfarm2.view.fragment.StateFragment
import kr.hs.dgsw.smartfarm2.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        observeViewModel()
        moveFragment()
    }

    private fun moveFragment() {
        val stateFragment = StateFragment()
        val controlFragment = ControlFragment()

        supportFragmentManager.beginTransaction().add(R.id.container, stateFragment).commit()

        binding.buttonControl.setOnClickListener {
            binding.buttonState.background =
                ContextCompat.getDrawable(this, R.drawable.button_off_background)
            binding.buttonState.setTextColor(ContextCompat.getColor(this, R.color.button_off_text))

            binding.buttonControl.background =
                ContextCompat.getDrawable(this, R.drawable.button_on_background)
            binding.buttonControl.setTextColor(ContextCompat.getColor(this, R.color.button_on_text))

            supportFragmentManager.beginTransaction().replace(R.id.container, controlFragment)
                .commit()
        }

        binding.buttonState.setOnClickListener {
            binding.buttonControl.background =
                ContextCompat.getDrawable(this, R.drawable.button_off_background)
            binding.buttonControl.setTextColor(ContextCompat.getColor(this,
                R.color.button_off_text))

            binding.buttonState.background =
                ContextCompat.getDrawable(this, R.drawable.button_on_background)
            binding.buttonState.setTextColor(ContextCompat.getColor(this, R.color.button_on_text))

            supportFragmentManager.beginTransaction().replace(R.id.container, stateFragment)
                .commit()
        }
    }

    fun onClickCameraBtn() {
        Toast.makeText(this, "준비 중입니다.", Toast.LENGTH_SHORT).show()
    }

    private fun observeViewModel() {
        with(viewModel) {
            cropsTipBtn.observe(this@MainActivity, Observer {
                val intent = Intent(this@MainActivity, CropsActivity::class.java)
                startActivity(intent)
            })

            cameraBtn.observe(this@MainActivity, Observer {
                onClickCameraBtn()
            })
        }
    }
}