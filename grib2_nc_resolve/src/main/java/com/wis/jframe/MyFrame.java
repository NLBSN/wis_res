package com.wis.jframe;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("我的第一个Frame");
        frame.setSize(500, 1000);
        frame.getContentPane().setBackground(Color.RED);
        frame.setVisible(true);

        BufferedReader intemp = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Press return key to exit.");
        try {
            String s = intemp.readLine();
        } catch (IOException e) {
            System.out.println("IOException");
        }

        System.exit(0);


    }//main 结束

}
