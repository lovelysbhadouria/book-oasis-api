package com.bookoasisapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Schema(accessMode = Schema.AccessMode.READ_ONLY)
private Long id;

@NotBlank(message = "Title is required")
@Size(max = 255, message = "Title must be under 255 characters")
private String title;

@NotBlank(message = "Author is required")
private String author;

@NotNull(message = "Publication year is required")
@Min(value = 1450, message = "Publication year must be a valid year")
@Max(value = 2026, message = "Publication year cannot be in the future")
private Integer publicationYear;
}