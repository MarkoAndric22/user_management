package springBootSecurity.com.example.springBootSecurity.service.service_api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import springBootSecurity.com.example.springBootSecurity.configuration.AppProperties;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StorageService {

  private final AppProperties appProperties;

  /**
   * Store image.
   *
   * @param file MultipartFile
   * @return String
   */
  public String storeImage(MultipartFile file) {
    String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    String fileExtension = StringUtils.getFilenameExtension(originalFileName);
    String newFileName =
      "img_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "." + fileExtension;
    try {
      Path filePath = Paths.get(appProperties.getUploadImagePath()).resolve(newFileName).normalize();
      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      ProcessBuilder processBuilder = new ProcessBuilder();
      processBuilder.command("bash", "-c", "sudo chmod 777 " + filePath);
      processBuilder.start();

      return appProperties.getDomainName() + "/images/profile/" + newFileName;
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  /**
   * Get image name from URL.
   *
   * @param urlPath String
   * @return String
   */
  public String imgFromUrl(String urlPath) {
    try {
      URL url = new URL(urlPath);
      String path = url.getPath();
      return Paths.get(path).getFileName().toString();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Delete image.
   *
   * @param imgPath String
   * @return Boolean
   */
  public Boolean deleteImage(String imgPath) {
    File file = new File(imgPath);
    if (file.exists()) {
      return file.delete();
    }
    return false;
  }
}
