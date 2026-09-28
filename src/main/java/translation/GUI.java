package translation;

import javax.swing.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Translator translator = new JSONTranslator();
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();

            JPanel countryPanel = new JPanel();

            String[] countryNames = new String[translator.getCountryCodes().size()];

            int i = 0;
            for (String countryCode : translator.getCountryCodes()) {
                countryNames[i] = countryConverter.fromCountryCode(countryCode);
                i++;
            }

            JList<String> countryList = new JList<>(countryNames);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JScrollPane countryScrollPane = new JScrollPane(countryList);

            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryScrollPane);


            JPanel languagePanel = new JPanel();

            JComboBox<String> languageComboBox = new JComboBox<>();

            for (String languageCode : translator.getLanguageCodes()) {
                String languageName = languageConverter.fromLanguageCode(languageCode);
                languageComboBox.addItem(languageName);
            }

            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);


            JPanel buttonPanel = new JPanel();

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);

            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            countryList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    String countryName = countryList.getSelectedValue();
                    String languageName = (String) languageComboBox.getSelectedItem();

                    if (countryName != null && languageName != null) {
                        String country = countryConverter.fromCountry(countryName);
                        String language = languageConverter.fromLanguage(languageName);

                        String result = translator.translate(country, language);

                        if (result == null) {
                            result = "no translation found!";
                        }

                        resultLabel.setText(result);
                    }
                }
            });


            languageComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String countryName = countryList.getSelectedValue();
                    String languageName = (String) languageComboBox.getSelectedItem();

                    if (countryName != null && languageName != null) {
                        String country = countryConverter.fromCountry(countryName);
                        String language = languageConverter.fromLanguage(languageName);

                        String result = translator.translate(country, language);

                        if (result == null) {
                            result = "no translation found!";
                        }

                        resultLabel.setText(result);
                    }
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