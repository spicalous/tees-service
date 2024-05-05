package com.example.teesservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@RestController
public class TeesController {

   @ExceptionHandler({ HttpMessageNotReadableException.class, BadRequestException.class })
   public ResponseEntity<String> handleBadRequestException(Exception e) {
      log.info("Exception msg={}", e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
   }

   @ExceptionHandler
   public ResponseEntity<String> handleException(Exception e) {
      log.error("Exception", e);
      return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
   }

   @PostMapping("/api/tee")
   public ResponseEntity<byte[]> postSvgToPng(HttpServletRequest request,
                                              @NotBlank @RequestBody String svg) throws TranscoderException {
      log.info("Request remoteAddr={}", request.getRemoteAddr());
      ByteArrayInputStream inputStream = new ByteArrayInputStream(svg.getBytes(UTF_8));
      TranscoderInput input = new TranscoderInput(inputStream);

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      TranscoderOutput output = new TranscoderOutput(outputStream);

      PNGTranscoder transcoder = new PNGTranscoder();

      try {
         transcoder.transcode(input, output);
      } catch (TranscoderException e) {
         if (e.getException() != null) {
            Exception cause = e.getException();
            if (cause.getMessage() != null && cause.getMessage().contains("Content is not allowed in prolog")) {
               throw new BadRequestException("Could not parse xml", cause);
            }
         }
         throw e;
      }
      return ResponseEntity.ok(outputStream.toByteArray());
   }

   private static class BadRequestException extends RuntimeException {
      public BadRequestException(String message, Throwable cause) {
         super(message, cause);
      }
   }
}
