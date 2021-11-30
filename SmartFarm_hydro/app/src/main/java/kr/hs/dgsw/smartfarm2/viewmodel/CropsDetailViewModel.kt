package kr.hs.dgsw.smartfarm2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kr.hs.dgsw.smartfarm2.network.model.response.Crop
import kr.hs.dgsw.smartfarm2.network.model.response.Response
import kr.hs.dgsw.smartfarm2.repository.CropsRepository
import kr.hs.dgsw.smartfarm2.util.SingleLiveEvent

class CropsDetailViewModel : ViewModel() {

    val disposable = CompositeDisposable()
    val repository = CropsRepository()

    val cropSuccess = MutableLiveData<Response<Crop>>()
    val cropError = MutableLiveData<Throwable>()

    val backBtn = SingleLiveEvent<Any>()

    fun getCrop() {
        addDisposable(repository.getCurrentCrops(), object : DisposableSingleObserver<Response<Crop>>() {
            override fun onSuccess(t: Response<Crop>) {
                cropSuccess.value = t
            }

            override fun onError(e: Throwable) {
                cropError.value = e
            }

        })
    }

    fun addDisposable(single: Single<*>, observer: DisposableSingleObserver<*>) {
        disposable.add(
            single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer as SingleObserver<Any>) as Disposable
        )
    }

    fun onClickBackBtn(){
        backBtn.call()
    }
}