# Secure Audio Watermarking System 🎵🔐

# Secure Audio Watermarking System 🎵🔐

This project provides a secure and offline method for protecting audio content ownership using invisible watermarking and two-factor authentication (2FA). Unlike traditional copyright protection systems that rely on cloud storage or centralized databases, this system embeds all ownership and licensing data directly into the audio file itself.

## Features
🔒 Secure Metadata Embedding
Users enter the following metadata into the application:

Owner Name

Email Address

Audio Title

License Type (e.g., CC BY, All Rights Reserved)

Ownership Type (Individual / Organization)

Custom Watermark Message

Password for encryption

This data is encrypted using AES (Advanced Encryption Standard) to ensure confidentiality.

🧬 Biometric-Based 2FA
The application captures the user’s face using a webcam and generates a 128-dimensional facial encoding using Python’s face_recognition library. This biometric signature is embedded alongside the encrypted metadata.

🎧 Invisible Embedding with LSB
The encrypted metadata (including the biometric data) is converted into binary and hidden inside the audio file using Least Significant Bit (LSB) steganography. This process maintains the audio quality while making the watermark invisible to users.

🔓 Decryption with Two-Factor Authentication
To extract and verify the watermark:

The user must enter the correct password to decrypt the metadata.

The system captures a live facial image and compares it to the embedded biometric encoding.

Only if both password and face match, the metadata is displayed.

📦 No Database Required
All data — including face encoding — is securely embedded within the audio file itself. No external server or database is used. The audio file becomes a self-contained proof of ownership.

## Technologies Used
- Java (Swing, AES, I/O)
- Python (OpenCV, face_recognition)
- GitHub for version control

## How to Run
1. Run the Java GUI
2. Select an audio file and enter metadata
3. Verify face and embed
4. For decryption: password + face match is required

## Setup
- `pip install -r requirements.txt`
- Java 8 or later
