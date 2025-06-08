
public class LSBSteganography {

    // Convert a string message into binary form
    public static String stringToBinary(String message) {
        StringBuilder binary = new StringBuilder();
        for (char character : message.toCharArray()) {
            // Convert each character to an 8-bit binary string
            binary.append(String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0'));
        }
        return binary.toString();
    }

    // Embed the binary message into the least significant bit of the audio data
    public static void embedMessage(byte[] audioData, String binaryMessage) {
        int messageIndex = 0;

        System.out.println("Starting LSB embedding...");
        int headerSize = 44;

        for (int i = headerSize; i < audioData.length; i++) {
            if (messageIndex < binaryMessage.length()) {
                // Get the current byte from the audio data
                int originalByte = audioData[i] & 0xFF;

                // Show the original byte in binary form
                String originalByteBinary = String.format("%8s", Integer.toBinaryString(originalByte)).replace(' ', '0');
                System.out.println("Original byte: " + originalByteBinary);

                // Clear the least significant bit (LSB) of the byte
                int modifiedByte = originalByte & 0xFE;

                // Show the byte with cleared LSB
                String clearedLSBByteBinary = String.format("%8s", Integer.toBinaryString(modifiedByte)).replace(' ', '0');
                System.out.println("Byte after clearing LSB: " + clearedLSBByteBinary);

                // Set the LSB to the corresponding bit from the message
                modifiedByte = modifiedByte | (binaryMessage.charAt(messageIndex) - '0');

                // Show the byte after setting the new LSB
                String modifiedByteBinary = String.format("%8s", Integer.toBinaryString(modifiedByte)).replace(' ', '0');
                System.out.println("Byte after setting new LSB: " + modifiedByteBinary);

                // Update the audio data with the modified byte
                audioData[i] = (byte) modifiedByte;

                // Move to the next bit in the message
                messageIndex++;
            } else {
                break;
            }
        }

        System.out.println("Message embedding completed.");
    }
}
