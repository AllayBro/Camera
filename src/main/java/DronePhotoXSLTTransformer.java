package main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;

public class DronePhotoXSLTTransformer {

    public static void main(String[] args) {
        try {
            // 1. Создаем папку для сохранения файлов, если её нет
            new File("save").mkdirs();

            // Определяем путь к XML файлу
            String xmlPath;
            if (args.length > 0) {
                xmlPath = args[0];
            } else {
                // Используем файл по умолчанию из ресурсов
                File defaultFile = new File("src/main/resources/example.xml");
                if (defaultFile.exists()) {
                    xmlPath = defaultFile.getAbsolutePath();
                    System.out.println("Используется файл по умолчанию: " + xmlPath);
                } else {
                    System.err.println("Ошибка: не указан путь к XML-файлу и файл по умолчанию не найден.");
                    return;
                }
            }

            // 2. Парсим XML (SAXSource)
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser saxParser = factory.newSAXParser();
            InputSource inputSource = new InputSource(new FileInputStream(xmlPath));
            SAXSource xmlSource = new SAXSource(saxParser.getXMLReader(), inputSource);

            // 3. Загружаем XSLT-шаблон
            StreamSource xsltSource = new StreamSource(new File("transform.xsl"));

            // 4. ????????????? ????????? ? DOM
            DOMResult intermediateResult = new DOMResult();

            // 5. ???????? ???????????? ??? XSLT
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(xsltSource);

            // 6. ????????? ?????????? XSLT (????????, ??????? ??????? ??????)
            transformer.setParameter("scale", 1.0);

            // 7. ?????????? XSLT-??????????????
            transformer.transform(xmlSource, intermediateResult);

            // 8. ?????????? DOM-?????????? ? HTML ????
            Transformer serializer = transformerFactory.newTransformer();
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD HTML 4.01 Transitional//EN");

            FileWriter fileWriter = new FileWriter("save/report_xslt.html");
            serializer.transform(new DOMSource(intermediateResult.getNode()), new StreamResult(fileWriter));

            System.out.println("XSLT-трансформация успешно выполнена и сохранена в save/report_xslt.html");

            // 9. Автоматически открываем результирующий файл в браузере
            try {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "save/report_xslt.html"});
            } catch (IOException ex) {
                System.err.println("[WARNING] Не удалось автоматически открыть файл в браузере.");
            }

        } catch (Exception e) {
            System.err.println("[ERROR] Ошибка при выполнении XSLT-трансформации:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}




