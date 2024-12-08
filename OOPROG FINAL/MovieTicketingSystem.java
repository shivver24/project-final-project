import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class LoginPage implements ActionListener {
    JFrame frame = new JFrame("Login");
    JTextField userField = new JTextField();
    JPasswordField passField = new JPasswordField();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    ImageIcon i = new ImageIcon("C:/Users/Gabe/Downloads/net.png");

    String[][] loginInfo = {{"user", "password"}, {"admin", "admin123"}};

    LoginPage() {
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        
        userLabel.setBounds(50, 50, 100, 25);
        passLabel.setBounds(50, 100, 100, 25);
        userField.setBounds(150, 50, 150, 25);
        passField.setBounds(150, 100, 150, 25);
        loginButton.setBounds(125, 200, 100, 25);
        resetButton.setBounds(225, 200, 100, 25);

        loginButton.addActionListener(this);
        
        resetButton.addActionListener(this);

        frame.add(userLabel);
        frame.add(passLabel);
        frame.add(userField);
        frame.add(passField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.setIconImage(i.getImage());
        frame.getContentPane().setBackground(new Color(0, 0, 0));
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            userField.setText("");
            passField.setText("");
        }

        if (e.getSource() == loginButton) {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            boolean isValid = false;
            for (String[] credentials : loginInfo) {
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    isValid = true;
                    break;
                }
            }

            if (isValid) {
                frame.dispose();
                new MovieCatalog();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
public class MovieCatalog implements ActionListener {
    JFrame frame = new JFrame("Movie Catalog");
    JTextField searchField = new JTextField(15);
    JButton searchButton = new JButton("Search");
    JList<String> movieList;
    DefaultListModel<String> movieModel = new DefaultListModel<>();

    String[] movies = {"Movie A", "Movie B", "Movie C"};

    MovieCatalog() {
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        
        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.setBackground(Color.BLACK);

        searchButton.addActionListener(this);

        
        movieList = new JList<>(movies);
        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieList.setBackground(Color.BLACK);
        movieList.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(movieList);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBorder(null);

        
        JButton selectButton = new JButton("Select Movie");
        selectButton.addActionListener(e -> {
            String selectedMovie = movieList.getSelectedValue();
            if (selectedMovie != null) {
                frame.dispose();
                new BookingScreen(selectedMovie);
            }
        });

        
        selectButton.setBackground(Color.BLACK);
        selectButton.setForeground(Color.WHITE);
        searchButton.setBackground(Color.BLACK);
        searchButton.setForeground(Color.WHITE);

        
        frame.getContentPane().setBackground(Color.BLACK); 
        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(selectButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String query = searchField.getText().toLowerCase();
        movieModel.clear();

        for (String movie : movies) {
            if (movie.toLowerCase().contains(query)) {
                movieModel.addElement(movie);
            }
        }

        movieList.setModel(movieModel);
    }    
  }
}

class BookingScreen {
    JFrame frame = new JFrame("Booking Screen");
    JTextField buyerField = new JTextField();
    JSpinner ticketSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    JLabel totalLabel = new JLabel("Total: Php0");
    JButton seatSelectButton = new JButton("Select Seats");

    BookingScreen(String movie) {
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel buyerLabel = new JLabel("Buyer Name:");
        buyerLabel.setBounds(50, 50, 100, 25);
        buyerField.setBounds(150, 50, 150, 25);

        JLabel ticketLabel = new JLabel("Tickets:");
        ticketLabel.setBounds(50, 100, 100, 25);
        ticketSpinner.setBounds(150, 100, 50, 25);

        totalLabel.setBounds(50, 150, 200, 25);

        seatSelectButton.setBounds(50, 200, 150, 25);
        seatSelectButton.addActionListener(e -> {
            frame.dispose();
            new SeatSelection(movie, (int) ticketSpinner.getValue());
        });

        frame.add(buyerLabel);
        frame.add(buyerField);
        frame.add(ticketLabel);
        frame.add(ticketSpinner);
        frame.add(totalLabel);
        frame.add(seatSelectButton);
        frame.setVisible(true);

        ticketSpinner.addChangeListener(e -> {
            int tickets = (int) ticketSpinner.getValue();
            totalLabel.setText("Total: Php" + (tickets * 200));
        });
    }
}

class SeatSelection {
    JFrame frame = new JFrame("Seat Selection");
    ArrayList<String> selectedSeats = new ArrayList<>();
    JButton[] seatButtons = new JButton[20];
    int tickets;

    SeatSelection(String movie, int tickets) {
        this.tickets = tickets;

        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(4, 5, 10, 10));

        for (int i = 0; i < 20; i++) {
            seatButtons[i] = new JButton("Seat " + (i + 1));
            int index = i;
            seatButtons[i].addActionListener(e -> {
                if (selectedSeats.size() < tickets) {
                    selectedSeats.add("Seat " + (index + 1));
                    seatButtons[index].setEnabled(false);
                    if (selectedSeats.size() == tickets) {
                        frame.dispose();
                        new PaymentConfirmation(movie, selectedSeats);
                    }
                }
            });
            frame.add(seatButtons[i]);
        }

        frame.setVisible(true);
    }
}


class PaymentConfirmation {
    PaymentConfirmation(String movie, ArrayList<String> seats) {
        JFrame frame = new JFrame("Payment Confirmation");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea details = new JTextArea();
        details.setText("Movie: " + movie + "\nSeats: " + seats);
        details.setEditable(false);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            frame.dispose();
        });

        frame.setLayout(new BorderLayout());
        frame.add(details, BorderLayout.CENTER);
        frame.add(confirmButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
  }



public class MovieTicketingSystem {
    public static void main(String[] args) {
        new LoginPage();
    }
}
