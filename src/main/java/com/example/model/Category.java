package com.example.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "categories")
public class Category extends BaseEntity implements Serializable {



    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Book> books = new ArrayList<>();


    public Category(Long id, LocalDate creationDate, LocalDate updatedDate,
                    String categoryName, List<Book> books) {
        super(id, creationDate, updatedDate);
        this.categoryName = categoryName;
        this.books = books;
    }


}
