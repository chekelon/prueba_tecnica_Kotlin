# Ofertas de Trabajo · 

Aplicación Android nativa en Kotlin que **descarga un feed XML de ofertas de trabajo, lo parsea manualmente y lo muestra en una lista** con una pantalla de detalle para cada oferta.

El objetivo de la prueba es demostrar el consumo de un servicio HTTP que responde en XML (sin librerías de terceros para el parseo), el uso del patrón **MVVM** y la navegación básica entre pantallas.

## Características

- Descarga el feed de empleos desde `https://people-pro.com/xml-feed/indeed` mediante `HttpURLConnection`.
- Parseo del XML con `XmlPullParser`, extrayendo por cada `<job>`: título, fecha, número de referencia, URL, empresa, ciudad, país y descripción.
- Navegación inferior (**Bottom Navigation**) con dos secciones: **Home** y **Jobs**.
- Lista de ofertas con `RecyclerView` que muestra el título y la empresa.
- Indicador de carga (`ProgressBar`) mientras se obtiene la información.
- Pantalla de detalle (`DetailsActivity`) con título, empresa y descripción desplazable. El objeto `Job` viaja entre pantallas como `Parcelable`.

## Tecnologías

| Área | Detalle |
|------|---------|
| Lenguaje | Kotlin 1.7.10 |
| Arquitectura | MVVM (`ViewModel` + `LiveData`) |
| Asincronía | Corrutinas (`viewModelScope`, `Dispatchers.IO`) |
| UI | Vistas XML, ViewBinding, Material Components, ConstraintLayout |
| Red | `HttpURLConnection` |
| Parseo | `XmlPullParser` / `XmlPullParserFactory` |
| Modelo | `@Parcelize` + anotaciones `@SerializedName` de Gson |
| Inyección de dependencias | Anotaciones de Hilt 2.35.1 (ver nota abajo) |
| Build | Android Gradle Plugin 7.2.2, Gradle 7.3.3 |

## Estructura del proyecto

```
app/src/main/java/com/example/parsearxml2/
├── JobsApp.kt                  # Application (@HiltAndroidApp)
├── data/api/
│   └── JobApiClient.kt         # Petición HTTP y parseo del XML
├── model/
│   ├── Job.kt                  # Modelo de oferta (Parcelable)
│   ├── JobsService.kt          # Ejecuta la llamada en Dispatchers.IO
│   └── JobsRepository.kt       # Repositorio que expone los datos
├── viewmodel/
│   └── JobsViewModel.kt        # Estado de carga y lista de empleos (LiveData)
└── view/
    ├── MainActivity.kt         # Contenedor con Bottom Navigation
    ├── HomeFragment.kt         # Pantalla de inicio
    ├── JobsFragment.kt         # Lista de ofertas
    ├── JobsAdapter.kt          # Adapter del RecyclerView
    └── DetailsActivity.kt      # Detalle de una oferta
```

**Flujo de datos:** `JobsFragment` pide los datos al `JobsViewModel`, que llama a `JobsRepository` → `JobsService` → `JobApiClient`. Este descarga el XML, lo parsea a una lista de `Job` y el resultado regresa al fragmento a través de `LiveData`, que actualiza la lista y oculta el indicador de carga.

## Requisitos

- Android Studio compatible con AGP 7.2.2 (por ejemplo, Chipmunk o superior)
- JDK 11 o superior para ejecutar Gradle
- Android SDK 33 instalado
- Dispositivo o emulador con **Android 6.0 (API 23)** o superior
- Conexión a internet (permiso `INTERNET` ya declarado en el manifiesto)

## Instalación y ejecución

1. Clona el repositorio:

   ```bash
   git clone https://github.com/chekelon/prueba_tecnica_Kotlin.git
   ```

2. Abre la carpeta en Android Studio.
3. Espera a que Gradle sincronice (**File → Sync Project with Gradle Files**).
4. Ejecuta la app en un emulador o dispositivo físico con el botón **Run**.
5. En la barra inferior, entra a **Jobs** para ver las ofertas y pulsa **Detalles** en cualquiera de ellas.

## Configuración

| Parámetro | Valor |
|-----------|-------|
| `applicationId` | `com.example.parsearxml2` |
| `minSdk` | 23 |
| `targetSdk` | 30 |
| `compileSdk` | 33 |

## Notas y posibles mejoras

- **Hilt:** el código incluye las anotaciones (`@HiltAndroidApp`, `@AndroidEntryPoint`, `@HiltViewModel`), pero el plugin de Hilt y el procesador `kapt` no están habilitados en Gradle, y `JobsApp` no está registrada en el `AndroidManifest.xml`. Además, el `ViewModel` crea sus propias dependencias directamente. Para usar Hilt de verdad hay que aplicar el plugin, activar el compilador e inyectar el repositorio por constructor.
- **Campo `state`:** el modelo `Job` lo contempla, pero el parser todavía no lo lee.
- **Parser:** las variables de cada oferta se declaran fuera del ciclo, por lo que si una oferta no trae alguna etiqueta podría heredar el valor de la anterior. Conviene reiniciarlas por cada `<job>`.
- **Manejo de errores:** no hay captura de excepciones de red ni estado de error en la interfaz.
- **Tecnologías actualizables:** migrar a Retrofit con un convertidor XML, actualizar `LiveData` a `StateFlow` y usar `Parcelable` con la API moderna de `getParcelableExtra`.
- Agregar capturas de pantalla y pruebas unitarias para el parser.

## Autor

**chekelon** · [GitHub](https://github.com/chekelon)

## Licencia

Este proyecto aún no define una licencia. Agrega un archivo `LICENSE` si quieres que otros puedan reutilizarlo.
