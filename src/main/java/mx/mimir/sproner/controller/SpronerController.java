package mx.mimir.sproner.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.mimir.sproner.repository.SpronerRepository;
import mx.mimir.sproner.model.Song;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 
 *  mvn spring-boot:run
    "%PSQL_ROOT%\bin\pg_ctl" -D "%PSQL_ROOT%\data" -l "%PSQL_ROOT%\log\run.log" start
    "%PSQL_ROOT%\bin\psql" -U dba -W -d musiclibrary
 */
   




@CrossOrigin(origins = "*")
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
                System.out.println("Buscando se mostraran todas");
                sr.findAll().forEach(songs::add);
            }else{
                System.out.println("Buscando la keyword:"+title);
                sr.findByTitleContaining(title).forEach(songs::add);
            }
            if ( songs.isEmpty() ){
                System.out.println("La lista esta vacia");
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
    

}
