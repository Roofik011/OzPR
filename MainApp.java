package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Основной класс приложения для захвата и обработки изображений.
 * Приложение позволяет пользователю выбрать изображение с диска или сделать снимок с веб-камеры.
 * Также предоставляет возможности для выполнения различных операций над изображением, таких как:
 * - Показать красный, зеленый или синий канал изображения.
 * - Обрезать изображение по заданным координатам.
 * - Вращать изображение на заданный угол.
 * - Нарисовать прямоугольник на изображении синим цветом по заданным координатам.
 * - Вернуть изображение к первоначальному состоянию.
 */
public class MainApp {

  public static void main(String[] args) {
    OperationsWithImages.loadOpenCVLibrary();

    JFrame frame = new JFrame("Работа с изображениями");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1024, 768);

    JLabel imageLabel = new JLabel();
    OperationsWithImages.setImageLabel(imageLabel);

    JButton chooseImageButton = new JButton("Выбрать изображение");
    JButton captureImageButton = new JButton("Сделать снимок с веб-камеры");
    JButton showChannelButton = new JButton("Показать канал");
    JButton cropImageButton = new JButton("Обрезать изображение");
    JButton rotateImageButton = new JButton("Вращать изображение");
    JButton drawRectangleButton = new JButton("Нарисовать прямоугольник");
    JButton resetImageButton = new JButton("Сбросить изображение");

    JTextField channelInput = new JTextField(5);
    JTextField cropXInput = new JTextField(5);
    JTextField cropYInput = new JTextField(5);
    JTextField cropWidthInput = new JTextField(5);
    JTextField cropHeightInput = new JTextField(5);
    JTextField rotateAngleInput = new JTextField(5);
    JTextField rectXInput = new JTextField(5);
    JTextField rectYInput = new JTextField(5);
    JTextField rectWidthInput = new JTextField(5);
    JTextField rectHeightInput = new JTextField(5);

    chooseImageButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        OperationsWithImages.chooseImage();
      }
    });

    captureImageButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        OperationsWithImages.captureImage();
      }
    });

    showChannelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String channel = channelInput.getText();
        OperationsWithImages.showChannel(channel);
      }
    });

    cropImageButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int x = Integer.parseInt(cropXInput.getText());
        int y = Integer.parseInt(cropYInput.getText());
        int width = Integer.parseInt(cropWidthInput.getText());
        int height = Integer.parseInt(cropHeightInput.getText());
        OperationsWithImages.cropImage(x, y, width, height);
      }
    });

    rotateImageButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        double angle = Double.parseDouble(rotateAngleInput.getText());
        OperationsWithImages.rotateImage(angle);
      }
    });

    drawRectangleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int x = Integer.parseInt(rectXInput.getText());
        int y = Integer.parseInt(rectYInput.getText());
        int width = Integer.parseInt(rectWidthInput.getText());
        int height = Integer.parseInt(rectHeightInput.getText());
        OperationsWithImages.drawRectangle(x, y, width, height);
      }
    });

    resetImageButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        OperationsWithImages.resetImage();
      }
    });

    JPanel controlsPanel = new JPanel();
    controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));

    controlsPanel.add(chooseImageButton);
    controlsPanel.add(captureImageButton);
    controlsPanel.add(new JLabel("Канал (Red/Green/Blue):"));
    controlsPanel.add(channelInput);
    controlsPanel.add(showChannelButton);
    controlsPanel.add(new JLabel("Обрезка (x, y, width, height):"));
    controlsPanel.add(cropXInput);
    controlsPanel.add(cropYInput);
    controlsPanel.add(cropWidthInput);
    controlsPanel.add(cropHeightInput);
    controlsPanel.add(cropImageButton);
    controlsPanel.add(new JLabel("Угол вращения:"));
    controlsPanel.add(rotateAngleInput);
    controlsPanel.add(rotateImageButton);
    controlsPanel.add(new JLabel("Прямоугольник (x, y, width, height):"));
    controlsPanel.add(rectXInput);
    controlsPanel.add(rectYInput);
    controlsPanel.add(rectWidthInput);
    controlsPanel.add(rectHeightInput);
    controlsPanel.add(drawRectangleButton);
    controlsPanel.add(resetImageButton);

    frame.setLayout(new BorderLayout());
    frame.add(controlsPanel, BorderLayout.WEST);
    frame.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
    frame.setVisible(true);
  }
}
