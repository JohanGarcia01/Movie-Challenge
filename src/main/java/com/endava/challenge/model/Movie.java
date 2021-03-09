package com.endava.challenge.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movies")
public class Movie {
    @Id
    @CsvBindByPosition(position = 5)
    private String _id;
    @CsvBindByPosition(position = 8)
    private String original_title;
    @CsvBindByPosition(position = 9)
    private String overview;
    @CsvBindByPosition(position = 7)
    private String original_language;
    @CsvBindByPosition(position = 2)
    private double budget;
    @CsvBindByPosition(position = 0)
    private boolean adult;
    @CsvBindByPosition(position = 3)
    private String genres_txt;
    private List<Genre> genres;

    public void buildGenres(){
        this.genres = new ArrayList<>();
        JSONArray object = new JSONArray(this.genres_txt);
        for(int i = 0; i < object.length(); i++ ){
            Genre genre = new Genre();
            genre.setId(String.valueOf(object.getJSONObject(i).get("id")));
            genre.setName(String.valueOf(object.getJSONObject(i).get("name")));
            this.genres.add(genre);
        }
        this.genres_txt = null;
    }
}
