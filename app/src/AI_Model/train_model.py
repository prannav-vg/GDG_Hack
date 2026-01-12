import pandas as pd
import tensorflow as tf

# Load dataset
data = pd.read_csv("apps.csv")

X = data.drop("label", axis=1).values
y = data["label"].values

# Lightweight neural network
model = tf.keras.Sequential([
    tf.keras.layers.Input(shape=(8,)),
    tf.keras.layers.Dense(16, activation="relu"),
    tf.keras.layers.Dense(3, activation="softmax")
])

model.compile(
    optimizer="adam",
    loss="sparse_categorical_crossentropy",
    metrics=["accuracy"]
)

model.fit(X, y, epochs=30)

# Convert to TensorFlow Lite
converter = tf.lite.TFLiteConverter.from_keras_model(model)
tflite_model = converter.convert()

with open("risk_model.tflite", "wb") as f:
    f.write(tflite_model)

print("risk_model.tflite generated successfully")
