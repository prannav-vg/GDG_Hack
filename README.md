ShadowData
Real-Time Monitoring and Preventing Permission Misuse
ShadowData is an Android privacy monitoring application designed to detect, explain, and prevent permission misuse in real time. 
It combines static permission analysis, runtime monitoring, and on-device AI-based risk prediction to provide transparent, privacy-first mobile security without collecting or uploading any personal data.

Features
Real-Time Permission Monitoring
Detects live camera and microphone access instantly.

AI-Based Risk Prediction
Classifies applications as Safe, Suspicious, or Dangerous using on-device machine learning.

Explainable Privacy Alerts
Clearly explains why an app is considered risky.

Incident Logging & Alerts
Logs high-risk events with timestamps and severity levels.

Women Safety Mode
Provides elevated alerts during sensitive situations.

Privacy Timeline
Maintains an on-device audit trail of permission usage.

Exportable Security Reports
Generates audit-ready reports in CSV and PDF formats.

Privacy-First Architecture
Fully on-device processing with zero cloud dependency.

Project Structure
app/
 ├── ui/                # Compose UI screens
 ├── navigation/        # Navigation controller
 ├── utils/             # Exporters & helpers
 ├── monitoring/        # Camera & mic monitors
 ├── ml/                # AI risk prediction logic
 └── MainActivity.kt

 
Permissions Used
CAMERA
RECORD_AUDIO
ACCESS_FINE_LOCATION
READ_CONTACTS
PACKAGE_USAGE_STATS

Report Export
ShadowData allows users to export security incident reports:
CSV – For audit and compliance review
PDF – For human-readable reports
Reports are stored in app-specific storage and never uploaded.

Novelty
ShadowData uniquely integrates real-time monitoring, explainable AI, and incident-based reporting into a single on-device privacy solution. 
Unlike traditional permission managers, it focuses on transparency, user understanding, and auditability

Authors
V Rishidharan(Testing and deployment)
V.G Pranav(Full stack engineer)
Tenin Christopher(AI-ML)
V Kaushik Muthuraman(UI-UX)
