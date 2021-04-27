package mx.mimir.sproner.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.mimir.sproner.repository.SpronerRepository;
import mx.mimir.sproner.model.Song;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 
 *  mvn spring-boot:run
    "%PSQL_ROOT%\bin\pg_ctl" -D "%PSQL_ROOT%\data" -l "%PSQL_ROOT%\log\run.log" start
    "%PSQL_ROOT%\bin\psql" -U dba -W -d musiclibrary
 */
   




@CrossOrigin(origins = "https://mercadomr.github.io")
@RestController
@RequestMapping("/api")
public class SpronerController {
    
    @Autowired
    SpronerRepository sr;
    
    @GetMapping("/song")
    public ResponseEntity<List<Song>> getAllSongs(
        @RequestParam(required = false)
        String title)
    {
        List<Song> songs = null;
        try{
            songs = new ArrayList<Song>();
            if ( title == null){
                sr.findAll().forEach(songs::add);
            }else{
                //sr.findByTitleContaining(title).forEach(songs::add);
                sr.findByTitleIgnoreCase(title).forEach(songs::add);
            }
            if ( songs.isEmpty() ){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(songs,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/song")
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        Song _song = null;
        System.out.println("Los datos recibidos son:");
        System.out.println(song);
        //temporalValues.setLocalTime(LocalTime.parse("15:30:18"));
        try{
            _song = sr.save(song);
            return new ResponseEntity<Song>(_song,HttpStatus.CREATED); 
        }catch(Exception e){
            return new ResponseEntity<Song>(_song,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(value = "/song/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable("id") long id, @RequestBody Song song){
        Optional<Song> songData = sr.findById(id);

		if (songData.isPresent()) {
			Song _song = songData.get();
			_song.setTitle(song.getTitle());
			_song.setDescription(song.getDescription());
			_song.setDuration(song.getDuration());
            _song.setPublishedOn(song.getPublishedOn());
			return new ResponseEntity<>(sr.save(_song), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    } 

    @GetMapping(value = "/song/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable("id") long id){
        Optional<Song> _song = sr.findById(id);

		if (_song.isPresent()) {
			return new ResponseEntity<>(_song.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    /* DELETE ALL */
    @DeleteMapping(value="/song")
    public ResponseEntity<Song> deleteAllSongs(){
        try{
            sr.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* DELETE By ID */
    @DeleteMapping(value="/song/{id}")
    public ResponseEntity<Song> deleteSong(@PathVariable("id") long id){
        try{
            sr.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
