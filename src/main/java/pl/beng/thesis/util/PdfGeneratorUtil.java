package pl.beng.thesis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pl.beng.thesis.exception.DocumentCreationException;

import java.io.ByteArrayOutputStream;

@Component
public class PdfGeneratorUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Create pdf file from given html template
     *
     * @param htmlContent html template
     * @return array of bytes
     * @throws DocumentCreationException if error occurred during pdf generation
     */
    public byte[] createPdf(String htmlContent) {

        try(ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(byteOutputStream);

            return byteOutputStream.toByteArray();
        } catch (Exception exception) {
            logger.error("Unable to create PDF! + " + exception);
            throw new DocumentCreationException(exception);
        }
    }
}
