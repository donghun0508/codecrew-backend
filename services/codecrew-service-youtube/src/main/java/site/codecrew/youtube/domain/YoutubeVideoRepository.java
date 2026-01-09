package site.codecrew.youtube.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface YoutubeVideoRepository extends JpaRepository<YoutubeVideo, Long> {

    @Query(value = """
        SELECT id
        FROM youtube_video
        WHERE (:lastId IS NULL OR id < :lastId)
          AND (
            :searchQuery IS NULL
            OR :searchQuery = ''
            OR title LIKE CONCAT('%', :searchQuery, '%')
          )
        ORDER BY id DESC
        LIMIT :limit
    """, nativeQuery = true)
    List<Long> findPageIds(
        @Param("lastId") Long lastId,
        @Param("limit") int limit,
        @Param("searchQuery") String searchQuery
    );


    @Query(value = """
        SELECT *
        FROM youtube_video
        WHERE id IN (:ids)
        ORDER BY id DESC
        """, nativeQuery = true)
    List<YoutubeVideo> findAllByIdsOrderByIdDesc(@Param("ids") List<Long> ids);

}
