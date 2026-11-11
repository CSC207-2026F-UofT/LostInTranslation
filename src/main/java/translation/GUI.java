package translation;

import javax.swing.*;
import java.awt.event.*;
import translation.LanguageCodeConverter;
import translation.CountryCodeConverter;

// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        Translator translator = new JSONTranslator();
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));
            JComboBox<String> countryComboBox = new JComboBox<>();
            CountryCodeConverter coun = new CountryCodeConverter();
            for(String countryCode : translator.getCountryCodes()) {
                countryComboBox.addItem(coun.fromCountryCode(countryCode));
            }
            countryPanel.add(countryComboBox);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageComboBox = new JComboBox<>();
            LanguageCodeConverter lang = new LanguageCodeConverter();
            for(String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(lang.fromLanguageCode(languageCode));
            }
            languagePanel.add(languageComboBox);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = lang.fromLanguage(languageComboBox.getSelectedItem().toString());
                    String country = coun.fromCountry(countryComboBox.getSelectedItem().toString());

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.


                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
