package domain;
/**
 * ��ȡxml�󱣴��ʵ�����
 * @author wsz
 * @date 2018��2��2��
 */
public class Message {
	
	private String id;
	
	private String content;
	
	private String user;
	
	private String time;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", content=" + content + ", user=" + user + ", time=" + time + "]";
	}
	
}
