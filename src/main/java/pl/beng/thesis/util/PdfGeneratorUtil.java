package pl.beng.thesis.util;

import com.lowagie.text.DocumentException;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Component
public class PdfGeneratorUtil {

    /**
     * Create pdf file from given html template
     *
     * @param htmlContent html template
     * @return array of bytes
     * @throws DocumentException if error occurred during pdf generation
     */
    public byte[] createPdf(String htmlContent) throws DocumentException {

        try(ByteOutputStream byteOutputStream = new ByteOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(byteOutputStream);

            return byteOutputStream.getBytes();
        }
    }
}
