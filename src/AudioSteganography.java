import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioSteganography {
    public static void embedMessageInAudio(File audioFile, String owner, String title, String email, String message, String password, String ownership, String license, String embeddedBiometricEncoding) {
        try {
            FileInputStream fileInputStream = new FileInputStream(audioFile);
            byte[] audioData = new byte[(int) audioFile.length()];
            fileInputStream.read(audioData);
            fileInputStream.close();

            // Add metadata
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String metadata = "Owner:" + owner + "|Title:" + title + "|Email:" + email +
                    "|License:" + license + "|Ownership:" + ownership +
                    "|Biometric:" + embeddedBiometricEncoding +
                    "|Watermark:" + message + "|Timestamp:" + timestamp;


            // Encrypt metadata
            String encryptedMetadata = AESUtil.encrypt(metadata, password);

            // Generate hash of encrypted data
            String metadataHash = HashUtil.sha256(encryptedMetadata);

            // Combine encrypted metadata and hash with a separator
            String finalDataToEmbed = encryptedMetadata + "::" + metadataHash;

            // Convert to binary and add null terminator
            String binaryMessage = LSBSteganography.stringToBinary(finalDataToEmbed) + "00000000";

            // Embed binary message in audio
            LSBSteganography.embedMessage(audioData, binaryMessage);

            // Save new file with modified audio
            String originalFileName = audioFile.getName();
            int dotIndex = originalFileName.lastIndexOf(".");
            String baseName = (dotIndex == -1) ? originalFileName : originalFileName.substring(0, dotIndex);
            String extension = (dotIndex == -1) ? "" : originalFileName.substring(dotIndex);
            String newFileName = baseName + "_with_message" + extension;

            File outputAudioFile = new File(audioFile.getParent(), newFileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outputAudioFile);
            fileOutputStream.write(audioData);
            fileOutputStream.close();

            System.out.println("Message embedded successfully in " + outputAudioFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
