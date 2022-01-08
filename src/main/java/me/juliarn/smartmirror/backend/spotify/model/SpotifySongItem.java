package me.juliarn.smartmirror.backend.spotify.model;

import java.util.List;

public class SpotifySongItem {

  private String name;
  private List<ItemArtist> artists;
  private Integer durationMs;

  public SpotifySongItem(String name, List<ItemArtist> artists, Integer durationMs) {
    this.name = name;
    this.artists = artists;
    this.durationMs = durationMs;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<ItemArtist> getArtists() {
    return this.artists;
  }

  public void setArtists(List<ItemArtist> artists) {
    this.artists = artists;
  }

  public Integer getDurationMs() {
    return this.durationMs;
  }

  public void setDurationMs(Integer durationMs) {
    this.durationMs = durationMs;
  }

  public static class ItemArtist {

    private String name;

    public ItemArtist(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class ItemAlbum {

    private String name;
    private List<AlbumImage> images;

    public ItemAlbum(String name, List<AlbumImage> images) {
      this.name = name;
      this.images = images;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public List<AlbumImage> getImages() {
      return this.images;
    }

    public void setImages(List<AlbumImage> images) {
      this.images = images;
    }

    public static class AlbumImage {

      private String url;
      private int width;
      private int height;

      public AlbumImage(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
      }

      public String getUrl() {
        return this.url;
      }

      public void setUrl(String url) {
        this.url = url;
      }

      public int getWidth() {
        return this.width;
      }

      public void setWidth(int width) {
        this.width = width;
      }

      public int getHeight() {
        return this.height;
      }

      public void setHeight(int height) {
        this.height = height;
      }
    }
  }
}