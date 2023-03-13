import java.io.File
import java.io.FileInputStream
import java.util.Properties

private const val KeystoreFileName = "keystore.properties"
private const val StoreFileProperty = "RELEASE_STORE_FILE"
private const val StorePasswordProperty = "RELEASE_STORE_PASSWORD"
private const val KeyAliasProperty = "RELEASE_STORE_KEY_ALIAS"
private const val KeyPasswordProperty = "RELEASE_STORE_KEY_PASSWORD"

class Keystore {
    private val keystoreFile = File(KeystoreFileName)
    val isAutoSigning = keystoreFile.exists()
    private val properties = Properties()

    val storeFile: String
    val storePassword: String
    val keyAlias: String
    val keyPassword: String

    init {
        if (isAutoSigning) {
            properties.load((FileInputStream(keystoreFile)))
            storeFile = properties[StoreFileProperty] as String
            storePassword = properties[StorePasswordProperty] as String
            keyAlias = properties[KeyAliasProperty] as String
            keyPassword = properties[KeyPasswordProperty] as String
        } else {
            storeFile = ""
            storePassword = ""
            keyAlias = ""
            keyPassword = ""
        }
    }
}