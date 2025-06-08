import face_recognition
import cv2
import sys
import numpy as np
import time

# Step 1: Read the saved biometric encoding from stdin
encoding_str = sys.stdin.read().strip()
if not encoding_str or encoding_str == "NoFace":
    print("InvalidEncoding")
    sys.exit()

try:
    saved_encoding = np.array([float(x) for x in encoding_str.split(",")])
except:
    print("InvalidFormat")
    sys.exit()

# Step 2: Open webcam
cap = cv2.VideoCapture(0)
if not cap.isOpened():
    print("CameraError")
    sys.exit()

print("Align your face - verification in 3 seconds...")
start_time = time.time()
frame = None

while True:
    ret, frame = cap.read()
    if not ret:
        print("CaptureFailed")
        break

    cv2.imshow("Face Verification", frame)

    if time.time() - start_time > 3:
        break

    if cv2.waitKey(1) & 0xFF == 27:  # ESC to cancel
        print("UserCancelled")
        break

cap.release()
cv2.destroyAllWindows()

if frame is None:
    print("NoCapture")
    sys.exit()

# Step 3: Encode the new face
try:
    current_encoding = face_recognition.face_encodings(frame)[0]
except:
    print("NoFace")
    sys.exit()

# Optional: Print encoding lengths for debugging
print("Saved encoding length:", len(saved_encoding))
print("Captured encoding length:", len(current_encoding))

# Step 4: Compare encodings
match = face_recognition.compare_faces([saved_encoding], current_encoding, tolerance=0.4)[0]


print("Verified" if match else "Denied")
