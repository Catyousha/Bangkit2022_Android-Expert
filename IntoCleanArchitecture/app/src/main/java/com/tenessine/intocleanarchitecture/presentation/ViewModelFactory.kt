import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tenessine.intocleanarchitecture.di.Injection
import com.tenessine.intocleanarchitecture.domain.MessageUseCase
import com.tenessine.intocleanarchitecture.presentation.MainViewModel

class MainViewModelFactory(
    // interaksi ke layer usecase melalui interface
    private var messageUseCase: MessageUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(messageUseCase) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: MainViewModelFactory? = null

        fun getInstance(): MainViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MainViewModelFactory(Injection.provideUseCase())
            }
    }
}