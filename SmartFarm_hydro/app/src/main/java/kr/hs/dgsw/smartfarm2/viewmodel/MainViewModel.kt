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
import kr.hs.dgsw.smartfarm2.repository.MainRepository
import kr.hs.dgsw.smartfarm2.util.SingleLiveEvent

class MainViewModel : ViewModel() {
    val repository = MainRepository()
    val disposable = CompositeDisposable()

    val ledOffImage = SingleLiveEvent<Any>()
    val ledOnImage = SingleLiveEvent<Any>()
    val ledControlBtn = SingleLiveEvent<Any>()

    val pumpOffImage = SingleLiveEvent<Any>()
    val pumpOnImage = SingleLiveEvent<Any>()
    val pumpControlBtn = SingleLiveEvent<Any>()

    val humidityValue = MutableLiveData<Int>()
    val tempValue = MutableLiveData<Int>()
    val ledStatus = MutableLiveData<Boolean>()
    val pumpStatus = MutableLiveData<Boolean>()
    val onErrorEvent = MutableLiveData<Throwable>()

    val ledControlResult = MutableLiveData<Boolean>()
    val pumpControlResult = MutableLiveData<Boolean>()
    val controlErrorEvent = MutableLiveData<Throwable>()

    val modeSwitch = MutableLiveData<Boolean>()

    init {
        humidityValue.value = -1
        tempValue.value = -1

        getAllSensor()
    }

    fun getAllSensor() {
        addDisposable(repository.getAllSensor(), object : DisposableSingleObserver<GetAllSensor>() {
            override fun onSuccess(t: GetAllSensor) {
                humidityValue.value = t.humidity.value
                tempValue.value = t.temp.value
                ledStatus.value = t.ledStatus.status
                pumpStatus.value = t.waterStatus.status
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                onErrorEvent.value = e
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