package com.example.miiweb
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AboutScreen() {
    val uriHandler = LocalUriHandler.current
    val annotatedString = buildAnnotatedString {
       /* append("www.miiweb.com.ar ")*/
        pushStringAnnotation(tag = "URL", annotation = "https://www.miiweb.com.ar") // Reemplaza con tu URL
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append("www.miiweb.com.ar")
        }
        pop()
        append(".")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.logob), // Reemplaza con tu imagen
            contentDescription = "www.miiweb.com.ar",
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Gemini AI Book Capture ",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Versión 1.0.0",
            style= MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Esta aplicación fue creada por Gonzalo G.M y GEMINI IA.  E-mail:gomezmarinero@gmail.com / El Bolsón Río Negro Argentina 2024",
            style = MaterialTheme.typography.bodyMedium
        )

        ClickableText(
            text = annotatedString,
            style = MaterialTheme.typography.bodyMedium,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        // Abre la URL en un navegador o realizaotra acción
                        // Por ejemplo, usando la clase UriHandler de Jetpack Compose

                        uriHandler.openUri(annotation.item)
                    }
            }
        )
    }
}