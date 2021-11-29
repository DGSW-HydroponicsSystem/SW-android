package kr.hs.dgsw.smartfarm2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kr.hs.dgsw.smartfarm2.network.model.response.GetAllSensor
import kr.hs.dgsw.smartfarm2.network.model.response.Response
import kr.hs.dgsw.smartfarm2.repository.SensorRepository
import kr.hs.dgsw.smartfarm2.util.SingleLiveEvent

class MainViewModel : ViewModel() {
    val repository = SensorRepository()
    val disposable = CompositeDisposable()

    val ledOffImage = SingleLiveEvent<Any>()
    val ledOnImage = SingleLiveEvent<Any>()
    val ledControlBtn = SingleLiveEvent<Any>()

    val pumpOffImage = SingleLiveEvent<Any>()
    val pumpOnImage = SingleLiveEvent<Any>()
    val pumpControlBtn = SingleLiveEvent<Any>()

    val getSensorSuccess = MutableLiveData<Response<GetAllSensor>>()
    val getSensorError = MutableLiveData<Throwable>()

    val ledControlResult = MutableLiveData<Boolean>()
    val pumpControlResult = MutableLiveData<Boolean>()
    val controlErrorEvent = MutableLiveData<Throwable>()

    val modeSwitch = MutableLiveData<Boolean>()

    val cropsTipBtn = SingleLiveEvent<Any>()

    init {
        getAllSensor()
    }

    fun onClickCropsTipBtn(){
        cropsTipBtn.call()
    }

    fun getAllSensor() {
        addDisposable(repository.getAllSensor(), object : DisposableSingleObserver<Response<GetAllSensor>>() {
            override fun onSuccess(t: Response<GetAllSensor>) {
                getSensorSuccess.value = t
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                getSensorError.value = e
            }

        })
    }

    fun controlLed(params: HashMap<String?, Boolean?>) {
        addDisposable(repository.controlLed(params), object : DisposableSingleObserver<Boolean>() {
            override fun onSuccess(t: Boolean) {
                ledControlResult.value = t
            }

            override fun onError(e: Throwable) {
                controlErrorEvent.value = e
                e.printStackTrace()
            }
        })
    }

    fun controlPump(params: HashMap<String?, Boolean?>) {
        Log.e("e", "ㅇㄻㄴㄹㅇ ${params["status"]}")
        addDisposable(repository.controlPump(params), object : DisposableSingleObserver<Boolean>() {
            override fun onSuccess(t: Boolean) {
                pumpControlResult.value = t
            }

            override fun onError(e: Throwable) {
                controlErrorEvent.value = e
                e.printStackTrace()
            }
        })
    }

    fun onClickLedOffImage() {
        ledOffImage.call()
    }

    fun onClickLedOnImage() {
        ledOnImage.call()
    }

    fun onClickLedControlbtn() {
        ledControlBtn.call()
    }

    fun onClickPumpOnImage() {
        pumpOnImage.call()
    }

    fun onClickPumpOffImage() {
        pumpOffImage.call()
    }

    fun onClickPumpControlbtn() {
        pumpControlBtn.call()
    }

    fun addDisposable(single: Single<*>, observer: DisposableSingleObserver<*>) {
        disposable.add(
            single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer as SingleObserver<Any>) as Disposable
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}