import android.app.Application
import androidx.lifecycle.LiveData
import com.mati_tech.masa_english_learning.db.AppDatabase
import com.mati_tech.masa_english_learning.db.MaterialDao
import com.mati_tech.masa_english_learning.models.Material
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MaterialRepository(application: Application?) {
    private val materialDao: MaterialDao
    private val allMaterials: LiveData<List<Material>>
    private val coroutineScope = CoroutineScope(Dispatchers.IO) // Background thread

    init {
        val database = AppDatabase.getInstance(application!!)
        materialDao = database!!.materialDao()
        allMaterials = materialDao.allMaterials
    }

    fun insert(material: Material) {
        coroutineScope.launch {
            materialDao.insert(material)
        }
    }

    fun delete(material: Material) {
        coroutineScope.launch {
            materialDao.delete(material)
        }
    }

    fun getAllMaterials(): LiveData<List<Material>> {
        return allMaterials
    }
}
