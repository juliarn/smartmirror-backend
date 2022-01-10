package me.juliarn.smartmirror.backend.spotify.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import java.util.List;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifySongItem {

  private String name;
  private List<ItemArtist> artists;
  private Integer durationMs;

  @Creator
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

  @Introspected
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class ItemArtist {

    private String name;

    @Creator
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

  @Introspected
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class ItemAlbum {

    private String name;
    private List<AlbumImage> images;

    @Creator
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

    @Introspected
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class AlbumImage {

      private String url;
      private int width;
      private int height;

      @Creator
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