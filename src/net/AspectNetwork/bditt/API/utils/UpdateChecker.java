package net.AspectNetwork.bditt.API.utils;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.bukkit.plugin.Plugin;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UpdateChecker {

  private String latestVersion = "";
  private String downloadLink = "";

  public UpdateChecker(String url) {
    try {
      InputStream stream = new URL(url).openConnection().getInputStream();
      Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);

      Node latestFile = document.getElementsByTagName("item").item(0);
      NodeList childNodes = latestFile.getChildNodes();

      latestVersion = childNodes.item(1).getTextContent();
      downloadLink = childNodes.item(3).getTextContent();
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
  }

  public String getLatestVersion() {
    return latestVersion;
  }

  public String getDownloadLink() {
    return downloadLink;
  }

  public boolean isUpdateNeeded(Plugin plugin) {
    return !("v" + plugin.getDescription().getVersion()).equalsIgnoreCase(latestVersion);
  }
}
