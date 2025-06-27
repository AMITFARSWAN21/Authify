package com.authify.controller;

import com.authify.entity.Certificate;
import com.authify.io.CertificateRequest;
import com.authify.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/certificates")
public class CertificateController {

    @Autowired
    private CertificateService service;

    @PostMapping("/generate")
    public ResponseEntity<Certificate> generateCertificate(@RequestBody CertificateRequest request) {
        Certificate certificate = service.generateCertificate(request);
        return ResponseEntity.ok(certificate);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id) {
        byte[] pdfData = service.getCertificatePdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("certificate_" + id + ".pdf")
                .build());

        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
    }
}
