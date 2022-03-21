import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class DictionaryClass1 extends JFrame {

	private JPanel panel1;
	private JPanel panel2;
	private JTextField searchField;
	private JList list;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DictionaryClass1 frame = new DictionaryClass1();
					//frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection;
	public DictionaryClass1() {
		connection = DatabaseConnection.connect();
		/*design the general gui
		    format
		 */
		setBackground(new Color(127, 255, 212));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 500);
		setTitle("Java Dictionary");
		setLayout(new BorderLayout());
		setResizable(false);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(224, 255, 255));
		setJMenuBar(menuBar);
		JMenu Menu = new JMenu("ABOUT");
		menuBar.add(Menu);
		JMenuItem MenuItem = new JMenuItem("ABOUT");
		Menu.add(MenuItem);
		MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				About about = new About();
				about.setVisible(true);
			}
		});

		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setBounds(0,0,450,150);


		panel2=new JPanel();
		panel2.setLayout(new GridLayout(2, 1));
		panel2.setBounds(0,200,450,250);

		JLabel Label = new JLabel("Search Here:");
		Label.setBounds(23, 34, 89, 14);
		panel1.add(Label);

		searchField = new JTextField();
		searchField.setBackground(new Color(211, 211, 211));
		searchField.setBounds(100, 28, 189, 45);
		searchField.setColumns(10);
		panel1.add(searchField);


		JButton searchButton = new JButton("Search");
		searchButton.setForeground(new Color(0, 102, 153));
		searchButton.setBounds(271, 28, 89, 23);
		searchButton.setFocusable(false);
		panel1.add(searchButton,FlowLayout.RIGHT);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO:(Not implemented yet)
			}
		});


		list = new JList();
		list.setVisibleRowCount(10);
		JScrollPane pane=new JScrollPane(list);
		panel2.add(pane);
		list.setFont(new Font(" GeeIME", Font.PLAIN,15));
		list.setForeground(new Color(25, 25, 112));
		list.setBackground(new Color(220, 220, 220));
		list.setBounds(0, 150, 450, 150);

		JTextArea amharicMeaning = new JTextArea();
		amharicMeaning.setBounds(0, 300, 450, 150);
		//amharicMeaning.setFont(new Font("GeezIME",Font.PLAIN,20));
		// Run when a certain list item is selected
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				amharicMeaning.setText(meaningQuery(list.getSelectedValue().toString()));
			}
		});
		panel2.add(amharicMeaning);

		getContentPane().add(panel1,BorderLayout.NORTH);
		getContentPane().add(panel2,BorderLayout.CENTER);


		/**
		 * Listening to the search field text changes to send a query to the database.
		 */
		searchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				Vector searchResult = SearchQuery(searchField.getText(), new Vector<>());
				list.setListData(searchResult);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				Vector searchResult = SearchQuery(searchField.getText(), new Vector<>());
				list.setListData(searchResult);
			}

			// not needed
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
		});

	}

	/**
	 * Once a certain word is chosen, this method is used to get the Amharic translation for the word
	 * @param str
	 * @return
	 */
	public String meaningQuery(String str) {
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT * FROM dictionary WHERE _id=?");
			pstmt.setString(1, str);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				String word = rs.getString("amh");
				return word;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return "";
	}

	/**
	 * Used to search the database for the words which begin with the values specified.
	 * @param str
	 * @param response
	 * @return
	 */
	public Vector<String> SearchQuery(String str, Vector<String> response) {
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT * FROM dictionary WHERE _id like ?");
			pstmt.setString(1, str + "%");
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				String word = rs.getString("_id");
				response.add(word);
			}
			return response;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return new Vector<>();

	}
}
