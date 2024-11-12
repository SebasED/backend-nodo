package estramipyme.controller;
import estramipyme.service.HtmlToPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pdf")
public class PdfController {
    @Autowired
    private HtmlToPdfService htmlToPdfService;

    // Endpoint para recibir el HTML y generar un PDF
    @PostMapping("/generar")
    public ResponseEntity<byte[]> generarPdfDesdeHtml(@RequestBody String htmlContent) {
        try {
            // Convertir el HTML a PDF
            byte[] pdfBytes = htmlToPdfService.convertirHtmlAPdf(htmlContent);

            // Configurar las cabeceras para que se descargue como un archivo PDF
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=documento.pdf");
            headers.add("Content-Type", "application/pdf");

            // Devolver el archivo PDF como respuesta
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.status(500).body(null);
        }
    }
}
