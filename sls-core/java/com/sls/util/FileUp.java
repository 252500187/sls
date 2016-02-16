package com.sls.util;

import java.io.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.sls.scorm.entity.Sco;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

public class FileUp {

    public void uploadFile(InputStream input, String filePath, String fileName) {
        try {
            FileOutputStream os = new FileOutputStream(filePath + fileName);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = input.read(bytes)) != -1) {
                os.write(bytes, 0, c);
            }
            os.close();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public InputStream getInputStream(MultipartHttpServletRequest request, String fileIn) throws IOException {
        MultipartFile file = request.getFile(fileIn);
        return file.getInputStream();
    }

    public String upImg(HttpServletRequest request, String mainPath, String imgPath, String imgName, String upImg) throws ServletException, IOException {
        String path = request.getSession().getServletContext().getRealPath("");
        path = path + "/" + mainPath + imgPath + "/";
        //创建文件，防文件夹不存在
        File file = new File(path);
        file.mkdirs();
        //接受页面流文件
        InputStream input = getInputStream((MultipartHttpServletRequest) request, upImg);
        uploadFile(input, path, imgName);
        //返回路径 0图片引用路径 1文件路径
        path = path.replace("\"", "/");
        String filePath = path + imgName;
        return filePath.substring(filePath.indexOf(mainPath));
    }

    public String upScorm(HttpServletRequest request, String scormPath, String upFile) throws ServletException, IOException, Exception {
        String scormFilePath = request.getSession().getServletContext().getRealPath("");
        scormFilePath = scormFilePath + "/" + DictConstant.TOP_SCORM_FILE_NAME + "/" + scormPath + "/" + DictConstant.SCORM_FILE_NAME + "/";
        //创建文件，防文件夹不存在
        File file = new File(scormFilePath);
        file.mkdirs();
        //接受页面流文件
        InputStream input = getInputStream((MultipartHttpServletRequest) request, upFile);
        uploadFile(input, scormFilePath, DictConstant.SCORM_NAME);
        ZipUtil.unzip(scormFilePath + DictConstant.SCORM_NAME);
        return scormFilePath;
    }

    public void unzip(String fileName) {
        try {
            String filePath = fileName.substring(0, fileName.lastIndexOf("/") + 1);
            ZipFile zipFile = new ZipFile(fileName);
            Enumeration emu = zipFile.entries();
            while (emu.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) emu.nextElement();
                if (entry.isDirectory()) {
                    new File(filePath + entry.getName()).mkdirs();
                    continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(filePath + entry.getName());
                File parent = file.getParentFile();
                if (parent != null && (!parent.exists())) {
                    parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                int count;
                byte data[] = new byte[1024];
                while ((count = bis.read(data, 0, 1024)) != -1) {
                    bos.write(data, 0, count);
                }
                bos.flush();
                bos.close();
                bis.close();
            }
            zipFile.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public List<Sco> analyzeXml(String url) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        //得到一个Xpath对象
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expression = xpath.compile("/manifest/organizations/organization");
        //得到一个输入对象
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File(url));
        NodeList nodeChapterList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
        Element element;
        NodeList nodeList;
        String scoPath = url.substring(url.indexOf(DictConstant.TOP_SCORM_FILE_NAME));
        scoPath = scoPath.substring(0, scoPath.indexOf(DictConstant.IMSMANIFEST));
        List<Sco> scos = new LinkedList<Sco>();
//        //处理没有organization的情况
//        if (nodeChapterList.getLength() < 1) {
//            expression = xpath.compile("/manifest/resources/resource");
//            nodeChapterList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
//            for (int i = 0; i < nodeChapterList.getLength(); i++) {
//                element = (Element) nodeChapterList.item(i);
//                nodeList = element.getElementsByTagName("title");
//                scoInfos.add(new Sco(nodeList.item(0) == null ? "" : nodeList.item(0).getTextContent(),
//                        element.getTagName(),
//                        "1",
//                        element.getAttribute("identifier"),
//                        scoPath + getUrl(element.getAttribute("identifier"), document)
//                ));
//            }
//            return scoInfos;
//        }
        //含有organization的情况
        for (int i = 0; i < nodeChapterList.getLength(); i++) {
            element = (Element) nodeChapterList.item(i);
            nodeList = element.getElementsByTagName("title");
            Sco sco = new Sco(nodeList.item(0) == null ? "" : nodeList.item(0).getTextContent(),
                    element.getTagName(),
                    "1",
                    element.getAttribute("identifier"),
                    element.getAttribute("identifierref") == "" ? "" : scoPath + getUrl(element.getAttribute("identifierref"), document),
                    element.getAttribute("identifierref") == "" ? "" : getLaunchData(element.getAttribute("identifierref"), document)
            );
            sco.setPassRaw(getCoreCredit(element.getAttribute("identifierref"), document));
            sco.setCoreCredit(DictConstant.CREDIT_IM);
            if (("").equals(sco.getPassRaw())) {
                sco.setCoreCredit(DictConstant.CREDIT_NO);
            }
            if (("").equals(sco.getUrl().trim())) {
                sco.setCoreCredit("");
            }
            scos.add(sco);
            getElement(element, scos, url, scoPath, document);
        }
        return scos;
    }

    private void getElement(Element element, List<Sco> scos, String url, String scoPath, Document document) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        NodeList nodes = element.getChildNodes();
        Element elementNew;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeName().equals("item")) {
                getElement((Element) nodes.item(i), scos, url, scoPath, document);
                elementNew = (Element) nodes.item(i);
                NodeList nodeList = elementNew.getElementsByTagName("title");
                Sco sco = new Sco(nodeList.item(0) == null ? "" : nodeList.item(0).getTextContent(),
                        elementNew.getTagName(),
                        element.getAttribute("identifier"), elementNew.getAttribute("identifier"),
                        elementNew.getAttribute("identifierref") == "" ? "" : scoPath + getUrl(elementNew.getAttribute("identifierref"), document),
                        elementNew.getAttribute("identifierref") == "" ? "" : getLaunchData(elementNew.getAttribute("identifierref"), document)
                );
                sco.setPassRaw(getCoreCredit(elementNew.getAttribute("identifierref"), document));
                sco.setCoreCredit(DictConstant.CREDIT_IM);
                if (("").equals(sco.getPassRaw())) {
                    sco.setCoreCredit(DictConstant.CREDIT_NO);
                }
                scos.add(sco);
            }
        }
    }

    private String getUrl(String identifier, Document document) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expression = xpath.compile("/manifest/resources/resource[@identifier='" + identifier + "']");
        Element element = (Element) expression.evaluate(document, XPathConstants.NODE);
        return element.getAttribute("href");
    }

    private String getLaunchData(String identifier, Document document) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expression = xpath.compile("/manifest/resources/resource[@identifier='" + identifier + "']");
        Element element = (Element) expression.evaluate(document, XPathConstants.NODE);
        try {
            return element.getAttribute("adlcp:datafromlms");
        } catch (Exception e) {
            return "";
        }
    }

    private String getCoreCredit(String identifier, Document document) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expression = xpath.compile("/manifest/resources/resource[@identifier='" + identifier + "']");
        Element element = (Element) expression.evaluate(document, XPathConstants.NODE);
        try {
            return element.getAttribute("adlcp:masteryscore");
        } catch (Exception e) {
            return "";
        }
    }
}