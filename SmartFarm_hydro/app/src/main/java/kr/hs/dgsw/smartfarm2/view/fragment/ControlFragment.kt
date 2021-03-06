package kr.hs.dgsw.smartfarm2.view.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.smartfarm2.R
import kr.hs.dgsw.smartfarm2.databinding.FragmentControllBinding
import kr.hs.dgsw.smartfarm2.viewmodel.ControlViewModel

class ControlFragment : Fragment() {

    lateinit var binding: FragmentControllBinding
    lateinit var viewModel: ControlViewModel
    //var aniBounce = AnimationUtils.loadAnimation(requireActivity().applicationContext, R.anim.button_animation)
    //var aniBounce = AnimationUtils.loadAnimation(layoutInflater.context, R.anim.button_animation)

    private var isChoiceLed: Int = -1
    private var isChoicePump: Int = -1
    private var isChoicePump2: Int = -1
    private var isChoiceFan: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_controll, container, false)
        viewModel = ViewModelProvider(this).get(ControlViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.fm = this
        binding.executePendingBindings()
        observeViewModel()

    }

    private fun observeViewModel() {
        binding.refreshControlView2.setOnRefreshListener {
            viewModel.getAllStatus()
            binding.refreshControlView2.isRefreshing = false
        }

        with(viewModel) {
            ledOffImage.observe(this@ControlFragment, Observer {
                ledSetChoice(0)
                //binding.ledOffImg.startAnimation(aniBounce)
            })

            ledOnImage.observe(this@ControlFragment, Observer {
                ledSetChoice(1)
                //binding.ledOnImg.startAnimation(aniBounce)
            })

            ledBtn.observe(this@ControlFragment, Observer {
                if (isChoiceLed == -1) {
                    Toast.makeText(requireActivity(), "????????? ?????? ??????????????????.", Toast.LENGTH_SHORT).show()
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

            getAllResult.observe(viewLifecycleOwner, Observer {
                if (it.ledStatus.value) {
                    binding.ledImgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_led_on))
                } else {
                    binding.ledImgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_led_off))
                }

                if (it.waterStatus1.value) {
                    binding.pumpImgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_pump_on))
                } else {
                    binding.pumpImgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_pump_off))
                }

                if (it.waterStatus2.value) {
                    binding.pumpImgStatus2.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_pump_on))
                } else {
                    binding.pumpImgStatus2.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_pump_off))
                }

                if (it.fanStatus.value) {
                    binding.fanImgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_fan_on))
                } else {
                    binding.fanImgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_fan_off))
                }
            })

            pumpOffImage.observe(this@ControlFragment, Observer {
                pumpSetChoice(0)
                //binding.pumpOffImg.startAnimation(aniBounce)
            })

            pumpOnImage.observe(this@ControlFragment, Observer {
                pumpSetChoice(1)
                //binding.pumpOnImg.startAnimation(aniBounce)
            })

            pumpOffImage2.observe(this@ControlFragment, Observer {
                pumpSetChoice2(0)
                //binding.pumpOffImg2.startAnimation(aniBounce)
            })

            pumpOnImage2.observe(this@ControlFragment, Observer {
                pumpSetChoice2(1)
                //binding.pumpOnImg2.startAnimation(aniBounce)
            })

            pumpbtn.observe(this@ControlFragment, Observer {
                if (isChoicePump == -1) {
                    Toast.makeText(requireActivity(), "????????? ?????? ??????????????????.", Toast.LENGTH_SHORT).show()
                } else {
                    val params = HashMap<String?, Boolean?>()
                    val device = HashMap<String?, Int?>()

                    device["device"] = 1

                    if (isChoicePump == 0) {
                        params["status"] = false
                    } else if (isChoicePump == 1) {
                        params["status"] = true
                    }
                    viewModel.controlPump(params, device)
                }

            })

            fanOffImage.observe(this@ControlFragment, Observer {
                fanSetChoice(0)
                //binding.ledOffImg.startAnimation(aniBounce)
            })

            fanOnImage.observe(this@ControlFragment, Observer {
                fanSetChoice(1)
                //binding.ledOnImg.startAnimation(aniBounce)
            })

            ledBtn.observe(this@ControlFragment, Observer {
                if (isChoiceLed == -1) {
                    Toast.makeText(requireActivity(), "????????? ?????? ??????????????????.", Toast.LENGTH_SHORT).show()
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

            controlErrorEvent.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireActivity(), "?????? ??? ????????? ??????????????????.", Toast.LENGTH_SHORT).show()
            })

            ledControlResult.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireActivity(), "?????? ??????!", Toast.LENGTH_SHORT).show()
                for (i in 0..3) {
                    Handler().postDelayed({
                        viewModel.getLedState()
                    }, 1500)
                }

                Toast.makeText(requireActivity(), "?????? ??????!", Toast.LENGTH_SHORT).show()
                binding.ledOnImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_led_on))
                binding.ledOffImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_led_off))
            })

            pumpControlResult.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireActivity(), "?????? ??????!", Toast.LENGTH_SHORT).show()
                for (i in 0..3) {
                    Handler().postDelayed({
                        viewModel.getPumpState()
                    }, 1500)
                }

                binding.pumpOnImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_on))
                binding.pumpOffImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_off))

                binding.pumpOnImg2.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_on))
                binding.pumpOffImg2.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_off))
            })

            fanControlResult.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireActivity(), "?????? ??????!", Toast.LENGTH_SHORT).show()
                for (i in 0..3) {
                    Handler().postDelayed({
                        viewModel.getFanState()
                    }, 1500)
                }

                binding.fanOnImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_fan_on))
                binding.fanOffImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_fan_off))
            })

            getLedResult.observe(viewLifecycleOwner, Observer {
                if (it.ledStatus.value) {
                    binding.ledImgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_led_on))
                } else {
                    binding.ledImgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_led_off))
                }
            })

            getPumpResult.observe(viewLifecycleOwner, Observer {
                if (it.waterPumpStatus1.value) {
                    binding.pumpImgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_pump_on))
                } else {
                    binding.pumpImgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_pump_off))
                }

                if (it.waterPumpStatus2.value) {
                    binding.pumpImgStatus2.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_pump_on))
                } else {
                    binding.pumpImgStatus.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_pump_off))
                }
            })

            getFanResult.observe(viewLifecycleOwner, Observer {
                if (it.fanStatus.value) {
                    binding.fanImgStatus.setImageDrawable(ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_fan_on))
                } else {
                    binding.fanImgStatus.setImageDrawable(ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_fan_off))
                }
            })

            stateErrorEvent.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun ledSetChoice(choice: Int) {
        when (choice) {
            0 -> {
                isChoiceLed = choice
                binding.ledOffImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_led_check))

                binding.ledOnImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_led_on))
                ledState()
            }
            1 -> {
                isChoiceLed = choice
                binding.ledOnImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_led_check))
                binding.ledOffImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_led_off))
                ledState()
            }
        }
    }

    private fun pumpSetChoice(choice: Int) {
        when (choice) {
            0 -> {
                isChoicePump = choice
                binding.pumpOffImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_check))
                binding.pumpOnImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_on))
                waterPumpState()
            }
            1 -> {
                isChoicePump = choice
                binding.pumpOnImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_check))
                binding.pumpOffImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_off))
                waterPumpState()
            }
        }
    }

    private fun pumpSetChoice2(choice: Int) {
        when (choice) {
            0 -> {
                isChoicePump2 = choice
                binding.pumpOffImg2.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_check))
                binding.pumpOnImg2.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_on))
                waterPumpState2()
            }
            1 -> {
                isChoicePump2 = choice
                binding.pumpOnImg2.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_check))
                binding.pumpOffImg2.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_pump_off))
                waterPumpState2()
            }
        }
    }

    private fun fanSetChoice(choice: Int) {
        when (choice) {
            0 -> {
                isChoiceFan = choice
                binding.fanOffImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_fan_check))
                binding.fanOnImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_fan_on))
                fanState()
            }
            1 -> {
                isChoiceFan = choice
                binding.fanOnImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_fan_check))
                binding.fanOffImg.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_fan_off))
                fanState()
            }
        }
    }

    fun ledState() {
        val params = HashMap<String?, Boolean?>()

        if (isChoiceLed == 0) {
            params["status"] = false
        } else if (isChoiceLed == 1) {
            params["status"] = true
        }

        viewModel.controlLed(params)
    }

    fun waterPumpState() {
        val status = HashMap<String?, Boolean?>()
        val device = HashMap<String?, Int?>()

        device["device"] = 1

        if (isChoicePump == 0) {
            status["status"] = false
        } else if (isChoicePump == 1) {
            status["status"] = true
        }
        viewModel.controlPump(status, device)
    }

    fun waterPumpState2() {
        val status = HashMap<String?, Boolean?>()
        val device = HashMap<String?, Int?>()

        device["device"] = 2

        if (isChoicePump2 == 0) {
            status["status"] = false
        } else if (isChoicePump2 == 1) {
            status["status"] = true
        }
        viewModel.controlPump(status, device)
    }

    fun fanState() {
        val params = HashMap<String?, Boolean?>()

        if (isChoiceFan == 0) {
            params["status"] = false
        } else if (isChoiceFan == 1) {
            params["status"] = true
        }
        viewModel.controlFan(params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::binding.isInitialized) binding.unbind()
    }
}