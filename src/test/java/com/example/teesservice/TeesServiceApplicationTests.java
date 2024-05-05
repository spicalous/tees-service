package com.example.teesservice;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(
   webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class TeesServiceApplicationTests {

   @Autowired
   private TestRestTemplate testRestTemplate;

   @Test
   void nullRequest() {
      ResponseEntity<String> response = testRestTemplate.postForEntity("/api/tee", null, String.class);
      String expectedBody = "Required request body is missing: public org.springframework.http.ResponseEntity<byte[]> com.example.teesservice.controllers.TeesController.postSvgToPng(jakarta.servlet.http.HttpServletRequest,java.lang.String) throws org.apache.batik.transcoder.TranscoderException";
      assertResponse(BAD_REQUEST, expectedBody, response);
   }

   @Test
   void emptyRequest() {
      ResponseEntity<String> response = testRestTemplate.postForEntity("/api/tee", "", String.class);
      String expectedBody = "Required request body is missing: public org.springframework.http.ResponseEntity<byte[]> com.example.teesservice.controllers.TeesController.postSvgToPng(jakarta.servlet.http.HttpServletRequest,java.lang.String) throws org.apache.batik.transcoder.TranscoderException";
      assertResponse(BAD_REQUEST, expectedBody, response);
   }

   @Test
   void notSvg() {
      ResponseEntity<String> response = testRestTemplate.postForEntity("/api/tee", "not-svg", String.class);
      assertResponse(BAD_REQUEST, "Could not parse xml", response);
   }

   @Test
   void svg() {
      ResponseEntity<byte[]> response = testRestTemplate.postForEntity("/api/tee", "<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\"></svg>", byte[].class);
      byte[] expectedBody = new byte[]{ -119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 1, -112, 0, 0, 1, -112, 8, 6, 0, 0, 0, -128, -65, 54, -52, 0, 0, 0, 32, 99, 72, 82, 77, 0, 0, 122, 38, 0, 0, -128, -124, 0, 0, -6, 0, 0, 0, -128, -24, 0, 0, 117, 48, 0, 0, -22, 96, 0, 0, 58, -104, 0, 0, 23, 112, -100, -70, 81, 60, 0, 0, 0, 4, 103, 65, 77, 65, 0, 0, -79, -114, 124, -5, 81, -109, 0, 0, 0, 1, 115, 82, 71, 66, 0, -82, -50, 28, -23, 0, 0, 0, 6, 98, 75, 71, 68, 0, -1, 0, -1, 0, -1, -96, -67, -89, -109, 0, 0, 0, 9, 112, 72, 89, 115, 0, 0, 14, -60, 0, 0, 14, -60, 1, -107, 43, 14, 27, 0, 0, 2, -125, 73, 68, 65, 84, 120, -38, -19, -63, -127, 0, 0, 0, 0, -61, -96, -7, 83, 95, -31, 0, 85, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -64, 111, -58, 23, 0, 1, -80, -33, 118, 91, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126 };
      assertResponse(OK, expectedBody, response);
   }

   @Test
   void test() throws IOException {
      ResponseEntity<byte[]> response = testRestTemplate.postForEntity("/api/tee", "<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\"></svg>", byte[].class);
      String fileStr = fileToString("src/test/resources/test-output/test");
      assertResponse(OK, decodeBase64(fileStr), response);
   }

   private <T> void assertResponse(HttpStatus expectedHttpStatus, T expectedBody, ResponseEntity<T> actual) {
      assertEquals(expectedHttpStatus, actual.getStatusCode());
      if (expectedBody instanceof byte[] expectedBodyByteArray && actual.getBody() instanceof byte[] actualBody) {
         assertArrayEquals(expectedBodyByteArray, actualBody);
      } else {
         assertEquals(expectedBody, actual.getBody());
      }
   }

   public static String fileToString(String path) throws IOException {
      return Files.readString(Path.of(path));
   }
}
