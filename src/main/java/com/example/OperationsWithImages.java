package com.example;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.io.File;

/**
 * Класс для выполнения различных операций над изображениями.
 * <p>
 * Предоставляет методы для выбора изображения, захвата изображения с веб-камеры,
 * показа отдельных цветовых каналов, обрезки, вращения и рисования прямоугольника на изображении.
 */
public class OperationsWithImages {

  private static Mat currentImage;

  static {
    // Загрузка библиотеки OpenCV
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  /**
   * Загрузка библиотеки OpenCV.
   */
  public static void loadOpenCVLibrary() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  /**
   * Открытие диалога выбора изображения и загрузка выбранного изображения.
   */
  public static void chooseImage() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String filePath = selectedFile.getAbsolutePath();
      currentImage = Imgcodecs.imread(filePath);
      if (!currentImage.empty()) {
        HighGui.imshow("Выбранное изображение", currentImage);
        HighGui.waitKey(0);
        HighGui.destroyAllWindows();
      } else {
        JOptionPane.showMessageDialog(null, "Не удалось загрузить изображение", "Ошибка", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Захват изображения с веб-камеры.
   */
  public static void captureImage() {
    VideoCapture camera = new VideoCapture(0);
    if (!camera.isOpened()) {
      JOptionPane.showMessageDialog(null, "Не удалось открыть веб-камеру", "Ошибка", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Mat frame = new Mat();
    if (camera.read(frame)) {
      currentImage = frame;
      HighGui.imshow("Снимок с веб-камеры", currentImage);
      HighGui.waitKey(0);
      HighGui.destroyAllWindows();
    } else {
      JOptionPane.showMessageDialog(null, "Не удалось захватить изображение", "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
    camera.release();
  }

  /**
   * Показать выбранный цветовой канал изображения.
   *
   * @param channel имя цветового канала ("red", "green", "blue")
   */
  public static void showChannel(String channel) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(null, "Изображение не загружено", "Ошибка", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Mat channelImage = new Mat();
    int channelIndex;
    switch (channel.toLowerCase()) {
      case "red":
        channelIndex = 2;
        break;
      case "green":
        channelIndex = 1;
        break;
      case "blue":
        channelIndex = 0;
        break;
      default:
        JOptionPane.showMessageDialog(null, "Неверное имя канала", "Ошибка", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Core.extractChannel(currentImage, channelImage, channelIndex);
    HighGui.imshow("Канал " + channel, channelImage);
    HighGui.waitKey(0);
    HighGui.destroyAllWindows();
  }

  /**
   * Обрезать изображение по заданным координатам.
   *
   * @param x      координата x верхнего левого угла обрезки
   * @param y      координата y верхнего левого угла обрезки
   * @param width  ширина области обрезки
   * @param height высота области обрезки
   */
  public static void cropImage(int x, int y, int width, int height) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(null, "Изображение не загружено", "Ошибка", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Rect rect = new Rect(x, y, width, height);
    Mat croppedImage = new Mat(currentImage, rect);
    currentImage = croppedImage;
    HighGui.imshow("Обрезанное изображение", currentImage);
    HighGui.waitKey(0);
    HighGui.destroyAllWindows();
  }

  /**
   * Вращать изображение на заданный угол.
   *
   * @param angle угол вращения в градусах
   */
  public static void rotateImage(double angle) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(null, "Изображение не загружено", "Ошибка", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Point center = new Point(currentImage.width() / 2, currentImage.height() / 2);
    Mat rotationMatrix = Imgproc.getRotationMatrix2D(center, angle, 1.0);
    Mat rotatedImage = new Mat();
    Imgproc.warpAffine(currentImage, rotatedImage, rotationMatrix, currentImage.size());
    currentImage = rotatedImage;
    HighGui.imshow("Вращенное изображение", currentImage);
    HighGui.waitKey(0);
    HighGui.destroyAllWindows();
  }

  /**
   * Нарисовать прямоугольник на изображении по заданным координатам.
   *
   * @param x      координата x верхнего левого угла прямоугольника
   * @param y      координата y верхнего левого угла прямоугольника
   * @param width  ширина прямоугольника
   * @param height высота прямоугольника
   */
  public static void drawRectangle(int x, int y, int width, int height) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(null, "Изображение не загружено", "Ошибка", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Point topLeft = new Point(x, y);
    Point bottomRight = new Point(x + width, y + height);
    Scalar blue = new Scalar(255, 0, 0); // Синий цвет
    Imgproc.rectangle(currentImage, topLeft, bottomRight, blue, 2);
    HighGui.imshow("Изображение с прямоугольником", currentImage);
    HighGui.waitKey(0);
    HighGui.destroyAllWindows();
  }
}
