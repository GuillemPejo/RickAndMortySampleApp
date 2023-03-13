import com.android.build.gradle.internal.dsl.ProductFlavor
import java.io.FileInputStream
import java.util.Properties

private const val LocalPropertiesFileName = "local.properties"

class Properties {

    private val properties = Properties()

    fun load(flavor: ProductFlavor, flavorData: ConfigField.Flavor) {
        properties.load(FileInputStream(LocalPropertiesFileName))
        flavor.loadFields(flavorData)
    }

    private fun ProductFlavor.loadFields(flavorData: ConfigField.Flavor) {
    }

    private fun Properties.getSafe(name: String): String =
        this[name] as? String ?: "\"\""
}