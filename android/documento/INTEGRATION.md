# Guia de Integração - Hive SDK Documento Android

Este guia descreve os passos necessários para configurar e utilizar o SDK de Captura de Documentos da Hive em seu projeto Android.

## 1. Pré-requisitos

Para utilizar o SDK, você precisará:
- **Username do GitHub** (para acesso ao repositório Maven)
- **Personal Access Token (PAT) do GitHub** (com permissão de leitura de pacotes)
- **API Key do SDK**
- **Application ID autorizado**: O `applicationId` do seu projeto deve ser o mesmo cadastrado na sua licença Hive (ex: `br.com.hive.app`).

## 2. Configuração do Projeto

### 2.1. Configurar `gradle.properties`

Adicione as suas credenciais no arquivo `gradle.properties` na raiz do seu projeto:

```properties
HIVE_SDK_USERNAME=seu_usuario_github
HIVE_SDK_PASSWORD=seu_token_github
HIVE_SDK_API_KEY=sua_chave_api_da_hive
```

### 2.2. Configurar Repositório em `settings.gradle.kts`

Adicione o repositório Maven da Hive no seu arquivo `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://maven.pkg.github.com/hiveplace/hive-sdk-documento-android")
            credentials {
                username = settings.providers.gradleProperty("HIVE_SDK_USERNAME").getOrElse("")
                password = settings.providers.gradleProperty("HIVE_SDK_PASSWORD").getOrElse("")
            }
        }
    }
}
```

### 2.3. Validar Application ID em `app/build.gradle.kts`

Certifique-se de que o `applicationId` corresponde ao pacote licenciado:

```kotlin
android {
    defaultConfig {
        applicationId = "br.com.hive.app" // Deve ser o pacote autorizado
        // ...
    }
}
```

### 2.4. Adicionar Dependência

Adicione a biblioteca às dependências do seu módulo `app`:

```kotlin
dependencies {
    // Substitua pela versão mais recente disponível
    implementation("br.com.hive.captura_documento:hivecapturadocumento:1.3.40-rc.8")
    
    // Dependências auxiliares necessárias
    implementation("androidx.camera:camera-view:1.1.0")
    implementation("com.google.android.material:material:1.4.0")
}
```

## 3. Implementação da Captura

### 3.1. Inicialização e Chamada

```kotlin
import br.com.hive.captura_documento.HiveCapturaDocumento

class MainActivity : AppCompatActivity() {

    private lateinit var capturaDocumento: HiveCapturaDocumento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializa com a API Key
        val apiKey = BuildConfig.HIVE_SDK_API_KEY
        capturaDocumento = HiveCapturaDocumento(this, apiKey)
        
        btnCapturar.setOnClickListener {
            iniciarCaptura()
        }
    }

    private fun iniciarCaptura() {
        capturaDocumento.startDocumentCapture(this, false, object : HiveCapturaDocumento.CaptureCallback {
            override fun onSuccess(documentos: Array<HiveCapturaDocumento.DocumentoResult>) {
                // Sucesso na captura
            }

            override fun onError(erro: HiveCapturaDocumento.CustomError) {
                // Erro (Ex: Licença inválida para este package name)
            }
        })
    }
}
```

## 4. Configurações Adicionais

### 4.1. Build Types e Fallbacks

Se utilizar variantes customizadas, configure o `matchingFallbacks`:

```kotlin
android {
    buildTypes {
        debug {
            matchingFallbacks.addAll(listOf("preprod", "homol", "release"))
        }
    }
}
```

### 4.2. Conflitos de Bibliotecas Nativas

Adicione ao bloco `android` para evitar duplicidade de arquivos `.so`:

```kotlin
android {
    packaging {
        jniLibs {
            pickFirsts += setOf(
                "**/libc++_shared.so",
                "**/libmediapipe_tasks_vision_jni.so",
                "**/libimage_processing_util_jni.so"
            )
        }
    }
}
```
