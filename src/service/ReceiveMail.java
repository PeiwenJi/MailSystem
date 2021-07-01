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
	    	String port = "110"; // 端口号
	        String servicePath = "pop3.163.com"; // 服务器地址
	
	        // 准备连接服务器的会话信息
	        Properties props = new Properties();
	        props.setProperty("mail.store.protocol", "pop3"); // 使用pop3协议
	        props.setProperty("mail.pop3.port", port); // 端口
	        props.setProperty("mail.pop3.host", servicePath); // pop3服务器
	         
	        // 创建Session实例对象
	        Session session = Session.getInstance(props);         
			store = session.getStore("pop3");		
	        store.connect(name+"@163.com", psw); // 163邮箱程序登录属于第三方登录所以这里的密码是163给的授权密码而并非普通的登录密码
	         
	        // 获得收件箱
	        folder = store.getFolder("INBOX");
	        folder.open(Folder.READ_WRITE); // 打开收件箱
	        
	    	unNum=folder.getUnreadMessageCount();
	    	newNum=folder.getNewMessageCount();
	    	delNum=folder.getDeletedMessageCount();
	    	totalNum=folder.getMessageCount();
	        
	        // 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数
	        System.out.println("未读邮件数: " + unNum);
	
	        // 由于POP3协议无法获知邮件的状态,所以下面得到的结果始终都是为0
	        System.out.println("新邮件: " + newNum);
	
	        // 获得收件箱中的邮件总数
	        System.out.println("邮件总数: " + totalNum);
	
	        // 得到收件箱中的所有邮件,并解析
	        messages = folder.getMessages();
	        parseMessage();
    	} catch (Exception e) {
			e.printStackTrace();
			Notice frame = new Notice("输入有误。请重试！");
			frame.setVisible(true);
		}
    }
 
    
    /*以下是一些get函数，目的是向外界传递参数*/
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
 
    
    /*以下是处理邮件、获取邮件重要信息部分*/
    
    //解析邮件
    public void parseMessage()
            throws MessagingException, IOException {
        if (messages == null || messages.length < 1) {
            throw new MessagingException("未找到要解析的邮件!");
        }
        // 解析所有邮件
        for (int i = 0, count = messages.length; i < count; i++) {
        	msg = (MimeMessage) messages[i];
            System.out.println("------------------解析第" + getMailNum()
                    + "封邮件-------------------- ");
            System.out.println("主题: " + getSubject());
            System.out.println("发件人: " + getFrom());
            System.out.println("收件人：" + getReceiveAddress(null));
            System.out.println("发送时间：" + getSentDate(null));
            System.out.println("是否已读：" + isSeen());
            System.out.println("邮件优先级：" + getPriority());
            System.out.println("是否需要回执：" + isReplySign());
            System.out.println("邮件大小：" + getMailSize());
            System.out.println("是否包含附件：" + isAttachment());
            if (isAttachment()) {
                saveAttachment(msg, adr); // 保存附件
            }
            content = new StringBuffer(30);
            getMailTextContent(msg, content);
            System.out.println("邮件正文：" + getText());
            System.out.println("------------------第" + getMailNum()
                    + "封邮件解析结束-------------------- ");
            System.out.println();

        }
    }   
    
    //获得邮件主题
    public String getSubject()
            throws UnsupportedEncodingException, MessagingException {
        return MimeUtility.decodeText(msg.getSubject());
    }

    //获得邮件发件人
    public String getFrom() throws MessagingException,
            UnsupportedEncodingException {
        String from = "";
        Address[] froms = msg.getFrom();
        if (froms.length < 1) {
            throw new MessagingException("没有发件人!");
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

    //根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
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
            throw new MessagingException("没有收件人!");
        }
        for (Address address : addresss) {
            InternetAddress internetAddress = (InternetAddress) address;
            receiveAddress.append(internetAddress.toUnicodeString())
                    .append(",");
        }

        receiveAddress.deleteCharAt(receiveAddress.length() - 1); // 删除最后一个逗号

        return receiveAddress.toString();
    }

    //获得邮件发送时间
    public String getSentDate(String pattern)
            throws MessagingException {
        Date receivedDate = msg.getSentDate();
        if (receivedDate == null) {
            return "";
        }
        if (pattern == null || "".equals(pattern)) {
            pattern = "yyyy年MM月dd日 E HH:mm ";
        }
        return new SimpleDateFormat(pattern).format(receivedDate);
    }

     //判断邮件中是否包含附件
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

    //判断邮件是否已读
    public boolean isSeen() throws MessagingException {
        return msg.getFlags().contains(Flags.Flag.SEEN);
    }

    //判断邮件是否需要阅读回执
    public boolean isReplySign()
            throws MessagingException {
        boolean replySign = false;
        String[] headers = msg.getHeader("Disposition-Notification-To");
        if (headers != null) {
            replySign = true;
        }
        return replySign;
    }

    //获得邮件的优先级
    public String getPriority() throws MessagingException {
        String priority = "普通";
        String[] headers = msg.getHeader("X-Priority");
        if (headers != null) {
            String headerPriority = headers[0];
            if (headerPriority.indexOf("1") != -1
                    || headerPriority.indexOf("High") != -1) {
                priority = "紧急";
            } else if (headerPriority.indexOf("5") != -1
                    || headerPriority.indexOf("Low") != -1) {
                priority = "低";
            } else {
                priority = "普通";
            }
        }
        return priority;
    }

    //获得邮件文本内容    
    public void getMailTextContent(Part part, StringBuffer content)
            throws MessagingException, IOException {
        // 如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
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

    //保存附件
    public void saveAttachment(Part part, String destDir)
            throws UnsupportedEncodingException, MessagingException,
            FileNotFoundException, IOException {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent(); // 复杂体邮件
            // 复杂体邮件包含多个邮件体
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                // 获得复杂体邮件中其中一个邮件体
                BodyPart bodyPart = multipart.getBodyPart(i);
                // 某一个邮件体也有可能是由多个邮件体组成的复杂体
                String disp = bodyPart.getDisposition();
                boolean isHasAttachment = (disp != null && (disp
                        .equalsIgnoreCase(Part.ATTACHMENT) || disp
                        .equalsIgnoreCase(Part.INLINE)));
                if (isHasAttachment) {
                    InputStream is = bodyPart.getInputStream();
                    saveFile(is, destDir, decodeText(bodyPart.getFileName()));
                    System.out.println("----附件："
                            + decodeText(bodyPart.getFileName()) + ","
                            + " 保存路径为" + destDir);
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

     //读取输入流中的数据保存至指定目录
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

    //文本解码
    public String decodeText(String encodeText)
            throws UnsupportedEncodingException {
        if (encodeText == null || "".equals(encodeText)) {
            return "";
        } else {
            return MimeUtility.decodeText(encodeText);
        }
    }
}

