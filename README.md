# Miiweb - Gemini AI Book Capture 📚✨

https://www.miiweb.com.ar/gemini-ai-book-capture/

<img src="https://www.miiweb.com.ar/img/logo.webp">

**Digitaliza tu biblioteca personal sin esfuerzo con Miiweb "Gemini IA book Capture".** Saca una foto a la portada de un libro y deja que la inteligencia artificial de Gemini haga el resto. Extrae, edita y gestiona los datos de tus libros como nunca antes.

## ✨ Características Principales

*   📸 **Captura Inteligente:** Usa la cámara de tu móvil para capturar la portada de cualquier libro. Nuestra IA se encarga de analizar la imagen al instante.
*   🧠 **Extracción con IA Gemini:** La potente IA de Google Gemini extrae automáticamente el título, autor, editorial y otros datos relevantes directamente de la foto de la portada.
*   ✏️ **Edición Fácil y Rápida:** ¿La IA no acertó algún dato? No hay problema. Edita y corrige cualquier campo de forma intuitiva antes de guardar el libro en tu colección.
*   💾 **Guarda y Organiza:** Almacena todos tus libros digitalizados directamente en la aplicación.
*   📄 **Exporta tu Colección:** Exporta fácilmente tu biblioteca a un archivo CSV para usarla en hojas de cálculo, realizar copias de seguridad u otras aplicaciones.

## 📸 Capturas de Pantalla:
<img src="./app/src/main/res/drawable/screen2.jpeg" height="410px"    width="200px" alt="Captura de Portada - G.IA Book Capture">
<img src="./app/src/main/res/drawable/screen3.jpeg" height="410px"    width="200px" alt="Captura de Portada - G.IA Book Capture">
<img src="./app/src/main/res/drawable/screen.jpeg" height="410px"    width="200px" alt="Captura de Portada - G.IA Book Capture">


## 🛠️ Tecnologías Utilizadas

*   **Lenguaje:** Kotlin
*   **UI:** Jetpack Compose
*   **Inteligencia Artificial:** Google Gemini API - Google AI Studio Key
*   **Procesamiento de Imágenes:**
*   **Arquitectura:** MVVM (Model-View-ViewModel) con Android Jetpack ViewModel
*   **Base de Datos Local:** Room Persistence Library
*   **Navegación:** Jetpack Navigation Component (Compose)
*   **Asincronía:** Kotlin Coroutines & Flow
*   **Inyección de Dependencias:** (Hilt/Dagger, Koin - si aplica)
*   **Permisos:** (Mencionar librerías si usas alguna para simplificar, ej: `accompanist-permissions`)

## 🚀 Cómo Empezar

### Requisitos Previos

*   Android Studio Android Studio Narwhal Feature Drop o Vcode
*   JDK [18]
*   ADK Android [14] 
*   Una clave API de Google Gemini:
    *   Deberás obtener tu propia clave API desde [Google AI Studio](https://aistudio.google.com/app/apikey) (o el servicio correspondiente que estés usando para Gemini).

### Instalación

1.  **Clona el repositorio:**
2.  **Navega a la carpeta de la aplicación:**
3.  **Ejecuta Android Studio e importa el proyecto:**
4.  Configura la API Key de Google AI Studio:
    Abre el archivo local.properties (si no existe, créalo) y añade tu clave API de la siguiente manera:
    API_KEY="TU_CLAVE_API_AQUÍ"    
    Luego, en el archivo build.gradle (Module), asegúrate de que la clave se esté leyendo correctamente, por ejemplo, en un bloque buildConfigField.
6.  Ejecuta la aplicación:
    Conecta un dispositivo Android o un emulador. Haz clic en el botón de Run ( ▶️ ) en la barra de herramientas de Android Studio. La aplicación se instalará y se iniciará automáticamente.

