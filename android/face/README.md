# Hive Integration Guide - Android

Este repositório contém um exemplo prático de como integrar o SDK de Captura de Documento da Hive em um projeto Android.

## 🚀 Como preparar para teste

Siga os passos abaixo para configurar o ambiente e rodar o projeto em seu dispositivo ou emulador.

### 1. Configuração de Credenciais e Licença

**Importante:** Todas as credenciais necessárias (incluindo as de acesso ao Maven/GitHub Packages) são fornecidas pela equipe Hive. Você não deve usar seu Token pessoal do GitHub para baixar as dependências.

1. Localize o arquivo `gradle.properties` na raiz do projeto.
2. Certifique-se de que as seguintes chaves estejam preenchidas com os valores **fornecidos pela Hive**:

```properties
HIVE_SDK_USERNAME=usuario_fornecido_pela_hive
HIVE_SDK_PASSWORD=token_fornecido_pela_hive
HIVE_SDK_API_KEY=sua_api_key_hive
```

3. **Verificação do Application ID**: O SDK valida se o pacote da aplicação é o mesmo que foi licenciado. Certifique-se de que o `applicationId` no seu `app/build.gradle.kts` seja exatamente o contratado (neste exemplo: `br.com.hive.app`).

### 2. Seleção do Ambiente (Build Variants)

O SDK da Hive possui diferentes ambientes. O projeto já está configurado para suportar:

- **Debug/Release:** Ambiente de produção.
- **Homol:** Ambiente de homologação.
- **Preprod:** Ambiente de pré-produção.

Para trocar o ambiente:
1. No Android Studio, abra a aba **Build Variants** (geralmente no canto inferior esquerdo).
2. Na coluna `Active Build Variant` do módulo `:app`, selecione a variante desejada (ex: `homol`).
3. O Gradle fará a sincronização automática para buscar a versão correspondente do SDK.

### 3. Instalação e Execução

1. Conecte um dispositivo Android físico ou inicie um emulador.
2. Clique no botão **Run** (ícone de play verde) no Android Studio.
3. Se houver erro de compilação relacionado a dependências, execute `File > Invalidate Caches / Restart`.

## 🛠️ Solução de Problemas Comuns

### Erro de "Could not find br.com.hive..."
Verifique se as credenciais `HIVE_SDK_USERNAME` e `HIVE_SDK_PASSWORD` no `gradle.properties` estão exatamente como fornecidas pela Hive. Não use suas credenciais pessoais do GitHub.

### Erro de "Licença Inválida" ou erro na inicialização
Verifique se o seu `applicationId` no arquivo `app/build.gradle.kts` corresponde ao pacote autorizado pela Hive e se a `HIVE_SDK_API_KEY` está correta.

### Erro de bibliotecas nativas (.so)
O arquivo `app/build.gradle.kts` já possui a configuração `pickFirsts` para resolver conflitos de bibliotecas como `libc++_shared.so` e `mediapipe`.

---

Para detalhes técnicos de como implementar do zero em seu próprio app, veja o arquivo [INTEGRATION.md](./INTEGRATION.md).
