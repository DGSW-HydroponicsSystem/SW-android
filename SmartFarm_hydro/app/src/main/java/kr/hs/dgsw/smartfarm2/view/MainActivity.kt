package kr.hs.dgsw.smartfarm2.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.smartfarm2.R
import kr.hs.dgsw.smartfarm2.databinding.ActivityMainBinding
import kr.hs.dgsw.smartfarm2.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    private var isChoiceLed: Int = -1
    private var isChoicePump: Int = -1

    lateinit var aniBounce: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.refreshView.setOnRefreshListener {
            viewModel.getAllSensor()
            binding.refreshView.isRefreshing = false
        }

        getCurrentMode()
        observeViewModel()
    }

    private fun getCurrentMode() {
        val currentMode = AppCompatDelegate.getDefaultNightMode()

        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            viewModel.modeSwitch.value = true
        } else if (currentMode == AppCompatDelegate.MODE_NIGHT_NO) {
            viewModel.modeSwitch.value = false
        }
    }

    private fun observeViewModel() {
        aniBounce = AnimationUtils.loadAnimation(this, R.anim.button_animation)
        with(viewModel) {
            modeSwitch.observe(this@MainActivity, Observer {
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

            getSensorSuccess.observe(this@MainActivity, Observer {
                binding.humidityProgress1.progress = it.data.humidity1.value
                binding.humidityTvProgress1.text = "${it.data.humidity1.value}%"
                binding.humidityProgress2.progress = it.data.humidity2.value
                binding.humidityTvProgress2.text = "${it.data.humidity2.value}%"

                binding.tempProgress1.progress = it.data.temp1.value
                binding.tempTvProgress1.text = "${it.data.temp1.value}℃"
                binding.tempProgress2.progress = it.data.temp2.value
                binding.tempTvProgress2.text = "${it.data.temp2.value}℃"

                if(it.data.ledStatus.status){
                    binding.ledImgStatus.setImageDrawable(ContextCompat.getDrawable(this@MainActivity,
                        R.drawable.ic_led_on))
                } else {
                    binding.ledImgStatus.setImageDrawable(ContextCompat.getDrawable(this@MainActivity,
                        R.drawable.ic_led_off))
                }

                if(it.data.waterPumpStatus.status){
                    binding.pumpImgStatus.setImageDrawable(ContextCompat.getDrawable(this@MainActivity,
                        R.drawable.ic_pump_on))
                } else {
                    binding.pumpImgStatus.setImageDrawable(ContextCompat.getDrawable(this@MainActivity,
                        R.drawable.ic_pump_off))
                }


                Toast.makeText(this@MainActivity, "서버로부터 값을 전달받지 못했습니다.", Toast.LENGTH_SHORT).show()
            })

            getSensorError.observe(this@MainActivity, Observer {
                binding.humidityProgress1.progress = 0
                binding.humidityTvProgress1.text = "${0}%"
                binding.humidityProgress2.progress = 0
                binding.humidityTvProgress2.text = "${0}%"

                binding.tempProgress1.progress = 0
                binding.tempTvProgress1.text = "${0}℃"
                binding.tempProgress2.progress = 0
                binding.tempTvProgress2.text = "${0}℃"

                binding.ledImgStatus.setImageDrawable(ContextCompat.getDrawable(this@MainActivity,
                    R.drawable.ic_led_off))
                binding.pumpImgStatus.setImageDrawable(ContextCompat.getDrawable(this@MainActivity,
                    R.drawable.ic_pump_off))

                Toast.makeText(this@MainActivity, "서버로부터 값을 전달받지 못했습니다.", Toast.LENGTH_SHORT).show()
            })

            ledOffImage.observe(this@MainActivity, Observer {
                ledSetChoice(0)
                binding.ledOffImg.startAnimation(aniBounce)
            })

            ledOnImage.observe(this@MainActivity, Observer {
                ledSetChoice(1)
                binding.ledOnImg.startAnimation(aniBounce)
            })

            ledControlBtn.observe(this@MainActivity, Observer {
                if (isChoiceLed == -1) {
                    Toast.makeText(this@MainActivity, "상태를 먼저 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    val params = HashMap<String?, Boolean?>()

                    if (isChoiceLed == 0) {
                        params["status"] = false
                    } else if (isChoiceLed == 1) {
                        params["status"] = true
                    }

                    viewModel.controlLed(params)
                }
            })

            pumpOffImage.observe(this@MainActivity, Observer {
                pumpSetChoice(0)
                binding.pumpOffImg.startAnimation(aniBounce)
            })

            pumpOnImage.observe(this@MainActivity, Observer {
                pumpSetChoice(1)
                binding.pumpOnImg.startAnimation(aniBounce)
            })

            pumpControlBtn.observe(this@MainActivity, Observer {
                if (isChoicePump == -1) {
                    Toast.makeText(this@MainActivity, "상태를 먼저 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    val params = HashMap<String?, Boolean?>()

                    if (isChoicePump == 0) {
                        params["status"] = false
                    } else if (isChoicePump == 1) {
                        params["status"] = true
                    }
                    viewModel.controlPump(params)
                }

            })

            controlErrorEvent.observe(this@MainActivity, Observer {
                Toast.makeText(this@MainActivity, "요청 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            })

            ledControlResult.observe(this@MainActivity, Observer {
                Toast.makeText(this@MainActivity, "전달 성공!", Toast.LENGTH_SHORT).show()
                for (i in 0..3) {
                    Handler().postDelayed({
                        viewModel.getAllSensor()
                    }, 1500)
                }
            })

            pumpControlResult.observe(this@MainActivity, Observer {
                Toast.makeText(this@MainActivity, "전달 성공!", Toast.LENGTH_SHORT).show()
                for (i in 0..3) {
                    Handler().postDelayed({
                        viewModel.getAllSensor()
                    }, 1500)
                }
            })

            cropsTipBtn.observe(this@MainActivity, Observer {
                val intent = Intent(this@MainActivity, CropsActivity::class.java)
                startActivity(intent)
            })
        }
    }

    private fun ledSetChoice(choice: Int) {
        when (choice) {
            0 -> {
                isChoiceLed = choice
                binding.ledOffImg.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_led_check))

                binding.ledOnImg.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_led_on))
            }
            1 -> {
                isChoiceLed = choice
                binding.ledOnImg.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_led_check))
                binding.ledOffImg.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_led_off))
            }
        }
    }

    private fun pumpSetChoice(choice: Int) {
        when (choice) {
            0 -> {
                isChoicePump = choice
                binding.pumpOffImg.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_pump_check))
                binding.pumpOnImg.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_pump_on))
            }
            1 -> {
                isChoicePump = choice
                binding.pumpOnImg.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_pump_check))
                binding.pumpOffImg.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_pump_off))
            }
        }
    }
}