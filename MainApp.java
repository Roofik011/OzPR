package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Приложение для захвата и обработки изображений,
 * позволяет пользователю выбрать изображение с диска или сделать снимок с веб-камеры.
 * Также предоставляет возможности для выполнения различных операций над изображением, таких как:
 * - Показать красный, зеленый или синий канал изображения.
 * - Обрезать изображение по заданным координатам.
 * - Вращать изображение на заданный угол.
 * - Нарисовать прямоугольник на изображении синим цветом по заданным координатам.
 */

public class MainApp {

  public static void main(String[] args) {
    OperationsWithImages.loadOpenCVLibrary();

    // GUI
    JFrame frame = new JFrame("Image Capture");
    JButton chooseImageButton = new JButton("Выбрать изображение");
    JButton captureImageButton = new JButton("Сделать снимок с веб-камеры");
    JButton showChannelButton = new JButton("Показать канал");
    JButton cropImageButton = new JButton("Обрезать изображение");
    JButton rotateImageButton = new JButton("Вращать изображение");
    JButton drawRectangleButton = new JButton("Нарисовать прямоугольник");

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

    frame.setLayout(new java.awt.FlowLayout());
    frame.add(chooseImageButton);
    frame.add(captureImageButton);
    frame.add(new JLabel("Канал (Red/Green/Blue):"));
    frame.add(channelInput);
    frame.add(showChannelButton);
    frame.add(new JLabel("Обрезка (x, y, width, height):"));
    frame.add(cropXInput);
    frame.add(cropYInput);
    frame.add(cropWidthInput);
    frame.add(cropHeightInput);
    frame.add(cropImageButton);
    frame.add(new JLabel("Угол вращения:"));
    frame.add(rotateAngleInput);
    frame.add(rotateImageButton);
    frame.add(new JLabel("Прямоугольник (x, y, width, height):"));
    frame.add(rectXInput);
    frame.add(rectYInput);
    frame.add(rectWidthInput);
    frame.add(rectHeightInput);
    frame.add(drawRectangleButton);

    frame.setSize(500, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
