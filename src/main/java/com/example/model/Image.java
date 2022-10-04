package com.example.model;

import com.fasterxml.jackson.core.SerializableString;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Table(name = "images")
public class Image extends BaseEntity implements Serializable {


    @Column(name = "image_url")
    private String imageUrl;


    /*@OneToOne(mappedBy = "image")
    private Book book;
    // image-e gore book-lari axtarmiyacam ona gore iki terefli relationship-e ehtiyac yoxdur
    */

    public Image(Long id, LocalDate creationDate,
                 LocalDate updatedDate, String imageUrl) {

        super(id, creationDate, updatedDate);
        this.imageUrl = imageUrl;
    }


}
