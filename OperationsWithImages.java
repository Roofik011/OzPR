package com.example;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

/**
 * Класс для выполнения различных операций над изображениями.
 * Предоставляет методы для выбора изображения, захвата изображения с веб-камеры,
 * показа отдельных цветовых каналов, обрезки, вращения и рисования прямоугольника на изображении.
 */
public class OperationsWithImages {

  private static Mat currentImage;
  private static Mat originalImage;
  private static JLabel imageLabel;

  static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  public static void setImageLabel(JLabel label) {
    imageLabel = label;
  }

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
      new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
          currentImage = Imgcodecs.imread(filePath);
          originalImage = currentImage.clone();
          return null;
        }

        @Override
        protected void done() {
          if (!currentImage.empty()) {
            updateImageLabel(currentImage);
          } else {
            JOptionPane.showMessageDialog(null, "Не удалось загрузить изображение", "Ошибка", JOptionPane.ERROR_MESSAGE);
          }
        }
      }.execute();
    }
  }

  /**
   * Захват изображения с веб-камеры.
   */
  public static void captureImage() {
    new SwingWorker<Void, Void>() {
      @Override
      protected Void doInBackground() throws Exception {
        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
          JOptionPane.showMessageDialog(null, "Не удалось открыть веб-камеру", "Ошибка", JOptionPane.ERROR_MESSAGE);
          return null;
        }

        Mat frame = new Mat();
        if (camera.read(frame)) {
          currentImage = frame;
          originalImage = currentImage.clone();
        } else {
          JOptionPane.showMessageDialog(null, "Не удалось захватить изображение", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        camera.release();
        return null;
      }

      @Override
      protected void done() {
        if (currentImage != null && !currentImage.empty()) {
          updateImageLabel(currentImage);
        }
      }
    }.execute();
  }

  /**
   * Показать выбранный цветовой канал изображения.
   * имя цветового канала ("red", "green", "blue")
   */
  public static void showChannel(String channel) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(null, "Изображение не загружено", "Ошибка", JOptionPane.ERROR_MESSAGE);
      return;
    }

    new SwingWorker<Void, Void>() {
      private Mat channelImage;

      @Override
      protected Void doInBackground() throws Exception {
        channelImage = new Mat();
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
            return null;
        }
        Core.extractChannel(currentImage, channelImage, channelIndex);
        return null;
      }

      @Override
      protected void done() {
        if (channelImage != null && !channelImage.empty()) {
          updateImageLabel(channelImage);
        }
      }
    }.execute();
  }

  /**
   * Обрезать изображение по заданным координатам.
   *
   * x      координата x верхнего левого угла обрезки
   * y      координата y верхнего левого угла обрезки
   * width  ширина области обрезки
   * height высота области обрезки
   */
  public static void cropImage(int x, int y, int width, int height) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(null, "Изображение не загружено", "Ошибка", JOptionPane.ERROR_MESSAGE);
      return;
    }

    new SwingWorker<Void, Void>() {
      private Mat croppedImage;

      @Override
      protected Void doInBackground() throws Exception {
        Rect rect = new Rect(x, y, width, height);
        croppedImage = new Mat(currentImage, rect);
        return null;
      }

      @Override
      protected void done() {
        currentImage = croppedImage;
        if (currentImage != null && !currentImage.empty()) {
          updateImageLabel(currentImage);
        }
      }
    }.execute();
  }

  /**
   * Вращать изображение на заданный угол.
   * angle угол вращения в градусах
   */
  public static void rotateImage(double angle) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(null, "Изображение не загружено", "Ошибка", JOptionPane.ERROR_MESSAGE);
      return;
    }

    new SwingWorker<Void, Void>() {
      private Mat rotatedImage;

      @Override
      protected Void doInBackground() throws Exception {
        Point center = new Point(currentImage.width() / 2, currentImage.height() / 2);
        Mat rotationMatrix = Imgproc.getRotationMatrix2D(center, angle, 1.0);
        rotatedImage = new Mat();
        Imgproc.warpAffine(currentImage, rotatedImage, rotationMatrix, currentImage.size());
        return null;
      }

      @Override
      protected void done() {
        currentImage = rotatedImage;
        if (currentImage != null && !currentImage.empty()) {
          updateImageLabel(currentImage);
        }
      }
    }.execute();
  }

  /**
   * Нарисовать прямоугольник на изображении синим цветом по заданным координатам.
   * x      координата x верхнего левого угла прямоугольника
   * y      координата y верхнего левого угла прямоугольника
   * width  ширина прямоугольника
   * height высота прямоугольника
   */
  public static void drawRectangle(int x, int y, int width, int height) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(null, "Изображение не загружено", "Ошибка", JOptionPane.ERROR_MESSAGE);
      return;
    }

    new SwingWorker<Void, Void>() {
      @Override
      protected Void doInBackground() throws Exception {
        Point topLeft = new Point(x, y);
        Point bottomRight = new Point(x + width, y + height);
        Scalar blue = new Scalar(255, 0, 0); // Синий цвет
        Imgproc.rectangle(currentImage, topLeft, bottomRight, blue, 2);
        return null;
      }

      @Override
      protected void done() {
        if (currentImage != null && !currentImage.empty()) {
          updateImageLabel(currentImage);
        }
      }
    }.execute();
  }

  /**
   * Сбросить изображение к первоначальному состоянию.
   */
  public static void resetImage() {
    if (originalImage != null) {
      currentImage = originalImage.clone();
      updateImageLabel(currentImage);
    } else {
      JOptionPane.showMessageDialog(null, "Оригинальное изображение не загружено", "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
  }

  private static void updateImageLabel(Mat mat) {
    if (imageLabel != null) {
      BufferedImage bufferedImage = matToBufferedImage(mat);
      imageLabel.setIcon(new ImageIcon(bufferedImage));
    }
  }

  private static BufferedImage matToBufferedImage(Mat mat) {
    int type = BufferedImage.TYPE_BYTE_GRAY;
    if (mat.channels() > 1) {
      type = BufferedImage.TYPE_3BYTE_BGR;
    }
    BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
    mat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
    return image;
  }
}
