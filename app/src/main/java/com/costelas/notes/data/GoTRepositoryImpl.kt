import com.costelas.notes.common.models.GoTQuote
import com.costelas.notes.domain.IGoTApiService
import com.costelas.notes.domain.IGoTRepository
import javax.inject.Inject

class GoTRepositoryImpl @Inject constructor(
    private val apiService: IGoTApiService
) : IGoTRepository {
    override suspend fun fetchRandomQuote(): GoTQuote {
        return apiService.getRandomQuote()
    }
}