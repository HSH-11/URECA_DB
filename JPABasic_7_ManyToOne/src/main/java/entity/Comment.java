package entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

// 게시글에 달린 comment
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String content;
	
//	@ManyToOne
//	private Post post; // 1 대 다 중에 "다"가 Ownership을 가진다.
	// @ManyToOne 관계를 가진 table comment에 연결 컬럼은 직접 지정하지 않으면 non-owing entity의 이름 + _id로 만들어진다.
	
//	@ManyToOne(cascade = CascadeType.PERSIST)
//	private Post post;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Post post;
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
//	@Override
//	public String toString() {
//		return "Comment [id=" + id + ", content=" + content + "]";
//	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", post=" + post + "]";
	}
	
	
	
}
