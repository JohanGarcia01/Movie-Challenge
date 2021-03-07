package com.endava.challenge.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ratings")
public class Rating {
    @Id
    @CsvBindByPosition(position = 0)
    private String _id;
    @CsvBindByPosition(position = 1)
    private String movie_id;
    @CsvBindByPosition(position = 2)
    public double rating;
}
