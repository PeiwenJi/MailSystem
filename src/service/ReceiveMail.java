package service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import ui.Notice;

public class ReceiveMail {

    private String adr;
	private String name;
	private String psw;
	private Message[] messages = null;
	private MimeMessage msg;
	private StringBuffer content;
	private int unNum;
	private int newNum;
	private int delNum;
	private int totalNum;
	private Folder folder;
	private Store store;
	
	public ReceiveMail(String adr,String name,String psw) {
		this.adr=adr;
		this.name=name;
		this.psw=psw;
	}
    
    public void resceive()  {
    	try {
	    	String port = "110"; // �˿ں�
	        String servicePath = "pop3.163.com"; // ��������ַ
	
	        // ׼�����ӷ������ĻỰ��Ϣ
	        Properties props = new Properties();
	        props.setProperty("mail.store.protocol", "pop3"); // ʹ��pop3Э��
	        props.setProperty("mail.pop3.port", port); // �˿�
	        props.setProperty("mail.pop3.host", servicePath); // pop3������
	         
	        // ����Sessionʵ������
	        Session session = Session.getInstance(props);         
			store = session.getStore("pop3");		
	        store.connect(name+"@163.com", psw); // 163��������¼���ڵ�������¼���������������163������Ȩ�����������ͨ�ĵ�¼����
	         
	        // ����ռ���
	        folder = store.getFolder("INBOX");
	        folder.open(Folder.READ_WRITE); // ���ռ���
	        
	    	unNum=folder.getUnreadMessageCount();
	    	newNum=folder.getNewMessageCount();
	    	delNum=folder.getDeletedMessageCount();
	    	totalNum=folder.getMessageCount();
	        
	        // ����POP3Э���޷���֪�ʼ���״̬,����getUnreadMessageCount�õ������ռ�����ʼ�����
	        System.out.println("δ���ʼ���: " + unNum);
	
	        // ����POP3Э���޷���֪�ʼ���״̬,��������õ��Ľ��ʼ�ն���Ϊ0
	        System.out.println("���ʼ�: " + newNum);
	
	        // ����ռ����е��ʼ�����
	        System.out.println("�ʼ�����: " + totalNum);
	
	        // �õ��ռ����е������ʼ�,������
	        messages = folder.getMessages();
	        parseMessage();
    	} catch (Exception e) {
			e.printStackTrace();
			Notice frame = new Notice("�������������ԣ�");
			frame.setVisible(true);
		}
    }
 
    
    /*������һЩget������Ŀ��������紫�ݲ���*/
    public Message getMessages(int i) {
    	return messages[i];
    }
    
    public void setMsg(Message messages1) {
    	msg = (MimeMessage) messages1;
    }
       
    public void close() throws MessagingException {
    	folder.close(true);
        store.close();
    }

    public int getUnNum() {
    	return unNum;
    }
    
    public int getDelNum() {
    	return delNum;
    }
    
    public int getNewNum() {
    	return newNum;
    }
    
    public int getTotalNum() {
    	return totalNum;
    }
    
    public int getMailLen() {
    	return messages.length;
    }
    
    public int getMailNum() {
    	return msg.getMessageNumber();
    }
    
    public Boolean isAttachment() throws MessagingException, IOException {
    	return isContainAttachment(msg);
    }
    
    public StringBuffer getText() {
    	if (content.length() > 100) {
    		StringBuffer sb = new StringBuffer(content.substring(0, 100)+"...");
			return sb;
    	}
		else
			return content;
    }
       
    public String getMailSize() throws MessagingException {
    	return msg.getSize() * 1024 + "kb";
    }
 
    
    /*�����Ǵ����ʼ�����ȡ�ʼ���Ҫ��Ϣ����*/
    
    //�����ʼ�
    public void parseMessage()
            throws MessagingException, IOException {
        if (messages == null || messages.length < 1) {
            throw new MessagingException("δ�ҵ�Ҫ�������ʼ�!");
        }
        // ���������ʼ�
        for (int i = 0, count = messages.length; i < count; i++) {
        	msg = (MimeMessage) messages[i];
            System.out.println("------------------������" + getMailNum()
                    + "���ʼ�-------------------- ");
            System.out.println("����: " + getSubject());
            System.out.println("������: " + getFrom());
            System.out.println("�ռ��ˣ�" + getReceiveAddress(null));
            System.out.println("����ʱ�䣺" + getSentDate(null));
            System.out.println("�Ƿ��Ѷ���" + isSeen());
            System.out.println("�ʼ����ȼ���" + getPriority());
            System.out.println("�Ƿ���Ҫ��ִ��" + isReplySign());
            System.out.println("�ʼ���С��" + getMailSize());
            System.out.println("�Ƿ����������" + isAttachment());
            if (isAttachment()) {
                saveAttachment(msg, adr); // ���渽��
            }
            content = new StringBuffer(30);
            getMailTextContent(msg, content);
            System.out.println("�ʼ����ģ�" + getText());
            System.out.println("------------------��" + getMailNum()
                    + "���ʼ���������-------------------- ");
            System.out.println();

        }
    }   
    
    //����ʼ�����
    public String getSubject()
            throws UnsupportedEncodingException, MessagingException {
        return MimeUtility.decodeText(msg.getSubject());
    }

    //����ʼ�������
    public String getFrom() throws MessagingException,
            UnsupportedEncodingException {
        String from = "";
        Address[] froms = msg.getFrom();
        if (froms.length < 1) {
            throw new MessagingException("û�з�����!");
        }
        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }
        from = person + "<" + address.getAddress() + ">";

        return from;
    }

    //�����ռ������ͣ���ȡ�ʼ��ռ��ˡ����ͺ����͵�ַ������ռ�������Ϊ�գ��������е��ռ���
    public String getReceiveAddress(
            Message.RecipientType type) throws MessagingException {
        StringBuffer receiveAddress = new StringBuffer();
        Address[] addresss = null;
        if (type == null) {
            addresss = msg.getAllRecipients();
        } else {
            addresss = msg.getRecipients(type);
        }

        if (addresss == null || addresss.length < 1) {
            throw new MessagingException("û���ռ���!");
        }
        for (Address address : addresss) {
            InternetAddress internetAddress = (InternetAddress) address;
            receiveAddress.append(internetAddress.toUnicodeString())
                    .append(",");
        }

        receiveAddress.deleteCharAt(receiveAddress.length() - 1); // ɾ�����һ������

        return receiveAddress.toString();
    }

    //����ʼ�����ʱ��
    public String getSentDate(String pattern)
            throws MessagingException {
        Date receivedDate = msg.getSentDate();
        if (receivedDate == null) {
            return "";
        }
        if (pattern == null || "".equals(pattern)) {
            pattern = "yyyy��MM��dd�� E HH:mm ";
        }
        return new SimpleDateFormat(pattern).format(receivedDate);
    }

     //�ж��ʼ����Ƿ��������
    public boolean isContainAttachment(Part part)
            throws MessagingException, IOException {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                boolean isHasAttachment = (disp != null && (disp
                        .equalsIgnoreCase(Part.ATTACHMENT) || disp
                        .equalsIgnoreCase(Part.INLINE)));
                if (isHasAttachment) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = isContainAttachment(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("application") != -1) {
                        flag = true;
                    }
                    if (contentType.indexOf("name") != -1) {
                        flag = true;
                    }
                }
                if (flag) {
                    break;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = isContainAttachment((Part) part.getContent());
        }
        return flag;
    }

    //�ж��ʼ��Ƿ��Ѷ�
    public boolean isSeen() throws MessagingException {
        return msg.getFlags().contains(Flags.Flag.SEEN);
    }

    //�ж��ʼ��Ƿ���Ҫ�Ķ���ִ
    public boolean isReplySign()
            throws MessagingException {
        boolean replySign = false;
        String[] headers = msg.getHeader("Disposition-Notification-To");
        if (headers != null) {
            replySign = true;
        }
        return replySign;
    }

    //����ʼ������ȼ�
    public String getPriority() throws MessagingException {
        String priority = "��ͨ";
        String[] headers = msg.getHeader("X-Priority");
        if (headers != null) {
            String headerPriority = headers[0];
            if (headerPriority.indexOf("1") != -1
                    || headerPriority.indexOf("High") != -1) {
                priority = "����";
            } else if (headerPriority.indexOf("5") != -1
                    || headerPriority.indexOf("Low") != -1) {
                priority = "��";
            } else {
                priority = "��ͨ";
            }
        }
        return priority;
    }

    //����ʼ��ı�����    
    public void getMailTextContent(Part part, StringBuffer content)
            throws MessagingException, IOException {
        // ������ı����͵ĸ�����ͨ��getContent��������ȡ���ı����ݣ����ⲻ��������Ҫ�Ľ��������������Ҫ���ж�
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part) part.getContent(), content);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart, content);
            }
        }
    }

    //���渽��
    public void saveAttachment(Part part, String destDir)
            throws UnsupportedEncodingException, MessagingException,
            FileNotFoundException, IOException {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent(); // �������ʼ�
            // �������ʼ���������ʼ���
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                // ��ø������ʼ�������һ���ʼ���
                BodyPart bodyPart = multipart.getBodyPart(i);
                // ĳһ���ʼ���Ҳ�п������ɶ���ʼ�����ɵĸ�����
                String disp = bodyPart.getDisposition();
                boolean isHasAttachment = (disp != null && (disp
                        .equalsIgnoreCase(Part.ATTACHMENT) || disp
                        .equalsIgnoreCase(Part.INLINE)));
                if (isHasAttachment) {
                    InputStream is = bodyPart.getInputStream();
                    saveFile(is, destDir, decodeText(bodyPart.getFileName()));
                    System.out.println("----������"
                            + decodeText(bodyPart.getFileName()) + ","
                            + " ����·��Ϊ" + destDir);
                } else if (bodyPart.isMimeType("multipart/*")) {
                    saveAttachment(bodyPart, destDir);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("name") != -1
                            || contentType.indexOf("application") != -1) {
                        saveFile(bodyPart.getInputStream(), destDir,
                                decodeText(bodyPart.getFileName()));
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachment((Part) part.getContent(), destDir);
        }
    }

     //��ȡ�������е����ݱ�����ָ��Ŀ¼
    private void saveFile(InputStream is, String destDir, String fileName)
            throws FileNotFoundException, IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(new File(destDir + fileName)));
        int len = -1;
        while ((len = bis.read()) != -1) {
            bos.write(len);
            bos.flush();
        }
        bos.close();
        bis.close();
    }

    //�ı�����
    public String decodeText(String encodeText)
            throws UnsupportedEncodingException {
        if (encodeText == null || "".equals(encodeText)) {
            return "";
        } else {
            return MimeUtility.decodeText(encodeText);
        }
    }
}

