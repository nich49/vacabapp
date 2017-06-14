package com.example.vocablarybuilderapp;

/**
 * Created by auk on 2017/06/11.
 * リストされる単語のクラス
 */

public class ListItem {
  private long id = 0;
  private String word = null;
  private String meaning = null;
  private String imagePath = null;
  private Long createdDate = null;
  private Long modifiedDate = null;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public String getMeaning() {
    return meaning;
  }

  public void setMeaning(String meaning) {
    this.meaning = meaning;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public Long getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Long createdDate) {
    this.createdDate = createdDate;
  }

  public Long getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(Long modifiedDate) {
    this.modifiedDate = modifiedDate;
  }
}
