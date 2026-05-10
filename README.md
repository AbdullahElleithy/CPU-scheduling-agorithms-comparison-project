# 📌 CPU Scheduling Project (JavaFX)

## ⚙️ JavaFX Setup Instructions

Before running the project, you need to configure JavaFX properly on your system.

---

## 📥 1. Download & Add JavaFX

- Download JavaFX SDK from the official site (version 21).
- Extract it to a fixed location, for example:

C:\javafx

- Add the following folder to your project libraries in your IDE (IntelliJ / Eclipse):

C:\javafx\lib

---

## 🧠 2. VM Options Configuration

Go to **Run Configuration → VM Options** and add the following:

--module-path
C:\javafx\lib
--add-modules
javafx.controls,javafx.fxml
-Djava.library.path=C:\javafx\bin

---

## ⚠️ Notes

- Make sure the JavaFX path matches where you extracted the SDK.
- If you move the folder, update all paths accordingly.
- Required modules:
  - javafx.controls
  - javafx.fxml

---

## 🚀 Run the Project

- Open the project in your IDE.
- Make sure VM options are set correctly.
- Run the main class normally.

---

## 🛠 Troubleshooting

If you face issues:
- Verify lib and bin paths.
- Ensure JavaFX SDK version matches your JDK version.
- Recheck VM options if IDE resets configuration.

- ## 📝 Final Note

I didn’t create a separate branch for my work because I was the one who pushed the base code initially, so I committed everything directly to the `main` branch — Abdallah Elliethy.
