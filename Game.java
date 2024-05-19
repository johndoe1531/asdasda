import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class Game extends JPanel {
    static String correctAnswer;
    static int scoreCounter = 0;
    static boolean awaitingSolution = false;
    static String asciiArt() {
        
        if(scoreCounter == 3) {
            return " + \n";
        }
        if(scoreCounter == 5) {
            return " + \n";
        }
        else return "";
    }
    static String rEquation() {
        int x;
        int y;

        x = (int) (Math.random() * 100);
        y = (int) (Math.random() * 100);

        String result = "";
        result = x + " + " + y;
        correctAnswer = String.valueOf(x + y);

        return result;
    }
     static String interpretCommand(String input) {
            
            switch (input) {
                case "s":
                    if (awaitingSolution) {
                        return "Answer the question first";
                    } else {
                        awaitingSolution = true;
                        return "Solve: " + rEquation();
                        
                    }
                case "exit":
                    System.exit(0);
                default:
                    if(awaitingSolution){
                        if (input.equals(correctAnswer)) {
                        scoreCounter++;
                        
                        return "Correct! \n Solve: " + rEquation() + "\n";
                    } else {
                        return "Incorrect!";
                    }
                    }else{
                     return "Unknown command";
                    }
                    
            }
        }

    public static void main(String[] args) {
        String input;
        JFrame frame = new JFrame("Command Prompt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JTextField textField = new JTextField(50);
        JTextArea textArea = new JTextArea(20, 60);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setBorder(null);
        textField.setCaretColor(Color.GREEN);
        textField.setBorder(null);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.GREEN);
        panel.add(textField);
        panel.add(scrollPane);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = textField.getText();
                textArea.append(command + "\n");
                textField.setText("");
               textArea.append(interpretCommand(command) + "\n");
               textArea.append(asciiArt());
                // Redirect output to JTextArea
                PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
                System.setOut(printStream);
            }
        });
        
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(textField, BorderLayout.SOUTH);
        
        frame.setVisible(true);

        System.out.println("Welcome to Math Game, press 's' to start and 'x' to exit");
        
       
    }
}

class CustomOutputStream extends OutputStream {
    private JTextArea textArea;

    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        // Redirect output to JTextArea
        textArea.append(String.valueOf((char) b));
        // Scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}

