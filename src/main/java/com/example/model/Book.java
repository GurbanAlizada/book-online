package com.example.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "books")
public class Book extends BaseEntity implements Serializable {


    @Column(name = "title")
    private String title;

    @Column(name = "author_name")
    private String authorName;


    @Column(name = "publisher")
    private String publisher;


    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;


    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "last_page_number")
    private Integer lastPageNumber;


    @Column(name = "total_page")
    private Integer totalPage;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;


}
