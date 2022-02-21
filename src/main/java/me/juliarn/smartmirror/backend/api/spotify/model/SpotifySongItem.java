package me.juliarn.smartmirror.backend.api.spotify.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import java.util.List;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SpotifySongItem(String name,
                              List<ItemArtist> artists,
                              SpotifySongItem.ItemAlbum album,
                              Integer durationMs) {

  @Creator
  public SpotifySongItem {
  }

  @Introspected
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record ItemArtist(String name) {

    @Creator
    public ItemArtist {
    }
  }

  @Introspected
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record ItemAlbum(String name, List<AlbumImage> images) {

    @Creator
    public ItemAlbum {
    }

    @Introspected
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record AlbumImage(String url, int width, int height) {

      @Creator
      public AlbumImage {
      }
    }
  }
}