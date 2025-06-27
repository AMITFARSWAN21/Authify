package com.authify.service;

import com.authify.entity.Certificate;
import com.authify.io.CertificateRequest;
import com.authify.repository.CertificateRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository repository;

    public Certificate generateCertificate(CertificateRequest dto) {
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // 1. Add Decorative Border
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect = new Rectangle(40, 40, PageSize.A4.getWidth() - 40, PageSize.A4.getHeight() - 40);
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(4);
            rect.setBorderColor(BaseColor.GRAY);
            canvas.rectangle(rect);

            // 2. Add Watermark
            try (InputStream bgStream = new ClassPathResource("image/bg-logo.png").getInputStream()) {
                Image bg = Image.getInstance(bgStream.readAllBytes());
                bg.setAbsolutePosition(150, 300);
                bg.scaleToFit(300, 300);
                bg.setTransparency(new int[]{0x0F, 0x10});
                document.add(bg);
            } catch (Exception ignored) {
            }

            // 3. Fonts
            Font headingFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 28, Font.BOLD, BaseColor.DARK_GRAY);
            Font nameFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 22, Font.BOLD, BaseColor.BLUE);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.DARK_GRAY);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);


            // Logo
            try (InputStream logoStream = new ClassPathResource("image/college_logo.png").getInputStream()) {
                Image logo = Image.getInstance(logoStream.readAllBytes());
                logo.scaleToFit(150, 150);
                logo.setAlignment(Image.ALIGN_CENTER);
                document.add(logo);
            }


            // 4. Title
            Paragraph title = new Paragraph("Certificate of " + dto.getCertificateType().toUpperCase(), headingFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // 5. Student Name
            Paragraph student = new Paragraph(dto.getStudentName().toUpperCase(), nameFont);
            student.setAlignment(Element.ALIGN_CENTER);
            student.setSpacingBefore(10);
            student.setSpacingAfter(20);
            document.add(student);

            // 6. Certificate Body
            Paragraph body = new Paragraph();
            body.setAlignment(Element.ALIGN_JUSTIFIED);
            body.setFont(bodyFont);
            body.setSpacingBefore(20);
            body.setSpacingAfter(40);

// Certificate wording
            String paragraphText = String.format(
                    "This is to certify that %s has successfully completed the course entitled \"%s\" conducted by %s at %s. " +
                            "The course spanned a total duration of %s and was completed on %s. " +
                            "During this course, %s has demonstrated commendable performance and has been awarded a grade of %s, " +
                            "earning a total of %s credits.\n\n" +
                            "Remarks: %s\n\n" +
                            "Issued on: %s.",
                    dto.getStudentName(),
                    dto.getCourseName(),
                    dto.getInstructorName(),
                    dto.getInstitutionName(),
                    dto.getDuration(),
                    dto.getCompletionDate(),
                    dto.getStudentName(),
                    dto.getGrade(),
                    dto.getCreditsEarned(),
                    dto.getDescription(),
                    dto.getIssueDate()
            );

            body.add(new Paragraph(paragraphText, bodyFont));
            document.add(body);


            // 7. Signature
            try (InputStream sigStream = new ClassPathResource("image/signature.png").getInputStream()) {
                Image signature = Image.getInstance(sigStream.readAllBytes());
                signature.scaleAbsolute(120, 50);
                signature.setAbsolutePosition(100, 100);
                document.add(signature);

                Paragraph signText = new Paragraph("Authorized Signature", bodyFont);
                signText.setAlignment(Element.ALIGN_LEFT);
                signText.setIndentationLeft(100);
                signText.setSpacingBefore(60);
                document.add(signText);
            }

            // 8. QR Code
            String qrContent = dto.getStudentName() + " | " + dto.getCourseName() + " | Issued: " + dto.getIssueDate();
            BarcodeQRCode qrCode = new BarcodeQRCode(qrContent, 150, 150, null);
            Image qrImage = qrCode.getImage();
            qrImage.setAbsolutePosition(450, 100);
            qrImage.scaleAbsolute(80, 80);
            document.add(qrImage);

            // 9. Footer
            Paragraph footer = new Paragraph(
                    "This is a system-generated certificate. Verification available on request.",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY)
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(100);
            document.add(footer);


            document.close();

            Certificate cert = Certificate.builder()
                    .studentName(dto.getStudentName())
                    .courseName(dto.getCourseName())
                    .instructorName(dto.getInstructorName())
                    .institutionName(dto.getInstitutionName())
                    .completionDate(dto.getCompletionDate())
                    .duration(dto.getDuration())
                    .grade(dto.getGrade())
                    .creditsEarned(dto.getCreditsEarned())
                    .certificateType(dto.getCertificateType())
                    .description(dto.getDescription())
                    .issueDate(LocalDate.parse(dto.getIssueDate()))
                    .pdfContent(out.toByteArray())
                    .build();

            return repository.save(cert);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate certificate", e);
        }
    }

    public byte[] getCertificatePdf(Long id) {
        Certificate cert = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
        return cert.getPdfContent();
    }
}
