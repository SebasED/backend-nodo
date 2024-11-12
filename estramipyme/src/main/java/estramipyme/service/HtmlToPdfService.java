package estramipyme.service;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class HtmlToPdfService {
    // Método para convertir HTML a PDF
    public byte[] convertirHtmlAPdf(String htmlContent) throws IOException {
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        // Convertir HTML a PDF usando iText
        HtmlConverter.convertToPdf(htmlContent, pdfOutputStream);

        // Retornar el contenido en formato PDF como un arreglo de bytes
        return pdfOutputStream.toByteArray();
    }
}

