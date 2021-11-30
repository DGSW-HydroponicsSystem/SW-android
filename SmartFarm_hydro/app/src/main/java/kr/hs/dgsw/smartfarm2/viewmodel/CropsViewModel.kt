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
import kr.hs.dgsw.smartfarm2.network.model.response.C
import kr.hs.dgsw.smartfarm2.network.model.response.Crops
import kr.hs.dgsw.smartfarm2.network.model.response.Response
import kr.hs.dgsw.smartfarm2.repository.CropsRepository
import kr.hs.dgsw.smartfarm2.util.SingleLiveEvent

class CropsViewModel: ViewModel() {
    private val disposable = CompositeDisposable()
    private val repository = CropsRepository()

    val cropsSuccess = MutableLiveData<C>()
    val cropsError = MutableLiveData<Throwable>()

    val updateSuccess = MutableLiveData<Response<Any>>()
    val updateError = MutableLiveData<Throwable>()

    val backBtn = SingleLiveEvent<Any>()

    fun getAllCrops(){
        addDisposable(repository.getAllCrops(), object : DisposableSingleObserver<Response<C>>(){
            override fun onSuccess(t: Response<C>) {
                cropsSuccess.value = t.data
                Log.e("adsf", "${t.data}")
            }

            override fun onError(e: Throwable) {
                cropsError.value = e
                Log.e("adsf", "${e}")
            }

        })
    }

    fun updateCrops(pk: Int){
        addDisposable(repository.updateCrops(pk), object : DisposableSingleObserver<Response<Any>>(){
            override fun onSuccess(t: Response<Any>) {
                updateSuccess.value = t
            }

            override fun onError(e: Throwable) {
                updateError.value = e
            }

        })
    }

    fun onClickBackBtn(){
        backBtn.call()
    }

    fun addDisposable(single: Single<*>, observer: DisposableSingleObserver<*>) {
        disposable.add(
            single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer as SingleObserver<Any>) as Disposable
        )
    }
}