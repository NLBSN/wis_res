package com.wis.jframe;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FrameWithPanelTest {

    public static void main1(String[] args) {
        JFrame frame = new JFrame("My Frame");
        frame.setSize(500, 500);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setVisible(true);

        JPanel contentPane = new JPanel();
        contentPane.setSize(100, 100);
        contentPane.setBackground(Color.yellow);
        frame.add(contentPane);
        frame.setLayout(null); //不加上这一句设置布局，contentPanel会铺满整个frame

        BufferedReader intemp = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Press return key to exit.");
        try {
            String s = intemp.readLine();
        } catch (IOException e) {
            System.out.println("IOException");
        }
        System.exit(0);

    }

    public static void main2(String[] args) {
        JFrame frame = new JFrame("My Frame");
        frame.setSize(500, 500);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50)); //为JFrame顶层容器设置FlowLayout布局管理器


        JPanel contentPane = new JPanel();
        contentPane.setSize(100, 100);
        //内部按钮对齐方式，水平和垂直间距
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50)); //为Japnel设置布局管理器
        contentPane.setBackground(Color.yellow);

        JButton btn1, btn2, btn3; //定义3个按钮
        btn1 = new JButton("打开");
        btn2 = new JButton("关闭");
        btn3 = new JButton("返回");
        contentPane.add(btn1);
        contentPane.add(btn2);
        contentPane.add(btn3);

        frame.add(contentPane);
        frame.setVisible(true); //显示JFrame


        BufferedReader intemp = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Press return key to exit.");
        try {
            String s = intemp.readLine();
        } catch (IOException e) {
            System.out.println("IOException");
        }
        System.exit(0);

        // 在上面代码加入下列语句
        // contentPane.add(new JLabel()); //空标签站位用
        // contentPane.add(new JLabel()); //空标签站位用
        // contentPane.add(new JLabel()); //空标签站位用

    }


}
