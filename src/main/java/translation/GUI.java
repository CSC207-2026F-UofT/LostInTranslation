package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Task C：负责实际翻译
            Translator translator = new JSONTranslator();

            // Task A：语言名称与语言代码互相转换
            LanguageCodeConverter languageConverter =
                    new LanguageCodeConverter();

            // Task B：国家名称与国家代码互相转换
            CountryCodeConverter countryConverter =
                    new CountryCodeConverter();

            /*
             * 准备语言名称。
             *
             * JSONTranslator 返回语言代码，例如：
             * en、es、fr
             *
             * GUI 应显示用户可读的名称，例如：
             * English、Spanish、French
             */
            List<String> languageNames = new ArrayList<>();

            for (String languageCode
                    : translator.getLanguageCodes()) {

                String languageName =
                        languageConverter.fromLanguageCode(
                                languageCode
                        );

                if (languageName != null) {
                    languageNames.add(languageName);
                }
            }

            // 按字母顺序显示语言
            languageNames.sort(
                    String.CASE_INSENSITIVE_ORDER
            );

            JComboBox<String> languageComboBox =
                    new JComboBox<>(
                            languageNames.toArray(new String[0])
                    );

            /*
             * 准备国家名称。
             *
             * JSONTranslator 返回193个有翻译数据的国家代码，
             * 例如 can、usa、gbr。
             *
             * CountryCodeConverter 将它们转换为：
             * Canada、United States of America (the) 等。
             */
            List<String> countryNames = new ArrayList<>();

            for (String countryCode
                    : translator.getCountryCodes()) {

                String countryName =
                        countryConverter.fromCountryCode(
                                countryCode
                        );

                if (countryName != null) {
                    countryNames.add(countryName);
                }
            }

            // 按字母顺序显示国家
            countryNames.sort(
                    String.CASE_INSENSITIVE_ORDER
            );

            JList<String> countryList =
                    new JList<>(
                            countryNames.toArray(new String[0])
                    );

            // 一次只能选择一个国家
            countryList.setSelectionMode(
                    ListSelectionModel.SINGLE_SELECTION
            );

            // 默认显示约12行
            countryList.setVisibleRowCount(12);

            JScrollPane countryScrollPane =
                    new JScrollPane(countryList);

            countryScrollPane.setPreferredSize(
                    new Dimension(350, 250)
            );

            /*
             * 翻译结果标签。
             */
            JLabel resultLabel = new JLabel(
                    "Select a country and language"
            );

            /*
             * 共用的更新逻辑。
             *
             * 国家或语言发生变化时，都执行这里的代码。
             */
            Runnable updateTranslation = () -> {
                String selectedCountry =
                        countryList.getSelectedValue();

                Object selectedLanguageObject =
                        languageComboBox.getSelectedItem();

                // 防止界面刚创建时还没有选择内容
                if (selectedCountry == null
                        || selectedLanguageObject == null) {
                    resultLabel.setText(
                            "Select a country and language"
                    );
                    return;
                }

                String selectedLanguage =
                        selectedLanguageObject.toString();

                /*
                 * 将用户看到的名称转换回程序使用的代码。
                 *
                 * Canada  → can
                 * Spanish → es
                 */
                String countryCode =
                        countryConverter.fromCountry(
                                selectedCountry
                        );

                String languageCode =
                        languageConverter.fromLanguage(
                                selectedLanguage
                        );

                /*
                 * Task C 执行实际翻译。
                 *
                 * 例如：
                 * translate("can", "es") → "Canadá"
                 */
                String result = translator.translate(
                        countryCode,
                        languageCode
                );

                if (result == null) {
                    result = "No translation found";
                }

                resultLabel.setText(result);
            };

            /*
             * 用户选择另一种语言时更新结果。
             */
            languageComboBox.addActionListener(
                    event -> updateTranslation.run()
            );

            /*
             * 用户选择另一个国家时更新结果。
             */
            countryList.addListSelectionListener(
                    (ListSelectionEvent event) -> {
                        /*
                         * 拖动或快速改变选择时，Swing 可能产生多个事件。
                         * getValueIsAdjusting() 为 false 表示选择已稳定。
                         */
                        if (!event.getValueIsAdjusting()) {
                            updateTranslation.run();
                        }
                    }
            );

            /*
             * 语言区域。
             */
            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);

            /*
             * 国家区域。
             */
            JPanel countryPanel =
                    new JPanel(new BorderLayout(5, 5));

            countryPanel.add(
                    new JLabel("Country:"),
                    BorderLayout.NORTH
            );

            countryPanel.add(
                    countryScrollPane,
                    BorderLayout.CENTER
            );

            /*
             * 翻译结果区域。
             */
            JPanel resultPanel = new JPanel();
            resultPanel.add(new JLabel("Translation:"));
            resultPanel.add(resultLabel);

            /*
             * 组装整个窗口。
             */
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(
                    new BoxLayout(
                            mainPanel,
                            BoxLayout.Y_AXIS
                    )
            );

            mainPanel.add(languagePanel);
            mainPanel.add(countryPanel);
            mainPanel.add(resultPanel);

            JFrame frame =
                    new JFrame("Country Name Translator");

            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            /*
             * 设置默认国家。
             *
             * JComboBox 创建后默认选择第一种语言，
             * JList 默认没有选择，所以这里选择第一个国家。
             */
            if (!countryNames.isEmpty()) {
                countryList.setSelectedIndex(0);
            }

            // 明确进行第一次翻译
            updateTranslation.run();
        });
    }
}