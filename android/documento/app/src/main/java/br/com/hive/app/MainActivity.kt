package br.com.hive.app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.hive.captura_documento.HiveCapturaDocumento

class MainActivity : AppCompatActivity() {

    private lateinit var capturaDocumento: HiveCapturaDocumento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Capturar exceções não tratadas
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("HiveApp", "Exceção não tratada: ${throwable.message}", throwable)
        }

        // Inicializando a biblioteca com uma API Key
        val apiKey =
            BuildConfig.HIVE_SDK_API_KEY // Substitua pela sua chave válida em gradle.properties
        capturaDocumento = HiveCapturaDocumento(this, apiKey)

        // Configurando o botão de captura
        val startCaptureButton: Button = findViewById(R.id.startCaptureButton)
        startCaptureButton.setOnClickListener { onStartCaptureButtonClick(it) }
    }

    private fun onStartCaptureButtonClick(view: View) {
        Log.d("HiveApp", "Iniciando captura de documento...")

        val apiKey =
            BuildConfig.HIVE_SDK_API_KEY // Substitua pela sua chave válida em gradle.properties

        val baseUrl = br.com.hive.captura_documento.BuildConfig.BASE_URL
        val currentFlavor = br.com.hive.captura_documento.BuildConfig.BUILD_TYPE
        val sdkVersion = br.com.hive.captura_documento.BuildConfig.SDK_VERSION

        Log.d(
            "HiveApp",
            "Base URL: $baseUrl, API Key: $apiKey, Flavor: $currentFlavor, SDK Version: $sdkVersion"
        )

        capturaDocumento.startDocumentCapture(this, false, object : HiveCapturaDocumento.CaptureCallback {
            override fun onSuccess(documentos: Array<HiveCapturaDocumento.DocumentoResult>) {
                Toast.makeText(
                    this@MainActivity,
                    "Captura realizada com sucesso!",
                    Toast.LENGTH_LONG
                ).show()

                // Exibindo os documentos capturados no Logcat
                for (documento in documentos) {
                    Log.d("HiveApp", "Documento ID: ${documento.id}")
                    Log.d("HiveApp", "Tipo: ${documento.tipo}")
                    Log.d("HiveApp", "Score: ${documento.score}")
                    Log.d("HiveApp", "Imagem: ${documento.imagem}")
                    documento.boundingBox?.let { box ->
                        Log.d(
                            "HiveApp",
                            "Bounding Box: x=${box.x}, y=${box.y}, width=${box.width}, height=${box.height}"
                        )
                    }
                }
            }

            override fun onError(erro: HiveCapturaDocumento.CustomError) {
                Toast.makeText(
                    this@MainActivity,
                    "Erro durante a captura: ${erro.descricao}",
                    Toast.LENGTH_LONG
                ).show()

                // Exibindo o erro no Logcat
                Log.e(
                    "HiveApp",
                    "Erro na captura: Código=${erro.codigo}, Descrição=${erro.descricao}, ID=${erro.id}"
                )
            }
        })
    }
}