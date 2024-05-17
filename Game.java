import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class Game extends JPanel {
    static String correctAnswer;
    static int scoreCounter = 0;

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
     static String interpretCommand(String input, boolean awaitingSolution) {
            
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
                        awaitingSolution = false;
                        return "Correct! \n Solve: " + rEquation() + "\n";
                    } else {
                        return "Incorrect" + correctAnswer;
                    }
                    }else{
                     return "Unknown command";
                    }
                    
            }
        }

    public static void main(String[] args) {
        String input;
        boolean awaitingSolution = false;
        JFrame frame = new JFrame("Command Prompt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter command");
        JTextField textField = new JTextField(20);
        JTextArea textArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);

        panel.add(label);
        panel.add(textField);
        panel.add(scrollPane);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = textField.getText();
                textArea.append(command + "\n");
                textField.setText("");
               textArea.append(interpretCommand(command, awaitingSolution));
                // Redirect output to JTextArea
                PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
                System.setOut(printStream);
            }
        });

        frame.add(panel);
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
