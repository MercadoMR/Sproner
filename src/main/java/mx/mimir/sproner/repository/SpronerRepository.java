package mx.mimir.sproner.repository;

import mx.mimir.sproner.model.Song;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpronerRepository extends JpaRepository<Song,Long> {
    
    List<Song> findByTitleContaining(String title);
    List<Song> findByTitleIgnoreCase(String title);
    
}
